package main.Services;

import main.Models.DAL.*;
import main.Models.DTO.*;

public interface IHigherService {
    ChallengeDTO checkIfTwoUsersChallengedEachOther(long userId);

    DBqueryDTO deleteFightLogByUserId(long userId);

    DBqueryDTO deleteAllFightLogsByFightId(String fightId);

    DBqueryDTO deleteFightByFightId(String fightId);

    DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId);

    DBqueryDTO deleteUserAndOpponentFromReadyToFight(long userId, long opponentId);

    ChallengeDTO getAllIssuedChallengesByUserId(long userId);

    ReadyToFightDTO getAllReadyToFightUsers();

    FightDTO getFightByUserId(long userId);

    FightLogDTO getFightLogByIdAndRound(String fightId, int round);

    FightResultDTO getFightResultByFightId(String fightId);

    UserDTO getUserByEmail(String email);

    UserDTO getUserByEmailAndPass(UserDAL user);

    UserDTO getUserByUserId(long userId);

    UserDTO getUserByUserNameAndEmail(String userName, String email);

    UserExtendedDTO getUserExtendByUserId(long userId);

    ReadyToFightDTO getUserFromReadyToFightByUserId(long userId);

    DBqueryDTO insertChallengedPlayers(ChallengeDAL dal);

    DBqueryDTO insertFightResults(FightResultDAL fightResults);

    DBqueryDTO insertNewFight(ChallengeDAL dal);

    DBqueryDTO insertNewUserExtended(UserExtendedDAL userExtendedDAL);

    DBqueryDTO insertTurnStats(FightLogDAL fightLog);

    DBqueryDTO insertUserToReadyToFightTable(ReadyToFightDAL readyUserDal);

    DBqueryDTO registerUser(UserDAL user);

    DBqueryDTO updateFightLogHPbyUserIdAndRound(FightLogDAL fightLog);
}
