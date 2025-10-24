package projeto.lp3;

import java.io.IOException;
import javafx.fxml.FXML;

public class paginaPrincipalController {

    @FXML
    private void switchTopaginaLogin() throws IOException {
        App.setRoot("paginaLogin");
    }
}
