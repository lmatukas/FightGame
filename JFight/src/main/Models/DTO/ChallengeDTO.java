package main.Models.DTO;

import main.Models.DAL.ChallengeDAL;

import java.util.List;

public class ChallengeDTO {
    public boolean success;
    public String message;
    public List<ChallengeDAL> list;

    public ChallengeDTO(boolean Success, String Message, List<ChallengeDAL> List) {
        success = Success;
        message = Message;
        list = List;
    }
}