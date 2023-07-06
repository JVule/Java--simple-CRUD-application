package hr.java.glavna;

import hr.java.entiteti.Serijalizacija;
import hr.java.niti.PromjenaNit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PromjeneController {
    @FXML
    private TextArea textArea;
    public synchronized void  promjene(){
        PromjenaNit promjenaNit=new PromjenaNit(textArea);
        ExecutorService executorService=Executors.newCachedThreadPool();
        executorService.execute(promjenaNit);
        executorService.shutdown();

    }
    public  void logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/glavniEkran.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
}
