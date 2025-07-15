package game;

import tictactoeImpl.Symbol;
import tictactoeImpl.TicTacToe;
import tictactoeImpl.TicTacToeBoard;
import tictactoeImpl.Player;

import java.util.Scanner;

public class TicTacToeGame {
    public static void main(String[] args){
        Player player1 = new Player("Player 1", Symbol.X);
        Player player2 = new Player("Player 2", Symbol.O);

        TicTacToe ticTacToeGame = new TicTacToe(player1, player2);

        ticTacToeGame.getStatus();

        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter coordinates for " + ticTacToeGame.getCurrentPlayer().getName() + "[" + ticTacToeGame.getCurrentPlayer().getSymbol() + "] (x y): ");
            ticTacToeGame.playMove(ticTacToeGame.getCurrentPlayer(), scanner.nextInt(), scanner.nextInt());
            ticTacToeGame.getStatus();
            if(ticTacToeGame.isGameOver()) {
                break;
            }
        }

    }
}
