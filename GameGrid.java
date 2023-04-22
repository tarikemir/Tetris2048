
import java.awt.*;
import java.util.Arrays;

// A class used for modelling the game grid
public class GameGrid {
   // data fields
   public int score=0;
   private int gridHeight, gridWidth; // the size of the game grid
   private Tile[][] tileMatrix; // to store the tiles locked on the game grid
   // the tetromino that is currently being moved on the game grid
   private Tetromino currentTetromino = null;
   // the gameOver flag shows whether the game is over or not
   private boolean gameOver = false;
   private Color emptyCellColor; // the color used for the empty grid cells
   private Color lineColor; // the color used for the grid lines
   private Color boundaryColor; // the color used for the grid boundaries
   private double lineThickness; // the thickness used for the grid lines
   private double boxThickness; // the thickness used for the grid boundaries

   // A constructor for creating the game grid based on the given parameters
   public GameGrid(int gridH, int gridW) {
      // set the size of the game grid as the given values for the parameters
      gridHeight = gridH;
      gridWidth = gridW;
      // create the tile matrix to store the tiles locked on the game grid
      tileMatrix = new Tile[gridHeight][gridWidth];
      // set the color used for the empty grid cells
      emptyCellColor = new Color(42, 69, 99);
      // set the colors used for the grid lines and the grid boundaries
      lineColor = new Color(0, 100, 200);
      boundaryColor = new Color(0, 100, 200);
      // set the thickness values used for the grid lines and the grid boundaries
      lineThickness = 0.002;
      boxThickness = 10 * lineThickness;
   }

   // A setter method for the currentTetromino data field
   public void setCurrentTetromino(Tetromino currentTetromino) {
      this.currentTetromino = currentTetromino;
   }

   // A method used for displaying the game grid
   public void display() {
      // clear the background to emptyCellColor
      StdDraw.clear(emptyCellColor);
      // draw the game grid
      drawGrid();
      // draw the current/active tetromino if it is not null (the case when the
      // game grid is updated)
      if (currentTetromino != null)
         currentTetromino.draw();
      // draw a box around the game grid
      drawBoundaries();
      // show the resulting drawing with a pause duration = 50 ms
      StdDraw.show();
      StdDraw.pause(50);
   }
   public void drawScore() {
      // Set the font and color for the score display
      StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
      StdDraw.setPenColor(StdDraw.WHITE);
      // Draw the score in the top-left corner of the canvas
      StdDraw.textLeft(0.05, 0.95, "Score: " + score);
   }
   public void drawNextTetromino( Tetromino t){

   }

   // A method for drawing the cells and the lines of the game grid
   public void drawGrid() {
      // for each cell of the game grid
      for (int row = 0; row < gridHeight; row++)
         for (int col = 0; col < gridWidth; col++)
            // draw the tile if the grid cell is occupied by a tile
            if (tileMatrix[row][col] != null)
               tileMatrix[row][col].draw(new Point(col, row));
      // draw the inner lines of the grid
      StdDraw.setPenColor(lineColor);
      StdDraw.setPenRadius(lineThickness);
      // x and y ranges for the game grid
      double startX = -0.5, endX = gridWidth - 0.5;
      double startY = -0.5, endY = gridHeight - 0.5;
      for (double x = startX + 1; x < endX; x++) // vertical inner lines
         StdDraw.line(x, startY, x, endY);
      for (double y = startY + 1; y < endY; y++) // horizontal inner lines
         StdDraw.line(startX, y, endX, y);
      StdDraw.setPenRadius(); // reset the pen radius to its default value

      drawScore();
   }

   // A method for drawing the boundaries around the game grid
   public void drawBoundaries() {
      // draw a bounding box around the game grid as a rectangle
      StdDraw.setPenColor(boundaryColor); // using boundaryColor
      // set the pen radius as boxThickness (half of this thickness is visible
      // for the bounding box as its lines lie on the boundaries of the canvas)
      StdDraw.setPenRadius(boxThickness);
      // the center point coordinates for the game grid
      double centerX = gridWidth / 2 - 0.5, centerY = gridHeight / 2 - 0.5;
      StdDraw.rectangle(centerX, centerY, gridWidth / 2, gridHeight / 2);
      StdDraw.setPenRadius(); // reset the pen radius to its default value
   }

   // A method for checking whether the grid cell with given row and column
   // indexes is occupied by a tile or empty
   public boolean isOccupied(int row, int col) {
      // considering newly entered tetrominoes to the game grid that may have
      // tiles out of the game grid (above the topmost grid row)
      if (!isInside(row, col))
         return false;
      // the cell is occupied by a tile if it is not null
      return tileMatrix[row][col] != null;
   }

