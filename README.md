# Tetris2048

This project is a combination of the 2048 and Tetris games. It is implemented in Java programming language using the StdDraw library for GUI elements.

## Game Rules

The game is played on a 12x18 grid. The player is presented with Tetris pieces that they can move around the board. The goal is to form complete horizontal lines by filling in the grid with the Tetris pieces. When a complete line is formed, it disappears, and the player scores points. Adjacent tiles with the same numbers get combined into one tile, which adds the final number to the score.

The game ends when the player can no longer place any Tetris pieces on the board. The player's final score is the sum of all the points earned during the game.

## How to Play

1. Clone the repository.
2. Open the project in an IDE that supports Java.
3. Run the `Tetris2048.java` file to start the game.
4. Use the arrow keys to move the Tetris pieces left, right, and down.
5. Use the up arrow key to rotate the Tetris piece.

## Dependencies

- Java Development Kit (JDK) version 11 or later.
- StdDraw library for GUI elements.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
