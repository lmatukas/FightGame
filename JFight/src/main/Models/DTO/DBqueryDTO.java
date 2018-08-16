package main.Models.DTO;

import java.util.List;

public class DBqueryDTO <T>{
    public boolean success;
    public String message;
    public List<T> list;

    public DBqueryDTO(boolean Success, String Message, List<T> List) {
        success = Success;
        message = Message;
        list = List;
    }
}
