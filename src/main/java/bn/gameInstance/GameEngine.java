package bn.gameInstance;

/**
 *
 * @author Eduardo Santos & Luís Amândio
 */
public class GameEngine {

  private LogicController logicController;
  private UXController uxController;
  private boolean running = false;

  public GameEngine() {
  }

  public LogicController getLogicController() {
    return logicController;
  }

  public void setLogicController(LogicController logicController) throws Exception {
    this.logicController = logicController;

    if (running) {
      logicController.start();
    }
  }

  public UXController getUXController() {
    return uxController;
  }

  public void setUXController(UXController uxController) throws Exception {
    this.uxController = uxController;

    if (running) {
      uxController.start();
    }
  }

  public void start() throws Exception {
    if (running) {
      return;
    }

    if (logicController != null) {
      logicController.start();
    }

    if (uxController != null) {
      uxController.start();
    }
    
    running = true;
  }

  public void stop() throws Exception {
    if (!running) {
      return;
    }
    
    if (logicController != null) {
      logicController.stop();
    }

    if (uxController != null) {
      uxController.stop();
    }
    
    running = false;
  }
}
