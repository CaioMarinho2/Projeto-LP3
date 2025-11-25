package projeto.lp3.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import projeto.lp3.App;
import projeto.lp3.model.Comentario;
import projeto.lp3.service.ComentarioService;

public class PaginaComentariosController {

    @FXML
    private ListView<String> listaComentarios;

    @FXML
    private TextArea campoComentario;

    private ComentarioService comentarioService = new ComentarioService();
    private int postId;
    private int usuarioId;

    @FXML
    private void initialize() {
        postId = App.getPostSelecionadoId();
        usuarioId = App.getUsuarioLogadoId();
        carregarComentarios();
    }

    private void carregarComentarios() {
        List<Comentario> comentarios = comentarioService.listarPorPost(postId);
        ObservableList<String> itens = FXCollections.observableArrayList();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM HH:mm");

        for (Comentario c : comentarios) {
            String data = c.getDataCriacao() != null ? c.getDataCriacao().format(f) : "";
            String texto = "@" + c.getUsuarioNome() + "  •  " + data + "\n" + c.getConteudo();
            itens.add(texto);
        }

        listaComentarios.setItems(itens);
    }

    @FXML
    private void comentar() {
        String texto = campoComentario.getText().trim();

        if (texto.isEmpty()) {
            mostrar("Erro", "Digite um comentário.");
            return;
        }

        boolean ok = comentarioService.comentar(usuarioId, postId, texto);

        if (ok) {
            campoComentario.clear();
            carregarComentarios();
        } else {
            mostrar("Erro", "Falha ao enviar comentário.");
        }
    }

    @FXML
    private void voltar() throws IOException {
        App.setRoot("PaginaPrincipal");
    }

    private void mostrar(String t, String m) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}