package projeto.lp3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class paginaLoginController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private void switchTopaginaCadastro() throws IOException {
        App.setRoot("paginaCadastro");
    }

    @FXML
    private void switchTopaginaPrincipal() throws IOException {
        String usuario = campoUsuario.getText().trim();
        ;
        String senha = campoSenha.getText().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, senha FROM usuarios WHERE usuario = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id");
                String senhaBanco = rs.getString("senha");

                if (senha.equals(senhaBanco)) {
                    App.setUsuarioLogadoId(idUsuario);
                    mostrarAlerta("Sucesso", "Bem vindo de volta!", AlertType.INFORMATION);
                    App.setRoot("paginaPrincipal");
                } else {

                    mostrarAlerta("Erro", "Senha ou nome de usuário incorretos.", Alert.AlertType.ERROR);
                }
            } else {

                mostrarAlerta("Erro", "Senha ou nome de usuário incorretos.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Erro de conexão", "Não foi possível conectar ao banco de dados.", Alert.AlertType.ERROR);
        }

    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
