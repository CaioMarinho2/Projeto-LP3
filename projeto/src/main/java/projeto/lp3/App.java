package projeto.lp3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    private static int usuarioLogadoId;
    private static int postSelecionadoId;

    public static void setUsuarioLogadoId(int id) {
        usuarioLogadoId = id;
    }

    public static int getUsuarioLogadoId() {
        return usuarioLogadoId;
    }

    public static void setPostSelecionadoId(int id) {
        postSelecionadoId = id;
    }

    public static int getPostSelecionadoId() {
        return postSelecionadoId;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("PaginaLogin"), 640, 480);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {

        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        scene.setRoot(loadFXML(fxml));

        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/projeto/lp3/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}