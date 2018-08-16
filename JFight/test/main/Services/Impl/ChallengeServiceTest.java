package main.Services.Impl;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ChallengeServiceTest {

    ChallengeService challengeService = new ChallengeService();
    long userId = 1;
    String userName = "testing";
    String[] arr = {"test", "testing"};

    @Test
    public void addPlayerToReadyToFight() {
        assertNotNull(challengeService.addPlayerToReadyToFight(userId, userName));
    }

    @Test
    public void checkIfUserGotMatched() {
        assertNotNull(challengeService.checkIfUserGotMatched(userId));
    }

    @Test
    public void createFightForMatchedPlayers() {
        assertNotNull(challengeService.createFightForMatchedPlayers(userId));
    }

    @Test
    public void getIssuedChallenges() {
        assertNotNull(challengeService.getIssuedChallenges(userId));
    }

    @Test
    public void getReadyToFightUsersExceptPrimaryUser() {
        assertNotNull(challengeService.getReadyToFightUsersExceptPrimaryUser(userId));
    }

    @Test
    public void submitChallenges() {
        assertNotNull(challengeService.submitChallenges(userId, arr));
    }

    @Test
    public void mainFightProcedure() {
        assertNotNull(challengeService.mainFightProcedure(userId,arr));
    }

}