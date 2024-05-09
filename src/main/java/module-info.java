module bn.batalhaNaval {
    requires javafx.controls;
    requires javafx.fxml;

    opens bn.batalhaNaval.ui.controllers.windowsControllers to javafx.fxml;
    exports bn.batalhaNaval.app;
}
