package main.Models.DTO;

import main.Models.BL.UserModel;

public class LoginDTO {
    public boolean success;
    public String message;
    public UserModel user;

    public LoginDTO(boolean Success, String Message, UserModel UserModel) {
        success = Success;
        message = Message;
        user = UserModel;
    }
}
