package tictactoeImpl.Strategy;

import tictactoeImpl.Board;
import tictactoeImpl.Symbol;

public interface WinningStrategy {
    boolean checkWinner(Board board, Symbol symbol);

}
