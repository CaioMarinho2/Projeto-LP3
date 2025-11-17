module projeto.lp3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens projeto.lp3.controller to javafx.fxml;
    opens projeto.lp3.model to javafx.fxml;

    exports projeto.lp3;
    exports projeto.lp3.controller;
}