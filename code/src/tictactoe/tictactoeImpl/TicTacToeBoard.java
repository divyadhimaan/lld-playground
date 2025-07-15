package tictactoeImpl;

import tictactoeImpl.Strategy.WinningStrategy;

import java.util.List;

public class TicTacToeBoard implements Board{

    private static TicTacToeBoard board;
    private final Cell[][] grid;
    private int movesCount;
    private static final int SIZE = 3;
    GameController controller;

    private TicTacToeBoard(List<WinningStrategy> strategies) {
        this.grid = new Cell[3][3];
        movesCount = 0;
        initializeBoard();
        controller = GameController.getInstance(strategies);
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public static synchronized Board getInstance(List<WinningStrategy> strategies) {
        if (board == null) {
            board = new TicTacToeBoard(strategies);
        }
        return board;
    }

    @Override
    public void placeMark(int x, int y, Symbol symbol) {
        controller.markCell(x, y, symbol, grid);
        showBoard();
    }

    @Override
    public boolean isFull() {
        return controller.isBoardFull(this, grid);
    }

    @Override
    public Player checkWinner(Player player1, Player player2) {

        //check if player 1 is winner
        if(controller.checkWin(board, player1.getSymbol()))
            return player1;

        else if(controller.checkWin(board, player2.getSymbol()))
            return player2;

        return null;
    }

    @Override
    public void reset() {
        movesCount = 0;
        initializeBoard();
    }

    @Override
    public void showBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(grid[i][j].getSymbol() == Symbol.EMPTY) {
                    System.out.print("-" + " ");
                }else {
                    System.out.print(grid[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE ;
    }

    public Symbol getSymbol(int x, int y) {
        if (!isValidMove(x, y)) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        return grid[x][y].getSymbol();
    }

    public int getSize(){
        return SIZE;
    }


}
