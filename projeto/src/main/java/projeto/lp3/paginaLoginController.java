package projeto.lp3;

import java.io.IOException;
import javafx.fxml.FXML;

public class paginaLoginController {

    @FXML
    private void switchTopaginaCadastro() throws IOException {
        App.setRoot("paginaCadastro");
    }
    
    @FXML
    private void switchTopaginaPrincipal() throws IOException {
        App.setRoot("paginaPrincipal");
    }
}
