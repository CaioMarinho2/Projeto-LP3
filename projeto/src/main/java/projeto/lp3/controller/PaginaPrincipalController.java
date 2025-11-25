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
import javafx.scene.control.TextInputDialog;
import projeto.lp3.App;
import projeto.lp3.model.Post;
import projeto.lp3.model.Usuario;
import projeto.lp3.service.PostService;
import projeto.lp3.service.UsuarioService;

public class PaginaPrincipalController {

    @FXML
    private TextField campoPost;

    @FXML
    private ListView<Post> listaPosts;

    private ObservableList<Post> posts = FXCollections.observableArrayList();
    private PostService service = new PostService();
    private UsuarioService usuarioService = new UsuarioService();
    private int usuarioId = App.getUsuarioLogadoId();
    private String usuarioLogadoNome;

    @FXML
    private void initialize() {
        Usuario u = usuarioService.buscarPorId(usuarioId);
        if (u != null) {
            usuarioLogadoNome = u.getUsuario();
        }
        configurarListView();
        carregarPosts();
    }

    private void configurarListView() {
        listaPosts.setCellFactory(lv -> new ListCell<Post>() {
            private HBox root = new HBox(10);
            private VBox info = new VBox(4);
            private Label nomeData = new Label();
            private Label conteudo = new Label();
            private Button btnCurtir = new Button();
            private Button btnComentar = new Button("Comentar");
            private Button btnCompartilhar = new Button("Compartilhar");
            private Button btnEditar = new Button("Editar");
            private Button btnExcluir = new Button("Excluir");

            {
                root.setStyle("-fx-padding: 10; -fx-background-color: #1a1a1a;");
                nomeData.setStyle("-fx-text-fill: #d7fa00; -fx-font-size: 12px;");
                conteudo.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
                btnCurtir.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");
                btnComentar.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");
                btnCompartilhar.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");
                btnEditar.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");
                btnExcluir.setStyle("-fx-background-color: #d7fa00; -fx-text-fill: #000000;");

                HBox botoes = new HBox(10, btnCurtir, btnComentar, btnCompartilhar, btnEditar, btnExcluir);
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
                    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM HH:mm");
                    String dataFormatada = p.getDataCriacao() != null ? p.getDataCriacao().format(f) : "";

                    int totalCurtidas = service.contarCurtidas(p.getId());
                    btnCurtir.setText("Curtir (" + totalCurtidas + ")");

                    if (p.isCompartilhado() && p.getCompartilhadoPor() != null) {
                        nomeData.setText(
                            "Compartilhado por @" + p.getCompartilhadoPor() + "\n@" +
                            p.getUsuarioNome() + "  •  " + dataFormatada
                        );
                        btnCompartilhar.setDisable(true);
                    } else {
                        nomeData.setText("@" + p.getUsuarioNome() + "  •  " + dataFormatada);
                        btnCompartilhar.setDisable(false);
                    }

                    conteudo.setText(p.getConteudo());

                    boolean ehDono = p.getUsuarioId() == usuarioId;
                    btnEditar.setVisible(ehDono);
                    btnEditar.setManaged(ehDono);
                    btnExcluir.setVisible(ehDono);
                    btnExcluir.setManaged(ehDono);

                    btnCurtir.setOnAction(e -> {
                        service.alternarCurtida(usuarioId, p.getId());
                        carregarPosts();
                    });

                    btnCompartilhar.setOnAction(e -> {
                        if (!p.isCompartilhado()) {
                            service.compartilhar(usuarioId, p.getId());
                            carregarPosts();
                        }
                    });

                    btnComentar.setOnAction(e -> {
                        App.setPostSelecionadoId(p.getId());
                        try {
                            App.setRoot("PaginaComentarios");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    btnEditar.setOnAction(e -> {
                        TextInputDialog dialog = new TextInputDialog(p.getConteudo());
                        dialog.setTitle("Editar post");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Altere o conteúdo:");
                        dialog.showAndWait().ifPresent(novo -> {
                            String texto = novo.trim();
                            if (!texto.isEmpty()) {
                                service.editarPost(usuarioId, p.getId(), texto);
                                carregarPosts();
                            }
                        });
                    });

                    btnExcluir.setOnAction(e -> {
                        if (p.isCompartilhado()) {
                            service.excluirCompartilhamento(usuarioId, p.getId());
                        } else {
                            service.excluirPost(usuarioId, p.getId());
                        }
                        carregarPosts();
                    });

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
    private void switchTopaginaSobreNos() {
        try {
            App.setRoot("PaginaSobreNos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchTopaginaPerfil() {
        try {
            App.setRoot("PaginaPerfil");
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