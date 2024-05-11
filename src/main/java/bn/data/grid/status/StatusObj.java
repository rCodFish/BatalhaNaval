package bn.data.grid.status;

import bn.data.grid.square.Square;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Santos
 */
public class StatusObj implements Serializable{
  private int rows;
  private int cols;
  private ArrayList<int[]> haveBeenHit; //List with lists containing the coordinates of the squares that have been hit
  private ArrayList<int[]> haveBoat;  //List with lists containing the coordinates of the squares that have a boat
  
  public StatusObj(Square[][] grid) throws Exception{
    //must be a square matrix, otherwise womp
    this.rows = grid.length;
    this.cols = grid[0].length;
    
    if(rows != cols){
      throw new Exception("Matrix provided is not square, statusObj could no be created");
    }
      
    haveBeenHit = new ArrayList<>();
    haveBoat = new ArrayList<>();
  }
  
  
  
  
}
