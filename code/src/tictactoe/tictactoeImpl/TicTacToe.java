package tictactoeImpl;

import tictactoeImpl.Strategy.DiagonalWinningStrategy;
import tictactoeImpl.Strategy.HorizontalWinningStrategy;
import tictactoeImpl.Strategy.VerticalWinningStrategy;
import tictactoeImpl.Strategy.WinningStrategy;

import java.util.List;

public class TicTacToe implements Game{
    Board board;
    List<Player> players;
    GameStatus gameStatus;
    int currentPlayerIdx;


    public TicTacToe(Player player1, Player player2) {
        List<WinningStrategy> strategies = List.of(
            new HorizontalWinningStrategy(),
            new VerticalWinningStrategy(),
            new DiagonalWinningStrategy()
        );

        this.board = TicTacToeBoard.getInstance(strategies);
        this.players = List.of(player1, player2);
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.currentPlayerIdx = 0;
    }

    @Override
    public void playMove(Player player, int x, int y) {
        if (gameStatus != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }

        if (!players.contains(player)) {
            throw new IllegalArgumentException("Player not part of this game");
        }

        TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;

        ticTacToeBoard.placeMark(x, y, player.getSymbol());

        Player winner = board.checkWinner(players.get(0), players.get(1));
        if (winner != null) {
            gameStatus = GameStatus.WIN;
            System.out.println("Winner: " + winner.getName());
        } else if (ticTacToeBoard.isFull()) {
            gameStatus = GameStatus.DRAW;
            System.out.println("Game is a draw");
        }
        moveToNextPlayer();
    }

    @Override
    public void resetGame() {
        board.reset();
    }

    @Override
    public GameStatus getStatus() {
        return GameStatus.IN_PROGRESS;
    }

    @Override
    public void printBoard() {
        board.showBoard();
    }

    @Override
    public boolean isGameOver() {
        return gameStatus != GameStatus.IN_PROGRESS;
    }

    private void moveToNextPlayer() {
        currentPlayerIdx = players.indexOf(players.get((currentPlayerIdx + 1) % players.size()));
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }


}
