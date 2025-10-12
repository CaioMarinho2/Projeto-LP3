module projeto.lp3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens projeto.lp3 to javafx.fxml;
    exports projeto.lp3;
}
