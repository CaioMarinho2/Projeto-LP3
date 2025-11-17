package projeto.lp3.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import projeto.lp3.App;
import projeto.lp3.model.Usuario;
import projeto.lp3.service.UsuarioService;

public class PaginaLoginController {

    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void switchTopaginaCadastro() throws IOException {
        App.setRoot("PaginaCadastro");
    }

    @FXML
    private void entrar() throws IOException {
        String usuario = campoUsuario.getText().trim();
        String senha = campoSenha.getText().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrar("Erro", "Preencha todos os campos.", AlertType.ERROR);
            return;
        }

        Usuario u = usuarioService.autenticar(usuario, senha);

        if (u != null) {
            App.setUsuarioLogadoId(u.getId());
            App.setRoot("PaginaPrincipal");
        } else {
            mostrar("Erro", "Usuário ou senha incorretos.", AlertType.ERROR);
        }
    }

    private void mostrar(String t, String m, AlertType tp) {
        Alert a = new Alert(tp);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}
