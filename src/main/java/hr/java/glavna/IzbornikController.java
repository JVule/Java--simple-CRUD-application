package hr.java.glavna;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikController {
public void prikaziDijetePrikaz() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/dijetePrikaz.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
    Aplikacija.getMainStage().setScene(scene);
    Aplikacija.getMainStage().show();
}
public void prikaziDijeteDodaj() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/dijeteDodaj.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
    Aplikacija.getMainStage().setScene(scene);
    Aplikacija.getMainStage().show();
}
public void  prikaziRoditeljePrikaz() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/roditeljPrikaz.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
    Aplikacija.getMainStage().setScene(scene);
    Aplikacija.getMainStage().show();
}
public  void prikaziRoditeljUnos() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/roditeljUnos.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
    Aplikacija.getMainStage().setScene(scene);
    Aplikacija.getMainStage().show();
}
public void prikaziOdgojiteljPrikaz() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/odgajateljPrikaz.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
    Aplikacija.getMainStage().setScene(scene);
    Aplikacija.getMainStage().show();
}
public  void prikaziOdgojiteljUnos() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/odgajateljUnos.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
    Aplikacija.getMainStage().setScene(scene);
    Aplikacija.getMainStage().show();
}
    public  void prikaziKuharicePrikaz() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/kuharicaPrikaz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
    public  void prikaziKuhariceUnos() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/kuharicaUnos.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
    public void prikaziJeloPrikaz() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/jeloPrikaz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
    public void prikaziJeloUnos() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/jeloUnos.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
    public void prikaziPromjene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/promjene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
}
