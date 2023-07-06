package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Dijete;
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

public class RoditeljPrikazController {
@FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField nazivDjetetaTextField;
    @FXML
    private TableView<Roditelj> roditeljTableView;
    @FXML
    private TableColumn<Roditelj,String> imeTableCol;
    @FXML
    private TableColumn<Roditelj,String> prezimeTableCol;
    @FXML
    private TableColumn<Roditelj,String> nazivDjetetaTableCol;
    List<Roditelj> listaRoditelja;
    public void initialize(){
        listaRoditelja= BazaPodataka.dohvatiRoditelje();
        imeTableCol.setCellValueFactory(roditelj->new SimpleStringProperty(roditelj.getValue().getIme()));
        prezimeTableCol.setCellValueFactory(roditelj->new SimpleStringProperty(roditelj.getValue().getPrezime()));
        nazivDjetetaTableCol.setCellValueFactory(roditelj->new SimpleStringProperty(roditelj.getValue().getDijete().toString()));
        roditeljTableView.setItems(FXCollections.observableList(listaRoditelja));
    }
    public void filtriraj(){
        List<Roditelj> roditeljList=listaRoditelja;
        String ime=unosPodataka(imeTextField);
        String prezime=unosPodataka(prezimeTextField);
        String nazivDjeteta=unosPodataka(nazivDjetetaTextField);
        if(ime!=null){
            roditeljList=roditeljList.stream().filter(s->s.getIme().toLowerCase().contains(ime.toLowerCase())).collect(Collectors.toList());
        }
        if(prezime!=null){
            roditeljList=roditeljList.stream().filter(s->s.getPrezime().toLowerCase().contains(prezime.toLowerCase())).collect(Collectors.toList());

        }
        if(nazivDjeteta!=null){
            roditeljList=roditeljList.stream().filter(s->s.getDijete().toString().toLowerCase().contains(nazivDjeteta.toLowerCase())).collect(Collectors.toList());

        }
        roditeljTableView.setItems(FXCollections.observableList(roditeljList));
    }
    public void obrisi(){

        Roditelj roditelj=roditeljTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pozor");
        alert.setHeaderText("Brisanje");
        alert.setContentText("Želite li obrisati "+roditelj.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            BazaPodataka.obrisiRoditelja(roditelj);
            listaRoditelja=BazaPodataka.dohvatiRoditelje();
            roditeljTableView.setItems(FXCollections.observableList(listaRoditelja));
        } else {

        }
    }
    public void promjeni(){
        StringBuilder promjena=new StringBuilder();
        Serijalizacija<String> serijalizacija=new Serijalizacija<>();
        String pathString="dat/roditeljiSerijalizacija.bin";
        List<String> listRoditeljiSerijalizacija=serijalizacija.deserijaliziraj(pathString);
        try {
            Path path=Path.of(pathString);
            if(Files.exists(path)){
                Files.delete(Path.of(pathString));}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Roditelj odabraniRoditelj=roditeljTableView.getSelectionModel().getSelectedItem();
        promjena.append("Stara vrijednost,promjenio zaposlenik datuma "+ LocalDateTime.now()+" ime "+odabraniRoditelj.getIme()+" prezime "+odabraniRoditelj.getPrezime()+" ime djeteta "+odabraniRoditelj.getDijete());

        Integer id=odabraniRoditelj.getId();
        String ime=unosPodataka(imeTextField);
        String prezime=unosPodataka(prezimeTextField);
        String nazivDjeteta=unosPodataka(nazivDjetetaTextField);
        if(ime == null){
            ime=odabraniRoditelj.getIme();
        }
        if(prezime == null){
            prezime=odabraniRoditelj.getPrezime();
        }
        if(nazivDjeteta==null){
            nazivDjeteta=odabraniRoditelj.getDijete();
        }
        Roditelj roditelj=new Roditelj(id,ime,prezime,nazivDjeteta);
        promjena.append("Nova Vrijednost ime: "+ime+" prezime "+prezime+" ime djeteta "+nazivDjeteta+" \n");
        listRoditeljiSerijalizacija.add(promjena.toString());
        serijalizacija.serijaliziraj(listRoditeljiSerijalizacija,pathString);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Promjena");
        alert.setHeaderText("Promjena");
        alert.setContentText("Da promjenim entitet?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            BazaPodataka.promjeniRoditelja(roditelj);
            roditeljTableView.setItems(FXCollections.observableList(BazaPodataka.dohvatiRoditelje()));
        } else {

        }
    }
    private String unosPodataka(TextField unos){
        if(unos.getText().isBlank()){
            return null;
        }
        else return unos.getText();
    }
}
