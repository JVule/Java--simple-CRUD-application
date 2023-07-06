package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Grupa;
import hr.java.entiteti.Odgajatelj;
import hr.java.entiteti.Roditelj;
import hr.java.entiteti.Serijalizacija;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OdgajateljPrikazController {
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField placaTextField;
    @FXML
    private ChoiceBox<Grupa> grupaChoiceBox;
    @FXML
    private TableColumn<Odgajatelj,String> imeTableColumn;
    @FXML
    private TableColumn<Odgajatelj,String> prezimeTableColumn;
    @FXML
    private TableColumn<Odgajatelj,String> placaTableColumn;
    @FXML
    private TableColumn<Odgajatelj,String> grupaTableColumn;
    @FXML
    private TableView<Odgajatelj> odgajateljTableView;
    List<Odgajatelj> listaOdgajatelja;
    public void initialize(){
        listaOdgajatelja= BazaPodataka.dohvatiOdgajatelje();
        imeTableColumn.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getIme()));
        prezimeTableColumn.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getPrezime()));
        grupaTableColumn.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getGrupa().getNaziv()));
        grupaChoiceBox.setItems(FXCollections.observableList(BazaPodataka.dohvatiGrupe()));
        placaTableColumn.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getPlaca().toString()));
        odgajateljTableView.setItems(FXCollections.observableList(listaOdgajatelja));
    }
    public void pretrazi(){
        List<Odgajatelj> odgajateljList=listaOdgajatelja;
        String ime=unosTexta(imeTextField);
        String prezime=unosTexta(prezimeTextField);
        String  grupaString=null;
        String placa=unosTexta(placaTextField);
        if(ime!=null){
            odgajateljList=odgajateljList.stream().filter(s->s.getIme().toLowerCase().contains(ime.toLowerCase())).collect(Collectors.toList());

        }
        if(prezime!=null){
            odgajateljList=odgajateljList.stream().filter(s->s.getPrezime().toLowerCase().contains(prezime.toLowerCase())).collect(Collectors.toList());

        }
        if(placa!=null){
            odgajateljList=odgajateljList.stream().filter(s->s.getPlaca().toString().compareTo(placa)==0).collect(Collectors.toList());

        }
        if(grupaChoiceBox.getValue()!=null){
            grupaString=grupaChoiceBox.getValue().getNaziv();
            String grupaString1=grupaString;
            odgajateljList=odgajateljList.stream().filter(s->s.getGrupa().getNaziv().compareTo(grupaString1)==0).collect(Collectors.toList());
        }
        odgajateljTableView.setItems(FXCollections.observableList(odgajateljList));

    }
    public void obrisi(){
        Odgajatelj odgajatelj=odgajateljTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pozor");
        alert.setHeaderText("Brisanje");
        alert.setContentText("Želite li obrisati "+odgajatelj.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            BazaPodataka.obrisiOdgajatelja(odgajatelj);
            listaOdgajatelja=BazaPodataka.dohvatiOdgajatelje();
            odgajateljTableView.setItems(FXCollections.observableList(listaOdgajatelja));
        } else {

        }
    }
    public void promjena(){
        StringBuilder promjena=new StringBuilder();
        Serijalizacija<String> serijalizacija=new Serijalizacija<>();
        String pathString="dat/odgajateljiSerijalizacija.bin";
        List<String>listOdgajateljicaSerijalizacija=serijalizacija.deserijaliziraj(pathString);
        try {
            Path path=Path.of(pathString);
            if(Files.exists(path)){
                Files.delete(path);}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Odgajatelj odabraniOdgajatelj=odgajateljTableView.getSelectionModel().getSelectedItem();
        promjena.append("Stara vrijednost promjenio zaposlenik datuma " + LocalDateTime.now()+" ime "+odabraniOdgajatelj.getIme()+" prezime "+odabraniOdgajatelj.getPrezime()+" grupa "+odabraniOdgajatelj.getGrupa()+" placa "+odabraniOdgajatelj.getPlaca());

        Integer id=odabraniOdgajatelj.getId();
        String ime=odabraniOdgajatelj.getIme();
        String prezime=odabraniOdgajatelj.getPrezime();
        Grupa grupa=odabraniOdgajatelj.getGrupa();
        Integer placa=odabraniOdgajatelj.getPlaca();
        if(unosTexta(imeTextField)!=null){
            ime=unosTexta(imeTextField);
            }
        if(unosTexta(prezimeTextField)!=null){
            prezime=unosTexta(prezimeTextField);
                }
        if(grupaChoiceBox.getValue()!=null){
                grupa=grupaChoiceBox.getValue();
}
if(unosTexta(placaTextField)!=null){
    placa=Integer.parseInt(unosTexta(placaTextField));
}
BazaPodataka.promjeniOdgajatelja(new Odgajatelj(id,ime,prezime,grupa,placa));
promjena.append("Nova vrijednost: ime "+ime+" prezime "+prezime+" grupa "+grupa+" placa "+placa);
listOdgajateljicaSerijalizacija.add(promjena.toString());
serijalizacija.serijaliziraj(listOdgajateljicaSerijalizacija,pathString);
listaOdgajatelja=BazaPodataka.dohvatiOdgajatelje();
odgajateljTableView.setItems(FXCollections.observableList(listaOdgajatelja));
    }

    public  String unosTexta(TextField unos){
        if(unos.getText().isBlank()==true){
            return null;
        }
        else return unos.getText();
    }

}
