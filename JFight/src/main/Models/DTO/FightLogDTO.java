package main.Models.DTO;

import main.Models.DAL.FightLogDAL;
import java.util.List;

public class FightLogDTO {
    public boolean success;
    public String message;
    public List<FightLogDAL> list;

    public FightLogDTO(boolean Success, String Message, List<FightLogDAL> List) {
        success = Success;
        message = Message;
        list = List;
    }
}
