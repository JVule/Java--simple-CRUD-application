package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class DijetePrikazController {
@FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField roditeljTextField;
    @FXML
    private TextField grupaTextField;
    @FXML
    private TextField dobTextField;
    @FXML
    private TableView<Dijete> dijeteTableView;
    @FXML
    private TableColumn<Dijete,String> imeTableCol;
    @FXML
    private TableColumn<Dijete,String> prezimeTableCol;
    @FXML
    private TableColumn<Dijete,String> grupaTableCol;
    @FXML
    private TableColumn<Dijete,String> roditeljTableCol;
    @FXML
    private TableColumn<Dijete,String> dobTableCol;

    public List<Dijete> getListDjeca() {
        return listDjeca;
    }

    public void setListDjeca(List<Dijete> listDjeca) {
        this.listDjeca = listDjeca;
    }

    private List<Dijete> listDjeca;
    private List<String> listDjecaSerijalizacija;
    public void initialize(){
listDjeca=BazaPodataka.dohvatiDjecu();
imeTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getIme()));
prezimeTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getPrezime()));
        grupaTableCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Dijete, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Dijete, String> dijete) {

                        if(dijete.getValue().getGrupa()==null){
                            return new SimpleStringProperty("grupa izbrisana");
                        }
                        else {
                            return new SimpleStringProperty(dijete.getValue().getGrupa().getNaziv());
                        }

                    }
                });
        roditeljTableCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Dijete, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Dijete, String> dijete) {

                        if(dijete.getValue().getRoditelj().getIme()==null){
                            return new SimpleStringProperty("roditelj izbrisan");
                        }
                        else {
                            return new SimpleStringProperty(dijete.getValue().getRoditelj().toString());
                        }

                    }
                });
dobTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getDob().toString()));
dijeteTableView.setItems(FXCollections.observableList(listDjeca));
    }
    public void pretrazi(){
        List<Dijete> dijeteList=listDjeca;
        String ime=unosPodataka(imeTextField);
        String prezime=unosPodataka(prezimeTextField);
        String roditelj=unosPodataka(roditeljTextField);
        String grupa=unosPodataka(grupaTextField);
        String dob=unosPodataka(dobTextField);
        if(ime!=null){
            dijeteList=dijeteList.stream().filter(s-> s.getIme().toLowerCase().contains(ime.toLowerCase())).collect(Collectors.toList());
        }
        if(prezime!=null){
            dijeteList=dijeteList.stream().filter(s->s.getPrezime().toLowerCase().contains(prezime.toLowerCase())).collect(Collectors.toList());}
            if(roditelj != null){
                dijeteList=dijeteList.stream().filter(s->s.getRoditelj().toString().toLowerCase().contains(roditelj.toLowerCase())).collect(Collectors.toList());
            }
            if( grupa != null){
                dijeteList=dijeteList.stream().filter(s->s.getGrupa().getNaziv().toLowerCase().contains(grupa.toLowerCase())).collect(Collectors.toList());

            }
            if( dob != null){
                dijeteList=dijeteList.stream().filter(s->s.getDob().toString().contains(dob)).collect(Collectors.toList());
            }


        dijeteTableView.setItems(FXCollections.observableList(dijeteList));
    }
    private String unosPodataka(TextField unos){
        if(unos.getText().isEmpty()){
            return null;
        }
        else{
            return unos.getText();
        }
    }
    public void obrisi(){
        Dijete dijete=dijeteTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pozor");
        alert.setHeaderText("Brisanje");
        alert.setContentText("Želite li obrisati "+dijete.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            BazaPodataka.obrisiDijete(dijete);
            listDjeca=BazaPodataka.dohvatiDjecu();
            dijeteTableView.setItems(FXCollections.observableList(listDjeca));
        } else {

        }

    }
    public void promjena(){
        StringBuilder promjena=new StringBuilder();
        Serijalizacija<String> serijalizacija=new Serijalizacija<>();
        listDjecaSerijalizacija=serijalizacija.deserijaliziraj("dat//djecaSerijalizacija.bin");
        Dijete dijeteKojeMijenjamo=dijeteTableView.getSelectionModel().getSelectedItem();
        promjena.append(" Stara vrijednost "+dijeteKojeMijenjamo.toString()+" roditelj "
                +dijeteKojeMijenjamo.getRoditelj()+" dob "+dijeteKojeMijenjamo.getGrupa()+" dob "+dijeteKojeMijenjamo.getDob().toString()+" promjenio zaposlenik "+ LocalDateTime.now().toString()+" ");
        String ime=imeTextField.getText();
        if(imeTextField.getText().isBlank()){
            ime=dijeteKojeMijenjamo.getIme();
        }
        String prezime=prezimeTextField.getText();
        if(prezimeTextField.getText().isBlank()){
            prezime=dijeteKojeMijenjamo.getPrezime();
        }
        Grupa grupa=null;
        Roditelj roditelj=null;
        Integer dob=0;
       Integer id=dijeteTableView.getSelectionModel().getSelectedItem().getId();
        Optional <Grupa>grupaOptional=Optional.empty();
        Optional<Roditelj>roditeljOptional=Optional.empty();
        if(grupaTextField.getText().isBlank()==false) {
            grupaOptional = BazaPodataka.dohvatiGrupuPoImenu(grupaTextField.getText());
        }
        else{
            grupa=dijeteKojeMijenjamo.getGrupa();
        }
        if(grupaOptional.isPresent()){
            grupa=grupaOptional.get();
        }

        if(!roditeljTextField.getText().isBlank()){
            roditeljOptional=BazaPodataka.dohvatiRoditeljPoImenu(roditeljTextField.getText());
        }
        else{
            roditelj=dijeteKojeMijenjamo.getRoditelj();
        }
        if(roditeljOptional.isPresent()){
            roditelj=roditeljOptional.get();
        }
        if(dobTextField.getText().isBlank()==false){
            dob=Integer.parseInt(dobTextField.getText());
        }
        else{
            dob=dijeteKojeMijenjamo.getDob();
        }
       try {
           Path path=Path.of("dat//djecaSerijalizacija.bin");
            if(Files.exists(path)){
            Files.delete(Path.of("dat//djecaSerijalizacija.bin"));}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(dob!=0 && ime!=null && prezime!=null && grupa!=null && roditelj!=null){
            BazaPodataka.postaviDijete(new Dijete(id,ime,prezime,roditelj,dob,grupa));
            promjena.append(" nova vrijednost:"+ime+" "+prezime+" "+roditelj.toString()+" "+dob+" "+grupa+"\n ");
            listDjecaSerijalizacija.add(promjena.toString());
            serijalizacija.serijaliziraj(listDjecaSerijalizacija,"dat//djecaSerijalizacija.bin");
            listDjeca=BazaPodataka.dohvatiDjecu();
            dijeteTableView.setItems(FXCollections.observableList(listDjeca));
        }

    }
}
