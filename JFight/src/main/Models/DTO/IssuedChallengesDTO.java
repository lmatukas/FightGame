package main.Models.DTO;

import main.Models.BL.IssuedChallengesModel;

public class IssuedChallengesDTO {
    public boolean success;
    public String message;
    public IssuedChallengesModel issuedChallenge;

    public IssuedChallengesDTO(boolean Success, String Message, IssuedChallengesModel IssuedChallenge) {
        success = Success;
        message = Message;
        issuedChallenge = IssuedChallenge;
    }
}
