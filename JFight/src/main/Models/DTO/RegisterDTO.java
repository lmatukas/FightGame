package main.Models.DTO;

import main.Models.BL.UserRegisterModel;

public class RegisterDTO {
    public boolean success;
    public String message;
    public UserRegisterModel userRegisterModel;

    public RegisterDTO(boolean success, String message, UserRegisterModel userRegisterModel) {
        this.success = success;
        this.message = message;
        this.userRegisterModel = userRegisterModel;
    }
}
