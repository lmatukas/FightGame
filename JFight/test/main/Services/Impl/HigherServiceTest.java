package main.Services.Impl;

import main.Models.DAL.ReadyToFightDAL;
import main.Models.DAL.UserDAL;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class HigherServiceTest {

    HigherService higherService = new HigherService();

    @Test
    public void getAllReadyToFightUsers() {
        assertNotNull(higherService.getAllReadyToFightUsers());
    }

    @Test
    public void insertUserToReadyToFightTable() {
        assertNotNull(higherService.insertUserToReadyToFightTable(new ReadyToFightDAL()));
    }

    @Test
    public void deleteUserAndOpponentFromReadyToFight() {
        assertNotNull(higherService.deleteUserAndOpponentFromReadyToFight(999999999,0));
    }

    @Test
    public void insertNewFight() {
        assertNotNull(higherService.insertNewFight(null));
    }

    @Test
    public void getUserByEmailAndPass() {
        assertNotNull(higherService.getUserByEmailAndPass(new UserDAL()));
    }

    @Test
    public void insertTurnStats() {
        assertNotNull(higherService.insertTurnStats(null));
    }

    @Test
    public void getFightLogByIdAndRound() {
        assertNotNull(higherService.getFightLogByIdAndRound(null, 0));
    }

    @Test
    public void getFightByUserId() {
        assertNotNull(higherService.getFightByUserId(0));
    }

    @Test
    public void insertChallengedPlayers() {
        assertNotNull(higherService.insertChallengedPlayers(null));
    }

    @Test
    public void checkIfTwoUsersChallengedEachOther() {
        assertNotNull(higherService.checkIfTwoUsersChallengedEachOther(999999999));
    }

    @Test
    public void getAllIssuedChallengesByUserId() {
        assertNotNull(higherService.getAllIssuedChallengesByUserId(0));
    }

    @Test
    public void getUserByUserId() {
        assertNotNull(higherService.getUserByUserId(0));
    }

    @Test
    public void getUserFromReadyToFightByUserId() {
        assertNotNull(higherService.getUserFromReadyToFightByUserId(999999999));
    }

    @Test
    public void deleteMatchedPlayersFromChallenge() {
        assertNotNull(higherService.deleteMatchedPlayersFromChallenge(0,999999999));
    }

    @Test
    public void getUserByEmail() {
        assertNotNull(higherService.getUserByEmail(null));
    }

    @Test
    public void registerUser() {
        assertNotNull(higherService.registerUser(null));
    }

    @Test
    public void deleteFightLogByUserId() {
        assertNotNull(higherService.deleteFightLogByUserId(0));
    }

    @Test
    public void getUserByUserNameAndEmail() {
        assertNotNull(higherService.getUserByUserNameAndEmail(null,null));
    }

    @Test
    public void getUserExtendByUserId() {
        assertNotNull(higherService.getUserExtendByUserId(0));
    }
}