package boards;

import api.Rule;
import api.RuleSet;
import game.Board;
import game.Cell;
import game.GameState;
import game.Move;

import java.util.function.BiFunction;
import java.util.function.Function;

public class TicTacToeBoard implements Board {
    String[][] cells  = new String[3][3];;


    public static RuleSet<TicTacToeBoard> getRules()
    {
        RuleSet rules = new RuleSet();
        rules.add(new Rule<TicTacToeBoard>(board -> outerTraversal((i, j) -> board.getSymbolAtCell(i, j))));
        rules.add(new Rule<TicTacToeBoard>(board -> outerTraversal((i, j) -> board.getSymbolAtCell(i, j))));
        rules.add(new Rule<TicTacToeBoard>(board -> traverse((i)-> board.getSymbolAtCell(i,i))));
        rules.add(new Rule<TicTacToeBoard>(board -> traverse((i)-> board.getSymbolAtCell(i,2-i))));
        rules.add(new Rule<TicTacToeBoard>(board ->{
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
        return rules;
    }
    public String getSymbolAtCell(int row, int col){
        return cells[row][col];
    }


    public void setCell(Cell cell,String symbol){
        cells[cell.getRow()][cell.getCol()] = symbol;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                result.append(cells[i][j] == null ? "-" : cells[i][j]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public void move(Move move){
        setCell(move.getCell(), move.getPlayer().symbol());
    }

    @Override
    public TicTacToeBoard copy(){
        TicTacToeBoard board = new TicTacToeBoard();
        for (int i = 0; i < 3; i++) {
            System.arraycopy(cells[i], 0, board.cells[i], 0, 3);
        }
        return board;
    }


    private static GameState outerTraversal(BiFunction<Integer, Integer, String> getCharacterAt){
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

    private static GameState traverse(Function<Integer, String> traversal){
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
