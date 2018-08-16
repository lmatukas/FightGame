package main.Services.Impl;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Models.CONS.BodyParts;
import main.Models.CONS.FightStatus;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.FightResultDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightLogDTO;
import main.Services.IFightService;
import main.Services.IHigherService;

public class FightService implements IFightService {

    private IHigherService hs = new HigherService();

    public TurnOutcomeModel getTurnOutcome(TurnStatsModel userModel) {
        setHealthPoints(userModel);
        DBqueryDTO dto = insertTurnFightLog(createFightLogFromTurnStatsModel(userModel));
        // TODO fix the null part once we can
        if (!dto.success) {
            return null;
        }

        // TODO handle timeout
        FightLogDTO fightLogDTO = checkForOpponent(userModel.fightId, userModel.round);

        if (!fightLogDTO.success) {
            return null;
        }

        TurnStatsModel opponent = new TurnStatsModel();
        for (FightLogDAL dal : fightLogDTO.list) {
            if (dal.userId != userModel.userId) {
                opponent = createTurnStatsModelFromFightLog(dal);
                opponent.userName = hs.getUserByUserId(dal.userId).user.userName;
                break;
            }
        }
        // Here we should get Opponent previous round HP not currently submitted.
        setHealthPoints(opponent);

        TurnOutcomeModel turnOutcome = calculateOutcome(userModel, opponent);

        fightCleanup(turnOutcome);

        userModel.hp = turnOutcome.userHp;

        hs.updateFightLogHPbyUserIdAndRound(createFightLogFromTurnStatsModel(userModel));

        return turnOutcome;
    }

    public TurnOutcomeModel getTurnOutcomeForFirstRound(TurnStatsModel userModel) {
        FightLogDTO dto = hs.getFightLogByIdAndRound(userModel.fightId, 1);

        if (dto.success) {
            TurnOutcomeModel outcome = new TurnOutcomeModel();
            outcome.fightId = userModel.fightId;
            outcome.round = 1;
            outcome.userId = userModel.userId;
            outcome.userName = userModel.userName;
            outcome.fightStatus = FightStatus.FIGHTING;

            for (FightLogDAL dal : dto.list) {
                if (dal.userId == userModel.userId) {
                    outcome.userHp = dal.hp;
                } else {
                    outcome.oppHp = dal.hp;
                    // TODO handle errors
                    outcome.oppName = hs.getUserByUserId(dal.userId).user.userName;
                }
            }
            return outcome;
        }
        return null;
    }

    private TurnOutcomeModel calculateOutcome(TurnStatsModel user, TurnStatsModel opponent) {
        // TODO create FIGHT LOG
        TurnOutcomeModel turnOutcome = calculateDamage(user, opponent);

        // TODO Where/when should i Add 1 to Round -> here or with JS
//        turnOutcome.round = user.round + 1;

        determineFightStatus(turnOutcome);

        turnOutcome.fightId = user.fightId;
        turnOutcome.round = user.round;
        turnOutcome.userName = user.userName;
        turnOutcome.userId = user.userId;
        turnOutcome.oppName = opponent.userName;
        turnOutcome.oppId = opponent.userId;

        return turnOutcome;
    }

    private TurnOutcomeModel calculateDamage(TurnStatsModel user, TurnStatsModel opponent) {
        TurnOutcomeModel turnOutcome = new TurnOutcomeModel();
        // TODO in the future damage variable will change depending on items/skills
        int damage = 1;

        // User outcome
        int attacksReceivedUser = 0;
        // Check if user defends against first Opponent attack
        if (user.def1 != opponent.att1 && user.def2 != opponent.att1) attacksReceivedUser++;
        // Check if user defends against second Opponent attack
        if (user.def1 != opponent.att2 && user.def2 != opponent.att2) attacksReceivedUser++;

        turnOutcome.userHp = user.hp - (attacksReceivedUser * damage);

        // Opponent outcome
        int attacksReceivedOpp = 0;
        if (opponent.def1 != user.att1 && opponent.def2 != user.att1) attacksReceivedOpp++;
        if (opponent.def1 != user.att2 && opponent.def2 != user.att2) attacksReceivedOpp++;

        turnOutcome.oppHp = opponent.hp - (attacksReceivedOpp * damage);
        return turnOutcome;
    }

