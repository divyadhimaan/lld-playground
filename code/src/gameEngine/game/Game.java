package game;

public class Game {
    private GameConfig gameConfig;
    private Board board;
    Player winner;
    private int lastMoveTimeInMillis;
    private int maxTimePerPlayer;
    private int maxTimePerMove;

    public void move(Move move, int timestampInMillis){
        int timeTakenSinceLastLastMove = timestampInMillis - lastMoveTimeInMillis;
        move.getPlayer().setTimeTaken(timeTakenSinceLastLastMove);

        if(gameConfig.timed){
            if(gameConfig.timePerMove != null) {
                if(moveMadeInTime(timeTakenSinceLastLastMove)){
                    board.move(move);
                }else{
                    winner = move.getPlayer().playerFlip();;
                }
            }else{
                if(moveMadeInTime(move.getPlayer())){
                    board.move(move);
                }else{
                    winner = move.getPlayer().playerFlip();;
                }
            }
        }
        else{
            board.move(move);
        }
    }

    private boolean moveMadeInTime(int timeTakenSinceLastLastMove){
        return timeTakenSinceLastLastMove < maxTimePerMove ;
    }

    private boolean moveMadeInTime(Player player){
        return player.getTimeUsedInMillis() < maxTimePerPlayer;
    }
}
