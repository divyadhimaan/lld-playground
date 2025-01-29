import java.util.Scanner;
import api.GameEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

public class Main {
    public static void main(String[] args){
        GameEngine gameEngine = new GameEngine();
        Board board = gameEngine.start("TicTacToe");
        Scanner scanner = new Scanner(System.in);
        while(!gameEngine.isComplete(board).isOver())
        {
            Player computer = new Player("O");
            Player human =  new Player("X");

            System.out.println("Make your move!");
            System.out.println(board);
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            Move humanMove = new Move(new Cell(row, col), human);
            if(gameEngine.isValidMove(humanMove, board, row, col)) {
                gameEngine.move(board, humanMove);
            }
            else{
                throw new IllegalStateException("This move is not allowed.0 Choose an empty cell.");
            }

            if(!gameEngine.isComplete(board).isOver()){
                Move computerMove = gameEngine.suggestMove(computer, board);
                gameEngine.move(board, computerMove);
            }
        }
        System.out.println("Game result: " + gameEngine.isComplete(board).showResult());
        System.out.println(board);
    }

}
