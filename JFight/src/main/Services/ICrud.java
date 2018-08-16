package main.Services;

import main.Models.BL.DBQueryModel;
import main.Models.DTO.DBqueryDTO;

public interface ICrud {
    DBqueryDTO create(Object object);
    <T> DBqueryDTO<T> read(DBQueryModel dbQueryModel, Class<T> dalType);
    DBqueryDTO update(Object dal, String[] primaryKey);
    DBqueryDTO delete(DBQueryModel deleteModel, Class dal);
}
