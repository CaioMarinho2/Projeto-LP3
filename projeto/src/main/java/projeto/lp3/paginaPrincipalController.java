package projeto.lp3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private int usuarioId = App.getUsuarioLogadoId();
    private ObservableList<String> posts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarPosts();
    }

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

            String usuarioSql = "SELECT usuario FROM usuarios WHERE id = ?";
            String nomeUsuario = "Você";
            try (PreparedStatement stmtUser = conn.prepareStatement(usuarioSql)) {
                stmtUser.setInt(1, usuarioId);
                try (ResultSet rs = stmtUser.executeQuery()) {
                    if (rs.next()) {
                        nomeUsuario = rs.getString("usuario");
                    }
                }
            }

            java.time.LocalDateTime agora = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter
                    .ofPattern("dd/MM/yyyy HH:mm:ss");
            String data = fmt.format(agora);

            String postFormatado = "@" + nomeUsuario + "\n -" + conteudo + "\n (" + data + ")\n";
            if (posts.size() == 1 && posts.get(0).equals("Ainda não há posts 😔")) {
                posts.clear();
            }

            posts.add(0, postFormatado);
            listaPosts.setItems(posts);

            campoPost.clear();

        } catch (SQLException e) {
            mostrarAlerta("Erro ao postar", "Não foi possível salvar o post: " + e.getMessage());
        }
    }

    private void carregarPosts() {
        posts.clear();

        String sql = "SELECT p.conteudo, u.usuario, p.data_criacao " +
                "FROM posts p " +
                "JOIN usuarios u ON p.usuario_id = u.id " +
                "ORDER BY p.data_criacao DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String conteudo = rs.getString("conteudo");
                Timestamp timestamp = rs.getTimestamp("data_criacao");
                String data = timestamp.toLocalDateTime()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                String post = "@" + usuario + "\n" + " " + "-" + conteudo + "\n" + " (" + data + ")\n";
                posts.add(post);
            }

            if (posts.isEmpty()) {
                posts.add("Ainda não há posts 😔");
            }

            listaPosts.setItems(posts);

        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar posts", "Não foi possível carregar o feed: " + e.getMessage());
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
