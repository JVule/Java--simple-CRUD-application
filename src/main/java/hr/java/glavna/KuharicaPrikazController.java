package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KuharicaPrikazController {

    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private ChoiceBox<Smjena> smjenaChoiceBox;
    @FXML
    private  ChoiceBox<Jelo> jeloChoiceBox;
    @FXML
    private TableView<Kuharica> kuharicaTableView;
    @FXML
    private TableColumn<Kuharica,String> imeTableCol;
    @FXML
    private TableColumn<Kuharica,String>prezimeTableCol;
    @FXML
    private  TableColumn<Kuharica,String>smjenaTableCol;
    @FXML
    private TableColumn<Kuharica,String>jeloTableCol;
    private  List<Smjena> smjenaList;
    private  List<Jelo> listJela;




    List<Kuharica> listKuharica;
    public void initialize(){
        listKuharica=BazaPodataka.dohvatiKuharice();
        smjenaList=new ArrayList<>();
        smjenaList.add(Smjena.UJUTRO);
        smjenaList.add(Smjena.POPODNE);
        smjenaList.add(null);
        smjenaChoiceBox.setItems(FXCollections.observableList(smjenaList));
      listJela= BazaPodataka.dohvatiJelo();
        listJela.add(null);
        jeloChoiceBox.setItems(FXCollections.observableList(listJela));
        imeTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getIme()));
        prezimeTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getPrezime()));
        smjenaTableCol.setCellValueFactory(s->new SimpleStringProperty(s.getValue().getSmjena().toString()));
        jeloTableCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Kuharica, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Kuharica, String> kuharica) {

                        if(kuharica.getValue().getJelo()==null){
                            return new SimpleStringProperty("jelo izbrisano");
                        }
                        else {
                            return new SimpleStringProperty(kuharica.getValue().getJelo().getNaziv());
                        }

                    }
                });
        kuharicaTableView.setItems(FXCollections.observableList(listKuharica));
    }
public void filtriraj(){
        List<Kuharica> kuharicaList=BazaPodataka.dohvatiKuharice();
        String ime=unosPodataka(imeTextField);
        String prezime=unosPodataka(prezimeTextField);
        String jelo=null;
        if(jeloChoiceBox.getValue()!=null){
            jelo=jeloChoiceBox.getValue().getNaziv();
        }
        String smjena=null;
        if(smjenaChoiceBox.getValue()!=null){
            smjena=smjenaChoiceBox.getValue().toString();
        }
        if(ime !=null){
            kuharicaList=kuharicaList.stream().filter(s->s.getIme().toLowerCase().contains(ime.toLowerCase())).collect(Collectors.toList());
        }
        if(prezime!=null){
            kuharicaList=kuharicaList.stream().filter(s->s.getPrezime().toLowerCase().contains(prezime.toLowerCase())).collect(Collectors.toList());

        }
        if(jelo!=null){
            String jelo1=jelo;
            kuharicaList=kuharicaList.stream().filter(s->s.getJelo().getNaziv().toLowerCase().contains(jelo1.toLowerCase())).collect(Collectors.toList());
        }
        if(smjena!=null){
            String smjena1=smjena;
            kuharicaList=kuharicaList.stream().filter(s->s.getSmjena().toString().toLowerCase().contains(smjena1.toLowerCase())).collect(Collectors.toList());
        }
        kuharicaTableView.setItems(FXCollections.observableList(kuharicaList));
}
public void izbrisi(){
BazaPodataka.obrisiKuharica(kuharicaTableView.getSelectionModel().getSelectedItem());
listKuharica=BazaPodataka.dohvatiKuharice();
kuharicaTableView.setItems(FXCollections.observableList(listKuharica));
}
public  void promjeni(){
        Kuharica odabranaKuharica=kuharicaTableView.getSelectionModel().getSelectedItem();
        String ime=unosPodataka(imeTextField);
        String prezime=unosPodataka(prezimeTextField);
        Integer placa=0;
    StringBuilder promjena=new StringBuilder();
    Serijalizacija<String> serijalizacija=new Serijalizacija<>();
    List<String> listKuharicaSerijalizacija=serijalizacija.deserijaliziraj("dat/kuhariceSerijalizacija.bin");
       promjena.append("Stara vrijednost: ime "+odabranaKuharica.getIme()+" prezime " +odabranaKuharica.getPrezime()+" jelo "+odabranaKuharica.getJelo().getNaziv()+" placa "+odabranaKuharica.getPlaca().toString()+" smjena "+odabranaKuharica.getSmjena()+" " );

        Jelo jelo=jeloChoiceBox.getValue();
        Smjena smjena=smjenaChoiceBox.getValue();
        if(ime == null){
            ime=odabranaKuharica.getIme();
        }
        if(prezime==null){
            prezime=odabranaKuharica.getPrezime();
        }
        if(placa.compareTo(0)==0){
            placa=odabranaKuharica.getPlaca();
        }
        if(smjenaChoiceBox.getValue()==null){
            smjena=odabranaKuharica.getSmjena();
        }
        if(jelo == null){
            jelo=odabranaKuharica.getJelo();
        }
        Kuharica kuharica=new Kuharica(odabranaKuharica.getId(),ime,prezime,placa,smjena,jelo);
        BazaPodataka.promjeniKuharice(kuharica);
        promjena.append("Nova vrijednost, mijenjao zaposlenik datuma " + LocalDateTime.now() + ": ime "+ime+" prezime "+prezime+ " jelo "+jelo.getNaziv()+" placa "+placa.toString()+" smjena "+smjena+" \n " );
    try {
        Path path=Path.of("dat//kuhariceSerijalizacija.bin");
        if(Files.exists(path)){
            Files.delete(Path.of("dat//kuhariceSerijalizacija.bin"));}
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    listKuharicaSerijalizacija.add(promjena.toString());
    serijalizacija.serijaliziraj(listKuharicaSerijalizacija,"dat//kuhariceSerijalizacija.bin");
        listKuharica=BazaPodataka.dohvatiKuharice();
        kuharicaTableView.setItems(FXCollections.observableList(listKuharica));
}
    private String unosPodataka(TextField unos){
        if(unos.getText().isBlank()){
            return null;
        }
        else return unos.getText();
    }
}
