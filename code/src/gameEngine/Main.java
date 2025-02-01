
import java.util.Scanner;

import api.AiEngine;
import api.GameEngine;
import api.RuleEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

public class Main {
    public static void main(String[] args){
        GameEngine gameEngine = new GameEngine();
        AiEngine aiEngine = new AiEngine();
        RuleEngine ruleEngine = new RuleEngine();
        Board board = gameEngine.start("TicTacToe");
        Scanner scanner = new Scanner(System.in);
        while(!ruleEngine.getGameState(board).isOver())
        {
            Player computer = new Player("O");
            Player human =  new Player("X");

            System.out.println("Make your move!");
            System.out.println(board);
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if(ruleEngine.isValidCell(row,col))
            {
                Move humanMove = new Move(new Cell(row, col), human);
                if(ruleEngine.isValidMove(humanMove, board, row, col)) {
                    gameEngine.move(board, humanMove);
                }else{
                    throw new IllegalStateException("This move is not allowed. Enter a valid cell.");
                }
            }else{
                throw new IllegalStateException("This move is not allowed.Choose an empty cell.");
            }

            if(!ruleEngine.getGameState(board).isOver()){
                Move computerMove = aiEngine.suggestMove(computer, board);
                gameEngine.move(board, computerMove);
            }
        }
        System.out.println("Game result: " + ruleEngine.getGameState(board).showResult());
        System.out.println(board);
    }

}
