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

    public static void setUsuarioLogadoId(int id) {
        usuarioLogadoId = id;
    }

    public static int getUsuarioLogadoId() {
        return usuarioLogadoId;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("paginaLogin"), 640, 480);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {

        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        scene.setRoot(loadFXML(fxml));

        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}