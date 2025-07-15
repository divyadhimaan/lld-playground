package tictactoeImpl.Strategy;

import tictactoeImpl.Board;
import tictactoeImpl.Symbol;
import tictactoeImpl.TicTacToeBoard;

public class VerticalWinningStrategy implements WinningStrategy{

    @Override
    public boolean checkWinner(Board board, Symbol symbol) {
        if(!(board instanceof TicTacToeBoard ticTacToeBoard)) {
            throw new IllegalArgumentException("Invalid board type");
        }

        for(int i = 0; i < ticTacToeBoard.getSize(); i++) {
            if (ticTacToeBoard.getSymbol(0, i) == symbol && ticTacToeBoard.getSymbol(1, i) == symbol && ticTacToeBoard.getSymbol(2, i) == symbol) {
                return true;
            }
        }
        return false;
    }
}
