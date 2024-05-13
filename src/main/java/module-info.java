module bn {
    requires javafx.controls;
    requires javafx.fxml;
  requires java.base;

    opens bn.ui.controllers.windowsControllers to javafx.fxml;
    exports bn.app;
}
