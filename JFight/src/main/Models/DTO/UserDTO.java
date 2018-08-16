package main.Models.DTO;

import main.Models.DAL.UserDAL;

public class UserDTO {
    public boolean success;
    public String message;
    public UserDAL user;

    public UserDTO(boolean Success, String Message, UserDAL User) {
        success = Success;
        message = Message;
        user = User;
    }
}
