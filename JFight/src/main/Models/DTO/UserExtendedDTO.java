package main.Models.DTO;

import main.Models.DAL.UserExtendedDAL;

public class UserExtendedDTO {
    public boolean success;
    public String message;
    public UserExtendedDAL user;

    public UserExtendedDTO(boolean Success, String Message, UserExtendedDAL User) {
        success = Success;
        message = Message;
        user = User;
    }
}