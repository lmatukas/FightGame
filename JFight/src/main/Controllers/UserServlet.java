package main.Controllers;

import main.Models.BL.UserExtendedModel;
import main.Models.BL.UserModel;
import main.Services.Helpers.ObjectConverterToString;
import main.Services.ICache;
import main.Services.ILoginService;
import main.Services.Impl.Cache;
import main.Services.Impl.LoginService;
import main.Services.Impl.UserInfoService;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ILoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());

        if (loginService.validate(token)) {

            ICache cache = Cache.getInstance();
            UserModel user = (UserModel) cache.get(token.getValue());
            UserInfoService userInfoService = new UserInfoService();
            UserExtendedModel userExtendedModel = userInfoService.getUserExtendedById(user.id);

            if (userExtendedModel != null) {

                request.setAttribute("userExtended", ObjectConverterToString.convertObject(userExtendedModel));
//                request.setAttribute("userName", userExtendedModel.userName);
//                request.setAttribute("totalFights",userExtendedModel.totalFights);
//                request.setAttribute("draw",userExtendedModel.draw);
//                request.setAttribute("lose",userExtendedModel.lose);
//                request.setAttribute("userId",userExtendedModel.userId);
//                request.setAttribute("win",userExtendedModel.win);

//                response.setContentType("image/png");

//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(userExtendedModel.image, "png", baos);
//                byte[] imageBytes = baos.toByteArray();
//                baos.close();

                String imgString = javax.xml.bind.DatatypeConverter.printBase64Binary(userExtendedModel.image);
                request.setAttribute("image", imgString);

//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                servletOutputStream.write(imageBytes);
//                response.getOutputStream().close();
                request.getRequestDispatcher("/user.jsp").forward(request, response);
            } else {
                response.sendRedirect("/login");
                // TODO an error has occurred SHOW ERROR message to Front
            }
        } else {
            response.sendRedirect("/login");
        }
    }
}
