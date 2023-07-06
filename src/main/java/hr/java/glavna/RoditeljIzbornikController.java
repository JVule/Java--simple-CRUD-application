package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Dolazak;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RoditeljIzbornikController {
    @FXML
    private TableView<Dolazak> dolazakTableView;
    @FXML
    private TableColumn<Dolazak, String> vrijemeDolaskaTableCol;
    @FXML
    private TableColumn<Dolazak,String> roditeljTableCol;
    @FXML
    private TableColumn<Dolazak,String> dijeteTableCol;
    @FXML
    private TableColumn<Dolazak,String>  grupaTableCol;
    List<Dolazak> listaDolazaka;
    public void prikaziGlavniEkran()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/glavniEkran.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
public void initialize(){
        listaDolazaka= BazaPodataka.dohvatiDolaske();
roditeljTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getRoditelj().toString()));
dijeteTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getDijete().toString()));
grupaTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getNazivGrupe()));
    vrijemeDolaskaTableCol.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Dolazak, String>,
                                ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(
                        TableColumn.CellDataFeatures<Dolazak, String> dolazak) {
                    SimpleStringProperty property = new
                            SimpleStringProperty();
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
                    property.setValue(
                            dolazak.getValue().getVrijemeDolaska().format(formatter));
                    return property;
                }
            });
    dolazakTableView.setItems(FXCollections.observableList(listaDolazaka));


}

public void prikaziDodajDolazakEkran() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/dodajDolazak.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
    Aplikacija.getMainStage().setScene(scene);
    Aplikacija.getMainStage().show();
}
    public void prikaziAnketaEkran() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/anketa.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
}
