package projeto.lp3.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import projeto.lp3.App;

public class PaginaSobreNosController {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("PaginaPrincipal");
    }

}