package main.Models.DAL;

public class ChallengeDAL {
    public long userId;
    public long opponentId;

    public ChallengeDAL(long userId, long opponentId) {
        this.userId = userId;
        this.opponentId = opponentId;
    }

    public ChallengeDAL(){}
}
