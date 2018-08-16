package main.Controllers;

import main.Models.BL.UserModel;
import main.Services.ICache;
import main.Services.Impl.Cache;
import main.Services.Impl.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewsServlet", urlPatterns = {"/news"})
public class NewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        LoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());

        if (token != null && loginService.validate(token)) {
            ICache cache = Cache.getInstance();
            UserModel user = (UserModel) cache.get(token.getValue());
            System.out.println(user.name);
            request.setAttribute("userName", user.name);
            request.getRequestDispatcher("/news.jsp").forward(request, response);
        } else {
            // UserModel is not logged in
            response.sendRedirect("/login");
        }

    }
}
