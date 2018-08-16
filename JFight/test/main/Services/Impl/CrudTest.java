package main.Services.Impl;

import main.Models.BL.DBQueryModel;
import main.Models.DAL.ChallengeDAL;
import main.Services.ICrud;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CrudTest {
    ICrud crud = new Crud();

    @Test
    public void create() {
        assertNotNull(crud.create(new StringBuilder("labas")));
    }

    @Test
    public void read() {
        assertNotNull(crud.read(new DBQueryModel(),DataBase.class));
    }

    @Test
    public void readNull() {
        assertNotNull(crud.read(null,null));
    }

    @Test
    public void update() {
        assertNotNull(crud.update(new ChallengeDAL(),new String[]{"test"}));
    }

    @Test
    public void delete() {
        assertNotNull(crud.delete(new DBQueryModel(), String.class));
    }
}