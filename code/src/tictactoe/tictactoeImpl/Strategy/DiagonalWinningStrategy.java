package tictactoeImpl.Strategy;

import tictactoeImpl.Board;
import tictactoeImpl.Symbol;
import tictactoeImpl.TicTacToeBoard;

public class DiagonalWinningStrategy implements WinningStrategy {


    @Override
    public boolean checkWinner(Board board, Symbol symbol) {
        if(!(board instanceof TicTacToeBoard ticTacToeBoard)) {
            throw new IllegalArgumentException("Invalid board type");
        }
        // Check the main diagonal
        if (ticTacToeBoard.getSymbol(0, 0) == symbol && ticTacToeBoard.getSymbol(1, 1) == symbol && ticTacToeBoard.getSymbol(2, 2) == symbol) {
            return true;
        }
        // Check the anti-diagonal
        if (ticTacToeBoard.getSymbol(0, 2) == symbol && ticTacToeBoard.getSymbol(1, 1) == symbol && ticTacToeBoard.getSymbol(2, 0) == symbol) {
            return true;
        }
        return false;
    }
}
