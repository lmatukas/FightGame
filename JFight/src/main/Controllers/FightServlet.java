package main.Controllers;

import main.Models.BL.*;
import main.Models.CONS.BodyParts;
import main.Services.Helpers.NotNullOrEmpty;
import main.Services.Helpers.ObjectConverterToString;
import main.Services.ICache;
import main.Services.IFightService;
import main.Services.Impl.Cache;
import main.Services.Impl.FightService;
import main.Services.Impl.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FightServlet", urlPatterns = {"/fight"})
public class FightServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestHandler(request, response);
    }

    private void requestHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());

        if (loginService.validate(token)) {
            ICache cache = Cache.getInstance();
            UserModel user = (UserModel) cache.get(token.getValue());
            FightParametersModel fightParameters = getFightParametersModel(request, user);
            TurnOutcomeModel turnOutcome = resolveRoundOutcome(fightParameters);

            if (turnOutcome != null) {
                request.setAttribute("turnOutcome", ObjectConverterToString.convertObject(turnOutcome));
                request.getRequestDispatcher("fight.jsp").forward(request, response);
            } else {
                response.sendRedirect("/login");
            }

        } else {
            response.sendRedirect("/login");
        }
    }

    private TurnOutcomeModel resolveRoundOutcome(FightParametersModel fightParameters) {
        IFightService fs;

        if (fightParameters.firstRound) {
            FirstRoundModel firstRound = getFirstRoundModel(fightParameters);

            if (NotNullOrEmpty.paramsNotNullOrEmpty(firstRound)) {
                fs = new FightService();
                return fs.getTurnOutcomeForFirstRound(createTurnStatsModelFromRoundModel(firstRound, null));
            }

        } else {
            OtherRoundModel otherRound = getOtherRoundModel(fightParameters);

            if (NotNullOrEmpty.paramsNotNullOrEmpty(otherRound)) {
                fs = new FightService();
                return fs.getTurnOutcome(createTurnStatsModelFromRoundModel(null, otherRound));
            }
        }

        return null;
    }


    private FightParametersModel getFightParametersModel(HttpServletRequest request, UserModel user) {
        FightParametersModel fightParametersModel = new FightParametersModel();
        fightParametersModel.fightId = request.getParameter("fightId");
        fightParametersModel.userName = user.name;
        fightParametersModel.userId = user.id;
        // TODO problem here ... Buna kad nepagauna 'round' parameter ir paraso 999
        fightParametersModel.round = request.getParameter("round") != null
                                        ? Integer.parseInt(request.getParameter("round")) : 999;
        fightParametersModel.att1 = request.getParameter("att1");
        fightParametersModel.att2 = request.getParameter("att2");
        fightParametersModel.def1 = request.getParameter("def1");
        fightParametersModel.def2 = request.getParameter("def2");
        fightParametersModel.firstRound = request.getParameter("firstRound") != null
                                        && Boolean.parseBoolean(request.getParameter("firstRound"));

        return fightParametersModel;
    }

    private FirstRoundModel getFirstRoundModel(FightParametersModel fightParameters) {
        FirstRoundModel firstRound = new FirstRoundModel();
        firstRound.userId = fightParameters.userId;
        firstRound.userName = fightParameters.userName;
        firstRound.fightId = fightParameters.fightId;
        return firstRound;
    }

    private OtherRoundModel getOtherRoundModel(FightParametersModel fightParameters) {
        OtherRoundModel otherRound = new OtherRoundModel();
        otherRound.fightId = fightParameters.fightId;
        otherRound.userId = fightParameters.userId;
        otherRound.userName = fightParameters.userName;
        otherRound.round = fightParameters.round;
        otherRound.att1 = BodyParts.valueOf(fightParameters.att1);
        otherRound.att2 = BodyParts.valueOf(fightParameters.att2);
        otherRound.def1 = BodyParts.valueOf(fightParameters.def1);
        otherRound.def2 = BodyParts.valueOf(fightParameters.def2);
        return otherRound;
    }

    private TurnStatsModel createTurnStatsModelFromRoundModel(FirstRoundModel firstRound, OtherRoundModel otherRound) {
        TurnStatsModel turnStats = new TurnStatsModel();

        if (firstRound != null) {

            turnStats.fightId = firstRound.fightId;
            turnStats.userId = firstRound.userId;
            turnStats.userName = firstRound.userName;

        } else if (otherRound != null){

            turnStats.fightId = otherRound.fightId;
            turnStats.userId = otherRound.userId;
            turnStats.userName = otherRound.userName;
            turnStats.round = otherRound.round;
            turnStats.att1 = otherRound.att1;
            turnStats.att2 = otherRound.att2;
            turnStats.def1 = otherRound.def1;
            turnStats.def2 = otherRound.def2;

        }

        return turnStats;
    }
}
