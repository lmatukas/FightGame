package main.Models.DTO;

import main.Models.DAL.FightDAL;

public class FightDTO {
    public boolean success;
    public String message;
    public FightDAL dal;

    public FightDTO(boolean Success, String Message, FightDAL Dal) {
        success = Success;
        message = Message;
        dal = Dal;
    }

}
