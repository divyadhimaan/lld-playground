# Building a Game Engine







## Step 1: **Basic designing**  
 - checkout branch (basic-designing)

### Design:

    1. GameEngine - 
        a. start() - Starts the game
        b. Move() - Makes the Move
        c. IsComplete() - Checks if the game is complete
        d. suggestMove() - suggest move for computer

    2. TicTacToeBoard - 
        a. getCell() - Sends the value of cell
        b. selCell() - Sets the value 
        c. toString() - prints the board
    3. Cell
        a. getRow()
        b. getCol()
    4. GameResult
        a. isOver() - boolean if the check the game is over or not.
        b. getWinner() - gives the winner
        c. printResult() - prints the result
    5. Move
        a. getCell()
        b. getPlayer()
    6. Player
        a. symbol() - symbol assigned to player


