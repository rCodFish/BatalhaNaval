package bn.gui.supportingLogic;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author Eduardo Santos
 */
public class BoatHBox {
    private HBox boatOption;
    private boolean isSelected = false;
    private int counter;

    public BoatHBox(String boatType, int boatCount) {
        counter = boatCount;
        boatOption = new HBox();
        Label label = new Label(boatType + " (x" + boatCount + ")");
        boatOption.getChildren().add(label);
    }

    public HBox getBoatOption() {
        return boatOption;
    }
    
    public boolean isSelected(){
      return isSelected;     
    }
    
    public void deselect() {
        boatOption.setStyle("-fx-background-color: transparent;");
        isSelected = false;
    }
    
    public void select() {
        boatOption.setStyle("-fx-background-color: lightblue;");
        isSelected = true;
    }

    public String getType() {
        Label label = (Label) boatOption.getChildren().get(0);
        String labelText = label.getText();
        return labelText.substring(0, labelText.indexOf(" ("));
    }
}
