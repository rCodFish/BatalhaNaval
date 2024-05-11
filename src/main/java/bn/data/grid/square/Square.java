package bn.data.grid.square;

/**
 *
 * @author Eduardo Santos
 */
public class Square {
  private boolean hasBoat = false;
  private boolean hasBeenHit = false;
  
  public Square(){}
  
  public void setBoat(Boolean in){
    this.hasBoat = in;
  }
  
  public void setHit(Boolean in){
    this.hasBeenHit = in;
  }
  
  public boolean hasBoat(){
    return this.hasBoat;
  }
  
  public boolean hasBeenHit(){
    return this.hasBeenHit;
  }
}
