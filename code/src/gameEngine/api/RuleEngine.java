package api;

import boards.TicTacToeBoard;
import game.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

// Rules, valid moves, winner, game ended? - Making sure rules are followed
public class RuleEngine {

    // board class name -> [Rules]
    Map<String, RuleSet> ruleMap = new HashMap<>();

    public RuleEngine()
    {
        ruleMap.put(TicTacToeBoard.class.getName(), TicTacToeBoard.getRules());
    }

    public GameInfo getInfo(Board board){
        if(board instanceof TicTacToeBoard){
            GameState gameState = getGameState(board);


            String players[] = new String[]{"X", "O"};
            for(int playerSymbol=0;playerSymbol<2;playerSymbol++)
            {
                for(int i=0;i<3;i++)
                {
                    for(int j=0;j<3;j++)
                    {
                        Board b = board.copy();
                        Player player = new Player(players[playerSymbol]);
                        b.move(new Move(new Cell(i,j), player));
                        boolean canStillWin = false;
                        for(int k=0;k<3;k++)
                        {
                            for(int l=0;l<3;l++)
                            {
                                Board b1 = b.copy();
                                b1.move(new Move(new Cell(i,j), player.playerFlip()));
                                if(!getGameState(b1).getWinner().equals(player.playerFlip().symbol()))
                                {
                                    canStillWin = true;
                                    break;
                                }
                            }
                            if(!canStillWin)
                                break;
                        }
                        if(canStillWin) {
                            return new GameInfoBuilder().isOver(gameState.isOver())
                                    .winner(gameState.getWinner())
                                    .hasFork(true)
                                    .player(player.playerFlip())
                                    .build();

                        }
                    }

                }
            }

            return new GameInfoBuilder().isOver(gameState.isOver())
                    .winner(gameState.getWinner())
                    .build();


        }else{
            throw new IllegalArgumentException();
        }
    }

    public GameState getGameState(Board board){
        if(board instanceof TicTacToeBoard){
            TicTacToeBoard b = (TicTacToeBoard) board;

            RuleSet<TicTacToeBoard> rules = ruleMap.get(TicTacToeBoard.class.getName());

            for(Rule<TicTacToeBoard> r: rules){
                GameState gameState = r.condition.apply(b);
                if(gameState.isOver())
                    return gameState;
            }
            return new GameState(false, "-");
        }
        return new GameState(false, "-");
    }

    public boolean isValidMove(Move move, Board board, int row, int col){
        TicTacToeBoard board1 = (TicTacToeBoard) board;
        return board1.getSymbolAtCell(row, col) == null;
    }

    public boolean isValidCell(int row, int col){
        return row>=0 && row<3 && col>=0 && col<3;
    }

}

