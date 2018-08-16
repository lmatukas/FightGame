package main.Models.DTO;

import main.Models.DAL.ReadyToFightDAL;
import java.util.List;

public class ReadyToFightDTO {
    public boolean success;
    public String message;
    public List<ReadyToFightDAL> list;

    public ReadyToFightDTO(boolean Success, String Message, List<ReadyToFightDAL> List) {
        success = Success;
        message = Message;
        list = List;
    }
}
