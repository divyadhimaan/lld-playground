package tictactoeImpl;

public interface Game {
    void playMove(Player player, int x, int y);
    void resetGame();

    GameStatus getStatus();
    void printBoard();

    boolean isGameOver();
}
