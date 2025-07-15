package tictactoeImpl.Strategy;

import tictactoeImpl.Board;
import tictactoeImpl.Symbol;
import tictactoeImpl.TicTacToeBoard;

public class HorizontalWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWinner(Board board, Symbol symbol) {
        if(!(board instanceof TicTacToeBoard ticTacToeBoard)) {
            throw new IllegalArgumentException("Invalid board type");
        }

        for(int i = 0; i < ticTacToeBoard.getSize(); i++) {
            if (ticTacToeBoard.getSymbol(i, 0) == symbol && ticTacToeBoard.getSymbol(i, 1) == symbol && ticTacToeBoard.getSymbol(i, 2) == symbol) {
                return true;
            }
        }
        return false;
    }
}