    private FightLogDTO checkForOpponent(String fightId, int round) {
        FightLogDTO dto = hs.getFightLogByIdAndRound(fightId, round);
        // TODO we will need a timeout counter if we cannot get result or opponent leaves
        int count = 0;
        while ((!dto.success && dto.message.equals("Only one record found.")) && count < 30) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dto = hs.getFightLogByIdAndRound(fightId, round);
            count++;
        }
        return dto;
    }

    private boolean checkIfWinResultAlreadyAdded(String fightId) {
        // TODO should return more than just boolean in case of DB error
        return hs.getFightResultByFightId(fightId).success;
    }

    private FightLogDAL createFightLogFromTurnStatsModel(TurnStatsModel model) {
        FightLogDAL fightLog = new FightLogDAL();
        fightLog.userId = model.userId;
        fightLog.fightId = model.fightId;
        // TODO check what happens if BodyParts.valueOf('INVALID NAME'); ---> IllegalArgumentException
        fightLog.attack1 = model.att1 != null ? model.att1.toString() : "NONE";
        fightLog.attack2 = model.att2 != null ? model.att2.toString() : "NONE";
        fightLog.defence1 = model.def1 != null ? model.def1.toString() : "NONE";
        fightLog.defence2 = model.def2 != null ? model.def2.toString() : "NONE";
        fightLog.round = model.round;
        fightLog.hp = model.hp;
        return fightLog;
    }

    private TurnStatsModel createTurnStatsModelFromFightLog(FightLogDAL fightLog) {
        TurnStatsModel turnStatsModel = new TurnStatsModel();
        turnStatsModel.userId = fightLog.userId;
        turnStatsModel.fightId = fightLog.fightId;
        turnStatsModel.round = fightLog.round;
        turnStatsModel.att1 = BodyParts.valueOf(fightLog.attack1);
        turnStatsModel.att2 = BodyParts.valueOf(fightLog.attack2);
        turnStatsModel.def1 = BodyParts.valueOf(fightLog.defence1);
        turnStatsModel.def2 = BodyParts.valueOf(fightLog.defence2);
        turnStatsModel.hp = fightLog.hp;
        return turnStatsModel;
    }

    private boolean deleteFightLogsAndFight(String fightId) {
        DBqueryDTO dto = hs.deleteAllFightLogsByFightId(fightId);

        if (!dto.success) {
            return false;
        }

        dto = hs.deleteFightByFightId(fightId);

        return dto.success;
    }

    private void determineFightStatus(TurnOutcomeModel turnOutcome) {

        if (turnOutcome.userHp <= 0 && turnOutcome.oppHp <= 0) {
            turnOutcome.fightStatus = FightStatus.DRAW;
        } else if (turnOutcome.userHp <= 0) {
            turnOutcome.fightStatus = FightStatus.LOSER;
        } else if (turnOutcome.oppHp <= 0) {
            turnOutcome.fightStatus = FightStatus.WINNER;
        } else {
            turnOutcome.fightStatus = FightStatus.FIGHTING;
        }

    }

    private void fightCleanup(TurnOutcomeModel outcome) {
        // First check if Winner has already been inserted!
        // If he was then you can safely delete Fight, FightLog records with that FightId;
        if (outcome.fightStatus != FightStatus.FIGHTING) {

            if (checkIfWinResultAlreadyAdded(outcome.fightId)) {

                // TODO needs error handling
                deleteFightLogsAndFight(outcome.fightId);

            } else {

                FightResultDAL fightResult = new FightResultDAL();
                fightResult.fightId = outcome.fightId;

                if (outcome.fightStatus == FightStatus.WINNER) {
                    fightResult.winnerId = outcome.userId;
                    fightResult.loserId = outcome.oppId;
                } else if (outcome.fightStatus == FightStatus.LOSER) {
                    fightResult.winnerId = outcome.oppId;
                    fightResult.loserId = outcome.userId;
                } else if (outcome.fightStatus == FightStatus.DRAW) {
                    fightResult.draw = 1;
                }

                // TODO needs error handling
                hs.insertFightResults(fightResult);
            }
        }
    }

    private DBqueryDTO insertTurnFightLog(FightLogDAL model) {
        return hs.insertTurnStats(model);
    }

    private void setHealthPoints(TurnStatsModel turnStats) {
        // We need to check for previous round FightLog
        FightLogDTO dto = hs.getFightLogByIdAndRound(turnStats.fightId, turnStats.round - 1);

        // TODO this needs more thought
        if (dto.success) {
            for (FightLogDAL dal : dto.list){
                if (turnStats.userId == dal.userId) {
                    turnStats.hp = dal.hp;
                    break;
                }
            }
        }
    }

}
