package game;

// Any other relevant info about the game - follows Builder design pattern
public class GameInfo {

    private boolean isOver;
    private String winner;
    private boolean hasFork;
    private Player player;
    private int numberOfMoves;



    public GameInfo(boolean isOver, String winner, boolean hasFork, Player player, int numberOfMoves)
    {
        this.isOver = isOver;
        this.winner = winner;
        this.hasFork = hasFork;
        this.player = player;
        this.numberOfMoves = numberOfMoves;
    }

}

