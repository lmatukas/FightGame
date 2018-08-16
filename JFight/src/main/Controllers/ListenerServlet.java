package main.Controllers;

import main.Models.BL.DBQueryModel;
import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightDAL;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Services.ICrud;
import main.Services.Impl.Crud;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ListenerServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ICrud crud = new Crud();
//        "DELETE FROM ReadyToFight"
        crud.delete(new DBQueryModel(), ReadyToFightDAL.class);
        crud.delete(new DBQueryModel(), ChallengeDAL.class);
        crud.delete(new DBQueryModel(), FightDAL.class);
        crud.delete(new DBQueryModel(), FightLogDAL.class);
        System.out.println("================================================================");
    }
}
