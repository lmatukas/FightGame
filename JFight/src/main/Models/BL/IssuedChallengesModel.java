package main.Models.BL;

import java.util.List;

public class IssuedChallengesModel {
    public long userId;
    public String userName;
    public List<Long> userChallenges;
    public List<Long> oppChallenges;

    public IssuedChallengesModel(long UserId, String UserName, List<Long> UserChallenges, List<Long> OppLong) {
        userId = UserId;
        userName = UserName;
        userChallenges = UserChallenges;
        oppChallenges = OppLong;
    }
}
