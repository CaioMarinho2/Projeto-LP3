package projeto.lp3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class paginaCadastroController {
    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoUsuario;

    @FXML
    private TextField campoEmail;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextField campoNascimento;

    @FXML
    private void criarConta() {
        String nome = campoNome.getText().trim();
        String usuario = campoUsuario.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = campoSenha.getText().trim();
        String nascimento = campoNascimento.getText().trim();

        if (nome.isEmpty() || usuario.isEmpty() || email.isEmpty() || senha.isEmpty() || nascimento.isEmpty()) {
            mostrarAlerta("Erro", "Por favor, preencha todos os campos.", AlertType.ERROR);
            return;
        }

        if (!emailValido(email)) {
            mostrarAlerta("Erro", "E-mail inválido! Use um formato válido (ex: nome@gmail.com).", AlertType.ERROR);
            return;
        }
        LocalDate data;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = LocalDate.parse(nascimento, formatter);
        } catch (DateTimeParseException e) {
            mostrarAlerta("Erro", "Data inválida! Use o formato dd/MM/yyyy (ex: 25/10/1990).", AlertType.ERROR);
            return;
        }

        String sql = "INSERT INTO usuarios (nome, usuario, email, senha, nascimento) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, usuario);
            stmt.setString(3, email);
            stmt.setString(4, senha);
            stmt.setDate(5, java.sql.Date.valueOf(data));
            stmt.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

            mostrarAlerta("Sucesso", "Usuário cadastrado com sucesso!", AlertType.INFORMATION);

            try {
                switchTopaginaLogin();
            } catch (IOException e) {
                mostrarAlerta("Erro", "Erro ao navegar para a página de login: " + e.getMessage(), AlertType.ERROR);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    private boolean emailValido(String email) {
        String padraoEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(padraoEmail, email);
    }

    @FXML
    private void switchTopaginaLogin() throws IOException {
        App.setRoot("paginaLogin");
    }

    private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
