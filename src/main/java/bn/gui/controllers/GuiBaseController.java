package bn.gui.controllers;

/**
 * Abstract base class for controllers.
 * 
 * @author Eduardo Santos
 */
public abstract class GuiBaseController {
  
  abstract public void transition();
  
  abstract public void otherReadyToTransition();
  
  abstract public void caughtEsc();
}
