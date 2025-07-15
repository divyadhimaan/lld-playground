package tictactoeImpl;

import tictactoeImpl.Strategy.WinningStrategy;

import java.util.List;

public class GameController {
    private List<WinningStrategy> winnningStrategies;
    private static GameController controller;


    private GameController(List<WinningStrategy> strategies)
    {
        this.winnningStrategies = strategies;

    }

    public static synchronized GameController getInstance(List<WinningStrategy> strategies) {
        if (strategies == null || strategies.isEmpty()) {
            throw new IllegalArgumentException("Strategies cannot be null or empty");
        }
        if (controller == null) {
            controller = new GameController(strategies);
        }
        return controller;
    }

    boolean isBoardFull(Board board, Cell[][] grid) {
        if (!isValidBoard(board)) {
            throw new IllegalArgumentException("Invalid board type");
        }
        TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;

        for (int i = 0; i < ticTacToeBoard.getSize(); i++) {
            for (int j = 0; j < ticTacToeBoard.getSize(); j++) {
                if (grid[i][j].getSymbol() == Symbol.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean checkWin(Board board, Symbol symbol) {
        if (!isValidBoard(board)) {
            throw new IllegalArgumentException("Invalid board type");
        }

        for (WinningStrategy strategy : winnningStrategies) {
            if (strategy.checkWinner(board, symbol)) {
                return true;
            }
        }
        return false;
    }

    void markCell(int x, int y, Symbol symbol, Cell[][] grid) {
        if (!isValidMove(x, y, grid.length)) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }

        if(grid[x][y].getSymbol() != Symbol.EMPTY)
            System.out.print("Cell is already used. Please choose another cell.");

        grid[x][y].setSymbol(symbol);
    }

    private boolean isValidMove(int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private boolean isValidBoard(Board board) {
        return (board instanceof TicTacToeBoard ticTacToeBoard);
    }

}
