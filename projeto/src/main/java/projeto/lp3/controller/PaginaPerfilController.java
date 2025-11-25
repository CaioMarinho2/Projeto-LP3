package projeto.lp3.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import projeto.lp3.App;
import projeto.lp3.model.Usuario;
import projeto.lp3.service.UsuarioService;

public class PaginaPerfilController {

    @FXML
    private ImageView fotoPerfil;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoUsuario;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoNascimento;
    @FXML
    private Label labelSeguidores;
    @FXML
    private Label labelSeguindo;

    private Usuario usuario;
    private UsuarioService usuarioService = new UsuarioService();
    private String caminhoFotoAtual;

    @FXML
    private void initialize() {
        int id = App.getUsuarioLogadoId();
        usuario = usuarioService.buscarPorId(id);

        caminhoFotoAtual = usuario.getFoto();

        if (caminhoFotoAtual != null && !caminhoFotoAtual.isEmpty()) {
            fotoPerfil.setImage(new Image("file:" + caminhoFotoAtual));
        }

        campoNome.setText(usuario.getNome());
        campoUsuario.setText(usuario.getUsuario());
        campoEmail.setText(usuario.getEmail());

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        campoNascimento.setText(usuario.getNascimento().format(f));

        int seguidores = usuarioService.contarSeguidores(usuario.getId());
        int seguindo = usuarioService.contarSeguindo(usuario.getId());

        labelSeguidores.setText("Seguidores: " + seguidores);
        labelSeguindo.setText("Seguindo: " + seguindo);
    }

    @FXML
    private void alterarFoto() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));

        File arquivo = fc.showOpenDialog(null);

        if (arquivo != null) {
            caminhoFotoAtual = arquivo.getAbsolutePath();
            fotoPerfil.setImage(new Image("file:" + caminhoFotoAtual));
        }
    }

    @FXML
    private void salvarAlteracoes() {
        String novoNome = campoNome.getText().trim();
        String novoUsuario = campoUsuario.getText().trim();
        String novoEmail = campoEmail.getText().trim();
        String nascimentoStr = campoNascimento.getText().trim();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate novaData = usuario.getNascimento();

        try {
            novaData = LocalDate.parse(nascimentoStr, f);
        } catch (Exception e) {
        }

        usuarioService.atualizarPerfil(
                usuario.getId(),
                novoNome,
                novoUsuario,
                novoEmail,
                novaData,
                caminhoFotoAtual);

        mostrar("Sucesso", "Perfil atualizado com sucesso!");
    }

    @FXML
    private void voltar() throws IOException {
        App.setRoot("PaginaPrincipal");
    }

    private void mostrar(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}