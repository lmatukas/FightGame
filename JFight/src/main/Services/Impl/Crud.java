package main.Services.Impl;

import javafx.util.Pair;
import main.Models.BL.DBQueryModel;
import main.Models.BL.UpdateModel;
import main.Models.DAL.UserExtendedDAL;
import main.Models.DTO.DBqueryDTO;
import main.Services.Helpers.QueryBuilder;
import main.Services.ICrud;
import main.Services.IDataBase;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crud implements ICrud {

    private IDataBase dataBase = new DataBase();
    private Connection connection;
    private Statement statement;

    @Override
    public DBqueryDTO create(Object object) {
        try {
            connection = dataBase.getConnection();
//            statement = connection.createStatement();
//            statement.executeUpdate(createInsertQuery(object));
//            statement.close();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(createInsertQuery(object));
            if (object.getClass().getSimpleName().equals("UserExtendedDAL")) {
                UserExtendedDAL userExtendedDAL = (UserExtendedDAL) object;
                preparedStatement.setBytes(1, userExtendedDAL.profileImg);
            }
            preparedStatement.executeUpdate();
            return new DBqueryDTO(true, "", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }

    @Override
    public <T> DBqueryDTO<T> read(DBQueryModel dbQueryModel, Class<T> dalType) {
        try {

            connection = dataBase.getConnection();
            ResultSet rs;
            CallableStatement cstmt = null;

            if (dbQueryModel.procedure != null) {
                cstmt = connection.prepareCall(dbQueryModel.procedure.name);
                List<Pair<String, Object>> params = dbQueryModel.procedure.params;
                for (Pair<String, Object> pair : params) {

                    if (pair.getValue().getClass().getSimpleName().equals("Integer")) {
                        cstmt.setInt(pair.getKey(), (int) pair.getValue());
                    } else {
                        cstmt.setString(pair.getKey(), (String) pair.getValue());
                    }

                }
                cstmt.execute();
                rs = cstmt.getResultSet();
            } else {
                statement = connection.createStatement();
                rs = statement
                        .executeQuery(new QueryBuilder(getClassNameWithoutDAL(dalType))
                                .buildQuery(dbQueryModel, "read")
                                .getQuery());
            }

            List<T> rows = new ArrayList<>();
            while (rs.next()) {
                T dal = dalType.newInstance();
                loadResultSetIntoObject(rs, dal);
                rows.add(dal);
            }

            if (statement != null) {
                statement.close();
            } else if (cstmt != null) {
                cstmt.close();
            }

            return new DBqueryDTO<>(true, "", rows);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }


    @Override
    public DBqueryDTO update(Object dal, String[] primaryKey) {
        try {
            connection = dataBase.getConnection();
            PreparedStatement stmt = createUpdatePreparedStatement(dal,
                    getClassNameWithoutDAL(dal.getClass()), primaryKey);
            System.out.println("Updated rows -> " + stmt.executeUpdate());
            stmt.close();
            return new DBqueryDTO(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }

    @Override
    public DBqueryDTO delete(DBQueryModel deleteModel, Class dal) {
        try {
            connection = dataBase.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(new QueryBuilder(getClassNameWithoutDAL(dal))
                    .buildQuery(deleteModel, "delete")
                    .getQuery());
            connection.commit();
            statement.close();
            return new DBqueryDTO(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, null, null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }


    private void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = rst.getObject(name);
            Class<?> type = field.getType();
            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);

                if (type == boolean.class) {
                    value = (int) value == 1;
                } else if (type == long.class) {
                    value = Long.parseLong(value.toString());
                } else {
                    value = boxed.cast(value);
                }

            }
            field.set(object, value);
        }
    }

    private String createInsertQuery(Object object)
            throws IllegalArgumentException, IllegalAccessException {
        Class<?> zclass = object.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(getClassNameWithoutDAL(zclass))
                .append(" VALUES (");
        Field[] fields = zclass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);

            if (fields[i].getName().equals("userId") && object.getClass().getSimpleName().equals("UserDAL")) {
                continue;
            } else if (fields[i].getName().equals("profileImg")) {
//                sb.append("CAST(").append(quoteIdentifier(byteArrayToString((byte[]) fields[i].get(object)))).append("AS VARBINARY(MAX))");
                sb.append("?");
            } else {
                sb.append(quoteIdentifier(fields[i].get(object).toString()));
            }

            if (i != fields.length - 1) {
                sb.append(",");
            } else {
                sb.append(")");
            }

        }
        return sb.toString();
    }

    private PreparedStatement createUpdatePreparedStatement(Object object,
                                                            String tableName, String[] primaryKey) {
        PreparedStatement stmt;
        try {
            Class<?> zclass = object.getClass();
            UpdateModel updateModel = createUpdateQuery(zclass, tableName, primaryKey);
            stmt = connection.prepareStatement(updateModel.query);
            Field[] fields = zclass.getDeclaredFields();
            int fieldPosition = 1;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(object);
                String name = field.getName();
                if (checkForPrimaryKey(name, primaryKey)) {
                    int pkPosition = updateModel.pkLocation.get(updateModel.primaryKeys.indexOf(name));
                    stmt.setObject(pkPosition, value);
                } else {
                    stmt.setObject(fieldPosition, value);
                    fieldPosition++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to create PreparedStatement: " + e.getMessage(), e);
        }
        return stmt;
    }

    private UpdateModel createUpdateQuery(Class<?> zclass, String tableName, String[] primaryKey) {
        StringBuilder sets = new StringBuilder();
        UpdateModel updateModel = new UpdateModel();
        updateModel.pkLocation = new ArrayList<>();
        updateModel.primaryKeys = new ArrayList<>();

        String where = null;
        Field[] fields = zclass.getDeclaredFields();
        int pkPosition = fields.length - primaryKey.length + 1;
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
//            String pair = quoteIdentifier(name) + " = ?";
            String pair = name + " = ?";
            if (checkForPrimaryKey(name, primaryKey)) {

                if (updateModel.primaryKeys.isEmpty()) {
                    where = " WHERE " + pair;
                } else {
                    where += " AND " + pair;
                }

                updateModel.pkLocation.add(pkPosition);
                updateModel.primaryKeys.add(name);
                pkPosition++;

            } else {
                if (sets.length() > 1) {
                    sets.append(", ");
                }
                sets.append(pair);
            }
        }

        if (where == null) {
            throw new IllegalArgumentException("Primary key not found in '" + zclass.getName() + "'");
        }
        updateModel.query = "UPDATE " + tableName + " SET " + sets.toString() + where;
        return updateModel;
    }

    private boolean checkForPrimaryKey(String fieldName, String[] primaryKeyArr){
        for (String pk : primaryKeyArr) {
            if (pk.equals(fieldName)) return true;
        }
        return false;
    }

    private String quoteIdentifier(String value) {
        return "'" + value + "'";
    }

    private boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class ||
                type == double.class || type == float.class
                || type == boolean.class || type == byte.class
                || type == char.class || type == short.class);
    }

    private Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            throw new IllegalArgumentException("Class '" + type.getName() + "' is not a primitive");
        }
    }

    private String getClassNameWithoutDAL(Class c) {
        if (c.getSimpleName().equals("UserDAL")) {
            return "[User]";
        }
        return c.getSimpleName().replace("DAL", "");
    }
}


