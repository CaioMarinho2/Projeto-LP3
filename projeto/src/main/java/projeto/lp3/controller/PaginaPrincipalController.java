package projeto.lp3.controller;

import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import projeto.lp3.App;
import projeto.lp3.model.Post;
import projeto.lp3.service.PostService;

public class PaginaPrincipalController {

    @FXML
    private TextField campoPost;
    @FXML
    private ListView<String> listaPosts;

    private ObservableList<String> posts = FXCollections.observableArrayList();
    private PostService service = new PostService();
    private int usuarioId = App.getUsuarioLogadoId();

    @FXML
    private void initialize() {
        carregarPosts();
    }

    @FXML
    private void switchTopaginaLogin() {
        try {

            App.setRoot("PaginaLogin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void postar() {
        String c = campoPost.getText().trim();

        if (c.isEmpty()) {
            mostrar("Erro", "Digite algo para postar.");
            return;
        }

        boolean ok = service.postar(usuarioId, c);

        if (ok) {
            carregarPosts();
            campoPost.clear();
        }
    }

    private void carregarPosts() {
        posts.clear();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Post p : service.listarPosts()) {
            posts.add("@" + p.getUsuarioNome() + "\n" + p.getConteudo() + "\n(" + p.getDataCriacao().format(f) + ")");
        }

        if (posts.isEmpty())
            posts.add("Nenhum post ainda.");

        listaPosts.setItems(posts);
    }

    private void mostrar(String t, String m) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}