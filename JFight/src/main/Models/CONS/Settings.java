package main.Models.CONS;

public final class Settings {
    //Its the amount of time items will be saved in Cache.class, now its set to 12h seconds
    public final static long LIVE_TIME = 43200000;
    // This constant signifies how many players can play at one time and will be used where we need to check if list
    // is of size 2
    public static final int NUMBER_OF_PLAYERS = 2;
    // Our cookie name which will be used to parse Cookie[]
    public final static String COOKIE_NAME = "token";
    //Checks needed parameters in Challenge servlet
    public final static String[] CHALLENGE_PARAMETERS = new String[]{"challengedPlayers"};
    //Checks initial fight parameters when players get matched
    public final static String[] INITIAL_FIGHT_PARAMETERS = new String[]{"round", "fightId", "userId", "firstRound"};
    //Checks parameters for each fight round
    public final static String[] FIGHT_PARAMETERS = new String[]{"userName", "oppName", "att1", "att2", "def1", "def2", "userHp"};
}
