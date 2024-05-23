package bn.data.grid;

import bn.data.grid.status.StatusObj;
import bn.data.grid.square.Square;

/**
 *
 * @author Eduardo Santos
 */
public class Grid {

  private Square[][] grid;
  private int rows;
  private int cols;

  public Grid(int rows, int cols) {
    grid = new Square[rows][cols];

    //rows
    for (int i = 0; i < rows; i++) {
      //cols
      for (int ii = 0; ii < cols; ii++) {
        grid[i][ii] = new Square();
      }
    }
  }
}
