package hr.java.glavna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Aplikacija extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Aplikacija.class);
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        Aplikacija.mainStage = mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        setMainStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/glavniEkran.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Projekt-Vrtić");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        logger.info("Aplikacija je pokrenuta");
        launch();
    }
}