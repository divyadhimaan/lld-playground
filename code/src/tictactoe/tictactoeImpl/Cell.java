package tictactoeImpl;

public class Cell {
    Symbol symbol;


    Cell(){
        this.symbol = Symbol.EMPTY;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Boolean isEmpty() {
        return this.symbol == Symbol.EMPTY;
    }
}
