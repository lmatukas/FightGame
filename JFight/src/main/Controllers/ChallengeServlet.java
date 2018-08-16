package main.Controllers;

import main.Models.BL.UserModel;
import main.Models.DTO.FightDTO;
import main.Models.DTO.IssuedChallengesDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Services.ICache;
import main.Services.IChallengeService;
import main.Services.Impl.Cache;
import main.Services.Impl.ChallengeService;
import main.Services.Impl.LoginService;
import main.Services.Helpers.ObjectConverterToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChallengeServlet", urlPatterns = {"/challenge"})
public class ChallengeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());

        if (token != null && loginService.validate(token)) {
            IChallengeService cs = new ChallengeService();
            ICache cache = Cache.getInstance();
            UserModel user = (UserModel) cache.get(token.getValue());
            request.setAttribute("userName", user.name);

            if (!cs.addPlayerToReadyToFight(user.id, user.name)) {
                // TODO should give a message to Front that an error has occurred.
            }

            if (request.getParameter("challengedPlayers") != null) {

                String[] challengedPlayers = request.getParameter("challengedPlayers").split("#");

                // TODO show the user that he is waiting for a match
                // What should we do when a user challenges someone and they challenge him after 15seconds.
                // Find out any possibilities of semi refresh of page

                if (cs.submitChallenges(user.id, challengedPlayers)) {

                    if (cs.checkIfUserGotMatched(user.id)) {

                        FightDTO fightDTO = cs.createFightForMatchedPlayers(user.id);

                        if (fightDTO.success) {
//                            request.getRequestDispatcher("/fight?fightId=" + fightDTO.dal.fightId +
////                                    "&userId=" + user.id + "&round=0" + "&firstRound=true").forward(request, response);
                            response.sendRedirect("/fight?fightId=" + fightDTO.dal.fightId +
                                    "&userId=" + user.id + "&round=0" + "&firstRound=true");
                            return;
                        } else {
                            System.out.println("ERROR -----> " + fightDTO.message);
                        }

                    } else {
                        IssuedChallengesDTO issuedChallengesDTO = cs.getIssuedChallenges(user.id);

                        if (issuedChallengesDTO.success) {
                            request.setAttribute("userChallenges", ObjectConverterToString.convertList(issuedChallengesDTO.issuedChallenge.userChallenges));
                            request.setAttribute("oppChallenges", ObjectConverterToString.convertList(issuedChallengesDTO.issuedChallenge.oppChallenges));
                        }
                    }
                }

            }

            // UserModel has entered the challenge page for the first time or no matches found, return him all players Ready to Fight
            ReadyToFightDTO readyDTO = cs.getReadyToFightUsersExceptPrimaryUser(user.id);

            if (readyDTO.list.size() > 0) {
                request.setAttribute("readyToFightList", ObjectConverterToString.convertList(readyDTO.list));
                readyDTO.list.forEach(el -> System.out.println("Users in ReadyToFight -> " + el.userName));
                request.getRequestDispatcher("/challenge.jsp").forward(request, response);
            } else {
                System.out.println("NO USERS INSIDE LIST");
                request.getRequestDispatcher("/challenge.jsp").forward(request, response);
            }

        }

        response.sendRedirect("/login");
    }
}
