package bn.gui.controllers;

import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController extends BaseController implements Initializable {

    @FXML
    private StackPane toggleSwitchPane;
    @FXML
    private StackPane toggleSwitchPane1;
    private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
    private WinStateMachine winAPI = winWrap.getWindowSM();


    @FXML
    public void close() {
        String PrepPhaseFXML = "options";
        try {
            winAPI.closeStage(PrepPhaseFXML);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleSwitch btnSound = new ToggleSwitch();
        toggleSwitchPane1.getChildren().add(btnSound);

        ToggleSwitch btnFull = new ToggleSwitch(() -> winAPI.setFullScreen(), () -> System.out.println("pequeno"));
        toggleSwitchPane.getChildren().add(btnFull);


    }

    private static class ToggleSwitch extends Parent {

        private BooleanProperty switchedOn = new SimpleBooleanProperty(false);

        private TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(0.25));
        private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.25));

        private ParallelTransition animation = new ParallelTransition(translateAnimation, fillAnimation);

        public BooleanProperty switchedOnProperty() {
            return switchedOn;
        }

        public ToggleSwitch() {
            this(null, null);
        }

        public ToggleSwitch(Runnable onAction, Runnable offAction) {
            Rectangle background = new Rectangle(30, 15);
            background.setArcWidth(5);
            background.setArcHeight(5);
            background.setFill(Color.WHITE);
            background.setStroke(Color.LIGHTGRAY);

            Circle trigger = new Circle(6);
            trigger.setCenterX(8);
            trigger.setCenterY(7.3);
            trigger.setFill(Color.WHITE);
            trigger.setStroke(Color.LIGHTGRAY);

            translateAnimation.setNode(trigger);
            fillAnimation.setShape(background);

            getChildren().addAll(background, trigger);

            switchedOn.addListener((obs, oldState, newState) -> {
                boolean isOn = newState;
                translateAnimation.setToX(isOn ? 100 - 86 : 0);
                fillAnimation.setFromValue(isOn ? Color.WHITE : Color.LIGHTGREEN);
                fillAnimation.setToValue(isOn ? Color.LIGHTGREEN : Color.WHITE);

                animation.play();

                if (isOn && onAction != null) {
                    onAction.run();
                } else if (!isOn && offAction != null) {
                    offAction.run();
                }
            });

            setOnMouseClicked(event -> {
                switchedOn.set(!switchedOn.get());
            });
        }
    }
}