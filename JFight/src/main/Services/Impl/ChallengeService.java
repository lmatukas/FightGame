package main.Services.Impl;

import main.Models.BL.IssuedChallengesModel;
import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DTO.*;
import main.Services.IChallengeService;
import main.Services.IHigherService;

import java.util.ArrayList;
import java.util.List;

public class ChallengeService implements IChallengeService {

    private IHigherService hs = new HigherService();
    private DBqueryDTO dto;

    @Override
    public boolean addPlayerToReadyToFight(long userId, String username) {
        ReadyToFightDTO userIsInReadyToFight = checkIfUserIsInReadyToFight(userId);
        // TODO use Enums
        if (!userIsInReadyToFight.success && userIsInReadyToFight.message.equals("Such user not found in ReadyToFight")) {
            dto = hs.insertUserToReadyToFightTable(new ReadyToFightDAL(userId, username));
            return dto.success;
        }

        return userIsInReadyToFight.success;
    }

    private ReadyToFightDTO checkIfUserIsInReadyToFight(long userId) {
        return hs.getUserFromReadyToFightByUserId(userId);
    }

    @Override
    public boolean checkIfUserGotMatched(long userId){
        ChallengeDTO challengeDTO = hs.checkIfTwoUsersChallengedEachOther(userId);
        int count = 0;

        while(!challengeDTO.success && count < 15) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            challengeDTO = hs.checkIfTwoUsersChallengedEachOther(userId);
            count++;
        }
        return challengeDTO.success && challengeDTO.list.size() > 0;
    }

    @Override
    public FightDTO createFightForMatchedPlayers(long userId) {
        FightDTO fightDTO = isFightAlreadyCreated(userId);

        // TODO use enums here
        if (!fightDTO.success && fightDTO.message.equals("No fight has been found.")) {
            // Fight has not been created so we move create new Fight and remove users from Challenge, ReadyTOFight tables
            ChallengeDTO challengeDTO = hs.checkIfTwoUsersChallengedEachOther(userId);

            if (!challengeDTO.success) {
                System.out.println("CHECK IF TWO USERS CRASH");
                return new FightDTO(false, challengeDTO.message, null);
            }

            ChallengeDAL challengeDAL = challengeDTO.list.get(0);
            dto = hs.insertNewFight(challengeDAL);
            if (!dto.success) {
                System.out.println("NEW FIGHT CREATION ERROR");
                return new FightDTO(false, dto.message, null);
            }

            FightDTO fightDTO2 = hs.getFightByUserId(userId);
            if (!fightDTO2.success) {
                System.out.println("GET FIGHT BY USER ID CRASH");
                return new FightDTO(false, fightDTO2.message, null);
            }

            dto = insertFirstRoundStatsBeforeFight(fightDTO2);
            if (!dto.success) {
                System.out.println("INSERT ZERO ROUND STATS");
                return new FightDTO(false, dto.message, null);
            }

            return fightDTO2;

        } else if (fightDTO.success){
            // Fight was already created so delete user from Challenge and ReadyToFight tables
            // TODO check the query if this is actually necessary -> is it possible for OpponnentId to be UserId ???
            long opponentId = fightDTO.dal.userId1 != userId ? fightDTO.dal.userId1 : fightDTO.dal.userId2;

            dto = deleteMatchedUsersFromChallengeAndReadyToFight(userId, opponentId);
            if (!dto.success) {
                System.out.println("DELETE FROM TABLES CRASH");
                return new FightDTO(false, dto.message, null);
            }

            return fightDTO;
        }

        // Fight is already created or DB crash
        return fightDTO;
    }

    private DBqueryDTO deleteMatchedUsersFromChallengeAndReadyToFight(long userId, long opponentId) {
        DBqueryDTO dto = hs.deleteMatchedPlayersFromChallenge(userId, opponentId);

        if (!dto.success) {
            // TODO log error message here
            return dto;
        }

        dto = hs.deleteUserAndOpponentFromReadyToFight(userId, opponentId);

        if (!dto.success) {
            // TODO log error message here
            return dto;
        }

        return dto;
    }

    @Override
    public IssuedChallengesDTO getIssuedChallenges(long userId) {
        ChallengeDTO challengeDTO = hs.getAllIssuedChallengesByUserId(userId);
        if (!challengeDTO.success) {
            return new IssuedChallengesDTO(false, "", null);
        } else if (challengeDTO.list.isEmpty()) {
            // TODO use ENUM
            return new IssuedChallengesDTO(false, "No one challenged the player.", null);
        }

        List<Long> userChallenges = new ArrayList<>();
        List<Long> oppChallenges = new ArrayList<>();

        challengeDTO.list.forEach(challenge -> {

            if (challenge.userId == userId) {
                userChallenges.add(challenge.opponentId);
            } else {
                oppChallenges.add(challenge.userId);
            }

        });

        return new IssuedChallengesDTO(true, "", new IssuedChallengesModel(userId, "", userChallenges, oppChallenges));
    }

    @Override
    public ReadyToFightDTO getReadyToFightUsersExceptPrimaryUser(long userId) {
        ReadyToFightDTO readyToFightDTO = hs.getAllReadyToFightUsers();

        if (readyToFightDTO.success) {
            for (int i = 0; i < readyToFightDTO.list.size(); i++) {
                if (readyToFightDTO.list.get(i).userId == userId) {
                    readyToFightDTO.list.remove(i);
                    break;
                }
            }
        }

        return readyToFightDTO;
    }

    @Override
    public boolean submitChallenges(long userId, String[] challengedPlayers) {

        for (String player : challengedPlayers) {
            ChallengeDAL challenged = new ChallengeDAL(userId, Long.parseLong(player));

            dto = hs.insertChallengedPlayers(challenged);
            if (!dto.success) {
                // TODO we should use a logger here to log any errors
                return false;
            }

        }

        return true;
    }

    @Override
    public FightDTO mainFightProcedure(long userId, String[] challengedPlayers) {
        if (!submitChallenges(userId, challengedPlayers)) {
            // TODO use enums
            return new FightDTO(false, "Failed to submit challenges.", null);
        }

        if (!checkIfUserGotMatched(userId)) {
            return new FightDTO(false, "User has no matches", null);
        }

        return createFightForMatchedPlayers(userId);
    }

    private DBqueryDTO insertFirstRoundStatsBeforeFight(FightDTO fightDTO) {
        // TODO we need to get stats from user Character table (not implemented)
        int hp = 10;
        int round = 1; //stats before fight

        FightLogDAL fightLog = new FightLogDAL();
        fightLog.userId = fightDTO.dal.userId1;
        fightLog.fightId = fightDTO.dal.fightId;
        fightLog.round = round;
        fightLog.hp = hp;

        dto = hs.insertTurnStats(fightLog);
        if (!dto.success) {
            // TODO log error
            return dto;
        }

        fightLog.userId = fightDTO.dal.userId2;

        dto = hs.insertTurnStats(fightLog);
        if (!dto.success) {
            // TODO log error
            return dto;
        }

        return dto;
    }

    private FightDTO isFightAlreadyCreated(long userId) {
        FightDTO fightDTO = hs.getFightByUserId(userId);

        if (fightDTO.success) {
            return new FightDTO(true, "Fight has been already created.", fightDTO.dal);
        } else if (fightDTO.message.equals("No Fights with such UserId found.")){
            return new FightDTO(false, "No fight has been found.", null);
        }

        // DB has crashed
        return new FightDTO(false, dto.message, null);
    }
}
