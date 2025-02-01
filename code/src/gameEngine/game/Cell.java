package game;

public class Cell {
    int row, col;
    public Cell(int r, int c){
        this.row = r;
        this.col = c;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
