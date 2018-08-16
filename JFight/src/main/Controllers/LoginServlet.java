package main.Controllers;

import main.Models.BL.LoginModel;
import main.Models.BL.RegisterModel;
import main.Models.BL.RegistrationEventModel;
import main.Models.BL.UserModel;
import main.Models.DTO.LoginDTO;
import main.Models.DTO.RegisterDTO;
import main.Services.Helpers.NotNullOrEmpty;
import main.Services.ILoginService;
import main.Services.IRegisterService;
import main.Services.Impl.LoginService;
import main.Services.Impl.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EventMethodSelector(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void EventMethodSelector(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RegistrationEventModel registrationParams = getRegistrationParams(request);

        if (areAllRequestParamsCorrect(registrationParams, true)) {
            LoginRedirect(request, response, registrationParams.emailLogin, registrationParams.password);
        } else if (areAllRequestParamsCorrect(registrationParams, false)) {
            RegistrationRedirect(response, registrationParams.regName, registrationParams.regPass, registrationParams.regEmail);
        }else {
            //TODO send some parameter and display in js that login or registration failed
            response.sendRedirect("/login.jsp");
        }
    }

    private RegistrationEventModel getRegistrationParams(HttpServletRequest request) {
        RegistrationEventModel model = new RegistrationEventModel();
        model.emailLogin = request.getParameter("email");
        model.password = request.getParameter("password");
        model.regName = request.getParameter("regName");
        model.regPass = request.getParameter("regPass");
        model.confPass = request.getParameter("confPass");
        model.regEmail = request.getParameter("regEmail");

        return model;
    }

    private LoginModel getLoginModel(RegistrationEventModel registrationEventModel) {
        LoginModel loginModel = new LoginModel();
        loginModel.emailLogin = registrationEventModel.emailLogin;
        loginModel.password = registrationEventModel.password;
        return loginModel;
    }

    private RegisterModel getRegisterModel(RegistrationEventModel registrationEventModel) {
        RegisterModel registerModel = new RegisterModel();
        registerModel.regName = registrationEventModel.regName;
        registerModel.regEmail = registrationEventModel.regEmail;
        registerModel.regPass = registrationEventModel.regPass;
        registerModel.confPass = registrationEventModel.confPass;
        return registerModel;
    }

    private void RegistrationRedirect(HttpServletResponse response, String regName, String regPass, String regEmail) throws IOException {
        IRegisterService registerService = new RegisterService();
        RegisterDTO isRegistered = registerService.find(regName, regEmail);

        if (isRegistered.success) {
            //TODO send some parameter and display in js that this user is already registered
            response.sendRedirect("/login.jsp");
        } else {
            RegisterDTO registerDTO = registerService.register(regName, regPass, regEmail);
            if (registerDTO.success) {
                UserModel user = registerService.addUserToCache(regEmail);
                response.addCookie(new Cookie("token", user.uuid));
                response.sendRedirect("/news");
            } else {
                //TODO add on error msg
                response.sendRedirect("/login.jsp");
            }
        }
    }

    private void LoginRedirect(HttpServletRequest request, HttpServletResponse response, String emailLogin, String password) throws IOException {
        ILoginService loginService = new LoginService();
        LoginDTO login = loginService.find(emailLogin, password);

        if (login.success) {
            response.addCookie(new Cookie("token", login.user.uuid));
            response.sendRedirect(request.getContextPath() + "/news");
        } else {
            response.sendRedirect("/login.jsp");
        }
    }

    private boolean areAllRequestParamsCorrect(RegistrationEventModel model, boolean isLogin) {
        if (isLogin) {
            LoginModel loginModel = getLoginModel(model);
            return NotNullOrEmpty.paramsNotNullOrEmpty(loginModel);
        } else {
            RegisterModel registerModel = getRegisterModel(model);
            return NotNullOrEmpty.paramsNotNullOrEmpty(registerModel);
        }
    }
}

