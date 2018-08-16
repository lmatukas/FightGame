package main.Models.DAL;

public class FightDAL {
    public String fightId;
    public long userId1;
    public long userId2;

    public FightDAL(String _fightId, long _userId1, long _userId2) {
        fightId = _fightId;
        userId1 = _userId1;
        userId2 = _userId2;
    }

    public FightDAL(){}
}
