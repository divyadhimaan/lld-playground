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
    Map<String, List<Rule<TicTacToeBoard>>> ruleMap = new HashMap<>();

    public RuleEngine()
    {
        String key = TicTacToeBoard.class.getName();
        ruleMap.put(key, new ArrayList<>());
        ruleMap.get(key).add(new Rule<>(board -> outerTraversal((i, j) -> board.getSymbolAtCell(i, j))));
        ruleMap.get(key).add(new Rule<>(board -> outerTraversal((i, j) -> board.getSymbolAtCell(i, j))));
        ruleMap.get(key).add(new Rule<>(board -> traverse((i)-> board.getSymbolAtCell(i,i))));
        ruleMap.get(key).add(new Rule<>(board -> traverse((i)-> board.getSymbolAtCell(i,2-i))));
        ruleMap.get(key).add(new Rule<>(board ->{
            int countOfFilledCells = 0;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(board.getSymbolAtCell(i,j) != null)
                        countOfFilledCells++;
                }
            }
            if(countOfFilledCells==9)
                return new GameState(true, "-");
            return new GameState(false, "-");
        }));

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
                        if(canStillWin)
                            return new GameInfo(gameState.isOver(),gameState.getWinner(), true, player.playerFlip());
                    }

                }
            }


            return new GameInfo(gameState.isOver(),gameState.getWinner(), false);


        }else{
            throw new IllegalArgumentException();
        }
    }

    public GameState getGameState(Board board){
        if(board instanceof TicTacToeBoard){
            TicTacToeBoard b = (TicTacToeBoard) board;

            for(Rule<TicTacToeBoard> r: ruleMap.get(TicTacToeBoard.class.getName())){
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

    private GameState outerTraversal(BiFunction<Integer, Integer, String> getCharacterAt){
        GameState result = new GameState(false, "-");
        for(int i=0;i<3;i++)
        {
            final int ii = i;
            GameState traversal = traverse((j) -> getCharacterAt.apply(ii,j));
            if(traversal.isOver())
                result = traversal;
        }
        return result;
    }

    private GameState traverse(Function<Integer, String> traversal){
        GameState result = new GameState(false, "-");
        boolean possibleStreak = true;
        for(int j=0;j<3;j++)
        {
            if(traversal.apply(j)==null || !traversal.apply(0).equals(traversal.apply(j))){
                possibleStreak = false;
                break;
            }
        }

        if(possibleStreak)
            result = new GameState(true, traversal.apply(0));
        return result;
    }
}

class Rule<T extends Board>{
    Function<T, GameState> condition;
    public Rule(Function<T, GameState> condition){
        this.condition = condition;
    }
}