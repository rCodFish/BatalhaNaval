module bn {
    requires javafx.controls;
    requires javafx.fxml;

    opens bn.ui.controllers.windowsControllers to javafx.fxml;
    exports bn.app;
}
