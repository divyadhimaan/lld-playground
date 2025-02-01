package api;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

//suggestMove() - smartness/ intelligence required
public class AiEngine {



    public Move suggestMove(Player player, Board board){
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard board1 = (TicTacToeBoard) board;
            Move suggestion;
            if(countMoves(board1) < 3) {
                suggestion = getBasicMove(player, board1);
            }else{
                suggestion = getSmartMove(player, board1);
            }
            if(suggestion != null)
                return suggestion;

            throw new IllegalStateException();
        }else{
            throw new IllegalArgumentException();
        }
    }



    public Move getBasicMove(Player player, TicTacToeBoard board1){
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(board1.getSymbolAtCell(i,j)==null){
                    return new Move(new Cell(i,j), player);
                }
            }
        }
        throw new IllegalStateException();
    }

    public Move getSmartMove(Player player, TicTacToeBoard board){
        RuleEngine ruleEngine = new RuleEngine();

        TicTacToeBoard dummyBoard = board.copy();
        // Attacking Move
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(dummyBoard.getSymbolAtCell(i,j)==null){

                    Move move = new Move(new Cell(i,j), player);
                    dummyBoard.move(move);
                    // either you won or its draw
                    if(ruleEngine.getGameState(dummyBoard).isOver()) {
//                        System.out.println("Made attacking move");
                        return move;
                    }
                }
            }
        }


        dummyBoard = board.copy();
        // Defensive Move
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(dummyBoard.getSymbolAtCell(i,j)==null){

                    Move move = new Move(new Cell(i,j), player.playerFlip());
                    dummyBoard.move(move);

                    // either you won or its draw
                    if(ruleEngine.getGameState(dummyBoard).isOver()) {
//                        System.out.println("Made defensive move");
                        return new Move(new Cell(i, j), player);
                    }
                }
            }
        }


        return getBasicMove(player,board);
    }

    public int countMoves(TicTacToeBoard board1){
        int count=0;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(board1.getSymbolAtCell(i,j)!=null){
                    count++;
                }
            }
        }
        return count;
    }
}
