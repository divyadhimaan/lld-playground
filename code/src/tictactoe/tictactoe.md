# Tic Tac Toe game
This is an object-oriented design of the Tic Tac Toe game for two players using clean separation of concerns. The system:

- Enforces rules (valid move, win, draw)
- Uses strategy pattern for pluggable winning logic 
- Promotes reusability and flexibility with interfaces like Game, Board, and WinningStrategy

## Design Patterns Used

| **Pattern**                | **Usage**                                                                 |
| -------------------------- | ------------------------------------------------------------------------- |
| **Strategy**               | `WinningStrategy` allows multiple pluggable strategies (Horizontal, etc.) |
| **Singleton**              | `GameController` ensures one controller instance per game lifecycle       |
| **Factory (partial)**      | `TicTacToeBoard.getInstance()` controls board instantiation               |
| **Interface-based design** | `Game`, `Board`, `WinningStrategy` promote loose coupling                 |


## Class Structure

| **Component**     | **Responsibility**                                                      |
| ----------------- | ----------------------------------------------------------------------- |
| `TicTacToeGame`   | Entry point: initializes players and game loop                          |
| `TicTacToe`       | Core game engine: manages turns, checks status, controls game flow      |
| `TicTacToeBoard`  | Manages the grid, symbols, reset, and win condition via controller      |
| `GameController`  | Central utility for validating moves, checking wins, and handling rules |
| `WinningStrategy` | Strategy interface for different win-check algorithms (H, V, Diagonal)  |
| `Player`          | Holds player details (name + symbol)                                    |
| `Symbol`          | Enum representing X, O, or EMPTY                                        |
| `Cell`            | Represents each cell in the board grid                                  |

```mermaid
classDiagram
    direction TB

    class TicTacToeGame {
        +main(String[]) void
    }

    class TicTacToe {
        -Board board
        -List~Player~ players
        -GameStatus gameStatus
        -int currentPlayerIdx
        +playMove(Player, int, int) void
        +resetGame() void
        +getStatus() GameStatus
        +printBoard() void
        +isGameOver() boolean
        +getCurrentPlayer() Player
    }

    class GameController {
        -List~WinningStrategy~ winnningStrategies
        -static GameController controller
        -GameController(List~WinningStrategy~)
        +getInstance(List~WinningStrategy~) GameController
        +isBoardFull(Board, Cell[][]) boolean
        +checkWin(Board, Symbol) boolean
        +markCell(int, int, Symbol, Cell[][]) void
    }

    class TicTacToeBoard {
        -Cell[][] grid
        -int movesCount
        -static final int SIZE
        -GameController controller
        -static TicTacToeBoard board
        +getInstance(List~WinningStrategy~) Board
        +placeMark(int, int, Symbol) void
        +isFull() boolean
        +checkWinner(Player, Player) Player
        +reset() void
        +showBoard() void
        +getSymbol(int, int) Symbol
        +getSize() int
    }

    class Cell {
        -Symbol symbol
        +setSymbol(Symbol) void
        +getSymbol() Symbol
        +isEmpty() Boolean
    }

    class Player {
        -String name
        -Symbol symbol
        +getName() String
        +getSymbol() Symbol
    }

class Symbol {
<<enum>>
X
O
EMPTY
}

class GameStatus {
<<enum>>
IN_PROGRESS
DRAW
WIN
}

class Board {
    <<interface>>
+placeMark(int, int, Symbol) void
+isFull() boolean
+checkWinner(Player, Player) Player
+reset() void
+showBoard() void
}

class Game {
    <<interface>>
+playMove(Player, int, int) void
+resetGame() void
+getStatus() GameStatus
+printBoard() void
+isGameOver() boolean
}

class WinningStrategy {
    <<interface>>
+checkWinner(Board, Symbol) boolean
}

class HorizontalWinningStrategy
class VerticalWinningStrategy
class DiagonalWinningStrategy

TicTacToeGame --> TicTacToe
TicTacToe --> Player
TicTacToe --> Board
TicTacToe --> GameStatus
TicTacToe --> "1" TicTacToeBoard : uses
TicTacToe --> GameController : indirectly via board
TicTacToeBoard --> GameController : uses
TicTacToeBoard --> Cell : contains
TicTacToeBoard --> Symbol : uses
TicTacToeBoard --> Player : checks winner
GameController --> WinningStrategy : uses
GameController --> Symbol : uses
Cell --> Symbol : uses
Player --> Symbol : has
TicTacToeBoard ..|> Board
TicTacToe ..|> Game
HorizontalWinningStrategy ..|> WinningStrategy
VerticalWinningStrategy ..|> WinningStrategy
DiagonalWinningStrategy ..|> WinningStrategy


```


## Future Enhancements
| **Enhancement Area**                | **Recommendation**                                                                    |
| ----------------------------------- | ------------------------------------------------------------------------------------- |
| **Exception handling**              | Avoid printing inside controller (`System.out.print`) – use proper exception messages |
| **Make `GameController` stateless** | Remove singleton, pass strategy list explicitly. Avoid tight coupling with board      |
| **Board factory**                   | Move `getInstance()` to a factory class to support different board sizes/types        |
| **Dynamic board size**              | Allow users to input board size instead of hardcoded 3x3                              |
| **Undo/Redo**                       | Maintain move history to support undo feature                                         |
| **Timer support**                   | Add timers per move and per game (for competitive play)                               |
| **Minimax / AI bot**                | Add bot player using strategy pattern                                                 |
| **Multiplayer support**             | Extend to online multiplayer using sockets or REST                                    |
