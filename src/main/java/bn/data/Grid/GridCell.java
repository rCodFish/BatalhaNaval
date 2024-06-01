
package bn.data.Grid;

/**
 *
 * @author Eduardo Santos
 */
public class GridCell {
  
  private boolean hasBoat = false;
  private boolean hasBeenHit = false;
  
  private final int x;
  private final int y;
  
  public GridCell(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public boolean hasBoat(){
    return hasBoat;
  }
  
  public void setBoat(boolean oui){
    hasBoat = oui;
  }
  
  public boolean hasBeenHit(){
    return hasBeenHit;
  }
  
  public void setBeenHit(boolean oui){
    hasBeenHit = oui;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  
  
}
