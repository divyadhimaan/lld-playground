package tictactoeImpl;

public interface Board {

    void placeMark(int x, int y, Symbol symbol);

    boolean isFull();

    Player checkWinner(Player player1, Player player2);

    void reset();

    void showBoard();
}
