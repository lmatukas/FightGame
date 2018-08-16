package main.Services;

import main.Models.BL.UserModel;
import main.Models.DTO.RegisterDTO;

import java.io.IOException;

public interface IRegisterService {
    RegisterDTO find(String userName, String email);
    RegisterDTO register(String userName, String password, String email) throws IOException;
    UserModel addUserToCache(String email);

}
