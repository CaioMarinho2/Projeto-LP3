package projeto.lp3.controller;

import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import projeto.lp3.App;
import projeto.lp3.model.Post;
import projeto.lp3.service.PostService;

public class PaginaPrincipalController {

    @FXML
    private TextField campoPost;
    @FXML
    private ListView<Post> listaPosts;

    private ObservableList<Post> posts = FXCollections.observableArrayList();
    private PostService service = new PostService();
    private int usuarioId = App.getUsuarioLogadoId();

    @FXML
    private void initialize() {
        configurarListView();
        carregarPosts();
    }

    private void configurarListView() {
        listaPosts.setCellFactory(lv -> new ListCell<Post>() {
            private HBox root = new HBox(10);
            private VBox info = new VBox(2);
            private Label nomeData = new Label();
            private Label conteudo = new Label();
            private Button btnCurtir = new Button("Curtir");
            private Button btnComentar = new Button("Comentar");
            private Button btnCompartilhar = new Button("Compartilhar");

            {
                root.setStyle("-fx-padding: 10; -fx-background-color: #1a1a1a;");
                nomeData.setStyle("-fx-text-fill: #d7fa00; -fx-font-size: 12px;");
                conteudo.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
                btnCurtir.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");
                btnComentar.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");
                btnCompartilhar.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");

                HBox botoes = new HBox(10, btnCurtir, btnComentar, btnCompartilhar);
                info.getChildren().addAll(nomeData, conteudo, botoes);
                root.getChildren().add(info);
            }

            @Override
            protected void updateItem(Post p, boolean empty) {
                super.updateItem(p, empty);

                if (empty || p == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    nomeData.setText("@" + p.getUsuarioNome() + "  •  " +
                            p.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
                    conteudo.setText(p.getConteudo());
                    setGraphic(root);
                    setText(null);
                }
            }
        });
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
        posts.addAll(service.listarPosts());
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