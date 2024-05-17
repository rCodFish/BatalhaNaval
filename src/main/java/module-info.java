module bn {
    requires javafx.controls;
    requires javafx.fxml;
  requires java.base;

    opens bn.gui.controllers to javafx.fxml;
    exports bn.app;
}
