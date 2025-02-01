package game;

// Any other relevant info about the game
public class GameInfo {

    private boolean isOver;
    private String winner;
    private boolean hasFork;
    private Player player;

    public GameInfo(GameState gameState, boolean hasFork, Player player)
    {
        isOver = gameState.isOver();
        winner = gameState.getWinner();
        this.hasFork = hasFork;
        this.player = player;
    }
}
