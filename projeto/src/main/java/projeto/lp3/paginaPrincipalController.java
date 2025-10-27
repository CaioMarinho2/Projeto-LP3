package projeto.lp3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class paginaPrincipalController {

 @FXML
    private TextField campoPost;

    @FXML
    private Button botaoPostar;

    @FXML
    private ListView<String> listaPosts;

    private int usuarioId = App.getUsuarioLogadoId();;

    @FXML
    private void switchTopaginaLogin() throws IOException {
        App.setRoot("paginaLogin");
    }

    @FXML
    private void postarMensagem() {
        String conteudo = campoPost.getText().trim();

        if (conteudo.isEmpty()) {
            mostrarAlerta("Campo vazio", "Escreva algo antes de postar!");
            return;
        }

        String sql = "INSERT INTO posts (usuario_id, conteudo) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setString(2, conteudo);

            stmt.executeUpdate();
            listaPosts.getItems().add(conteudo);
            campoPost.clear();

        } catch (SQLException e) {
            mostrarAlerta("Erro ao postar", "Não foi possível salvar o post: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
