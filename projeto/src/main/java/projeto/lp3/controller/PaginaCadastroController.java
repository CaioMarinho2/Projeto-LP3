package projeto.lp3.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import projeto.lp3.App;
import projeto.lp3.service.UsuarioService;

public class PaginaCadastroController {

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

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void criarConta() {
        String nome = campoNome.getText().trim();
        String usuario = campoUsuario.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = campoSenha.getText().trim();
        String nascimento = campoNascimento.getText().trim();

        if (nome.isEmpty() || usuario.isEmpty() || email.isEmpty() || senha.isEmpty() || nascimento.isEmpty()) {
            mostrar("Erro", "Preencha todos os campos.", AlertType.ERROR);
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            mostrar("Erro", "Digite um email válido.", AlertType.ERROR);
            return;
        }

        LocalDate data;
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = LocalDate.parse(nascimento, f);
        } catch (Exception e) {
            mostrar("Erro", "Data inválida! Use o formato dd/MM/yyyy.", AlertType.ERROR);
            return;
        }

        int idade = Period.between(data, LocalDate.now()).getYears();

        if (idade < 18) {
            mostrar("Erro", "Você precisa ter mais de 18 anos para usar a rede.", AlertType.ERROR);
            return;
        }

        if (idade > 120) {
            mostrar("Erro", "Digite uma data de nascimento válida,que uma pessoa possa ter.", AlertType.ERROR);
            return;
        }

        String status = usuarioService.validarCadastro(nome, usuario, email, senha, data);

        if (!status.equals("OK")) {
            mostrar("Erro", status, AlertType.ERROR);
            return;
        }

        boolean ok = usuarioService.cadastrarUsuario(nome, usuario, email, senha, data);

        if (ok) {
            mostrar("Sucesso", "Usuário cadastrado!", AlertType.INFORMATION);
            try {
                App.setRoot("PaginaLogin");
            } catch (IOException e) {
            }
        } else {
            mostrar("Erro", "Falha ao cadastrar.", AlertType.ERROR);
        }
    }

    @FXML
    private void switchTopaginaLogin() throws IOException {
        App.setRoot("PaginaLogin");
    }

    private void mostrar(String t, String m, AlertType tp) {
        Alert a = new Alert(tp);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}