package main.Services.Impl;

import main.Models.BL.UserExtendedModel;
import main.Models.DTO.UserExtendedDTO;
import main.Services.IHigherService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UserInfoService {

    private IHigherService hs = new HigherService();

    public UserExtendedModel getUserExtendedById(long userId) throws IOException{
        UserExtendedDTO dto = hs.getUserExtendByUserId(userId);
        if(dto.success){
            UserExtendedModel model = new UserExtendedModel();
            model.userId = dto.user.userId;
            model.userName = dto.user.userName;
            model.image = dto.user.profileImg;
            model.win = dto.user.win;
            model.lose = dto.user.lose;
            model.draw = dto.user.draw;
            model.totalFights = dto.user.totalFights;
            return model;
        }
        return null;
    }
}
