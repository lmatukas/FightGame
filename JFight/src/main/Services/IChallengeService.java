package main.Services;

import main.Models.DTO.FightDTO;
import main.Models.DTO.IssuedChallengesDTO;
import main.Models.DTO.ReadyToFightDTO;

public interface IChallengeService {
    boolean addPlayerToReadyToFight(long userId, String username);

    boolean checkIfUserGotMatched(long userId);

    FightDTO createFightForMatchedPlayers(long userId);

    IssuedChallengesDTO getIssuedChallenges(long userId);

    ReadyToFightDTO getReadyToFightUsersExceptPrimaryUser(long userId);

    boolean submitChallenges(long userId, String[] challengedPlayers);

    FightDTO mainFightProcedure(long userId, String[] challengedPlayers);
}