   // A method for checking whether the cell with given row and column indexes
   // is inside the game grid or not
   public boolean isInside(int row, int col) {
      if (row < 0 || row >= gridHeight)
         return false;
      if (col < 0 || col >= gridWidth)
         return false;
      return true;
   }
   public void clearLine(){

      int numRows = tileMatrix.length;
      int numCols = tileMatrix[0].length;
      int numFullLines = 0;
      for (int i = 0; i < numRows; i++) {
         boolean fullLine = true;
         for (int j = 0; j < numCols; j++) {
            if (tileMatrix[i][j] == null) {
               fullLine = false;
               break;
            }
         }
         if (fullLine) {
            numFullLines++;
            // Move all rows above the current row down by 1
            for (int k = i + 1; k < numRows; k++) {
               for (int j = 0; j < numCols; j++) {

                  if( tileMatrix[k - 1][j] != null ) score += tileMatrix[k - 1][j].getNumber();
                  tileMatrix[k - 1][j] = tileMatrix[k][j];
                  tileMatrix[k][j] = null;
               }
            }
            // Clear the top row
            Arrays.fill(tileMatrix[numRows-1], null);
            // Since we moved everything down, we need to re-check this row
            i--;
         }
      }
   }
   // A method that locks the tiles of the landed tetromino on the game grid while


   public void moveUnconnectedTilesDown() {
      int numRows = tileMatrix.length;
      int numCols = tileMatrix[0].length;

      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {

            if (tileMatrix[i][j] == null) continue;
            boolean connected = isThereConnection(i, j, numRows, numCols);
            if (connected) continue;

            while (!connected && i > 0) {
               tileMatrix[i - 1][j] = tileMatrix[i][j];
               tileMatrix[i][j] = null;
               i--;
               connected = isThereConnection(i, j, numRows, numCols);
            }
            if (connected) break;
         }
      }
   }

   public boolean isThereConnection(int i, int j, int numRows, int numCols) {
      if (i > 0) {
         if (tileMatrix[i - 1][j] != null) return true;
      }
      if (j > 0) {
         if (tileMatrix[i][j - 1] != null) return true;
      }
      if (j < numCols - 1) {
         if (tileMatrix[i][j + 1] != null) return true;
      }
      if (i == 0) {
         return true;
      }
      return false;
   }



   public void mergeTiles() {
      for (int row = 0; row < tileMatrix.length; row++) {
         for (int col = 0; col < tileMatrix[0].length; col++) {
            Tile currentTile = tileMatrix[row][col];
            if (currentTile != null) {
               // Check if there is a same-numbered tile above or below

               if (row < tileMatrix.length - 1) {
                  Tile belowTile = tileMatrix[row+1][col];
                  if (belowTile != null && currentTile.getNumber() == belowTile.getNumber()) {
                     currentTile.setNumber(currentTile.getNumber() * 2);
                     tileMatrix[row+1][col] = null;
                     score += belowTile.getNumber()*2;
                  }
               }
            }
         }
      }
      moveUnconnectedTilesDown();

   }



   // checking if the game is over due to having tiles above the topmost grid row.
   // The method returns true when the game is over and false otherwise.
   public boolean check2048(){
      int rowN = tileMatrix.length;
      int colN = tileMatrix[0].length;
      for(int i=0; i<rowN; i++){
         for( int j=0; j<colN; j++){
            if (tileMatrix[i][j] != null) {
               if( tileMatrix[i][j].getNumber() == 2048) return true;
            }
         }
      }

      return false;
   }
   public boolean updateGrid(Tile[][] tilesToLock, Point blcPosition) {
      // necessary for the display method to stop displaying the tetromino

      currentTetromino = null;
      // lock the tiles of the current tetromino (tilesToLock) on the game grid
      int nRows = tilesToLock.length, nCols = tilesToLock[0].length;
      for (int col = 0; col < nCols; col++) {
         for (int row = 0; row < nRows; row++) {
            // place each tile onto the game grid
            if (tilesToLock[row][col] != null) {
               // compute the position of the tile on the game grid
               Point pos = new Point();
               pos.setX(blcPosition.getX() + col);
               pos.setY(blcPosition.getY() + (nRows - 1) - row);
               if (isInside(pos.getY(), pos.getX()))
                  tileMatrix[pos.getY()][pos.getX()] = tilesToLock[row][col];
               // the game is over if any placed tile is above the game grid
               else
                  gameOver = true;
            }
         }
      }
      if ( check2048()) {
         gameOver = true;
      }
      // return the value of the gameOver flag
      return gameOver;
   }
}