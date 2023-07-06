package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JeloPrikazController {
    @FXML
    private TextField nazivTextField;
    @FXML
    private Spinner<Integer> uglikohidratiSpinner;
    @FXML
    private Spinner<Integer> proteiniSpinner;
    @FXML
    private Spinner<Integer> mastiSpinner;
    @FXML
    private TableColumn<Jelo, String> nazivTableColumn;
    @FXML
    private TableColumn<Jelo, String> ugljikohidratiTableColumn;
    @FXML
    private TableColumn<Jelo, String> proteiniTableColumn;
    @FXML
    private TableColumn<Jelo, String> mastiTableColumn;
    @FXML
    private TableView<Jelo> jeloTableView;
    private List<Jelo> listaJela;

    public void initialize() {
        listaJela = BazaPodataka.dohvatiJelo();
        nazivTableColumn.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getNaziv()));
        ugljikohidratiTableColumn.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getUgljikohidrati().toString()));
        mastiTableColumn.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getMasti().toString()));
        proteiniTableColumn.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getProteini().toString()));
        jeloTableView.setItems(FXCollections.observableList(listaJela));
        SpinnerValueFactory<Integer> valueFactory1=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000);
        valueFactory1.setValue(0);
        SpinnerValueFactory<Integer> valueFactory2=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000);
        valueFactory2.setValue(0);
        SpinnerValueFactory<Integer> valueFactory3=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000);
        valueFactory3.setValue(0);
        mastiSpinner.setValueFactory(valueFactory1);
        uglikohidratiSpinner.setValueFactory(valueFactory2);
        proteiniSpinner.setValueFactory(valueFactory3);

    }

    public void pretrazi() {
        List<Jelo> jeloList = listaJela;
        String nazivJela=unosPodataka(nazivTextField);
        if (nazivJela != null) {
            jeloList = jeloList.stream().filter(s -> s.getNaziv().toLowerCase().contains(nazivJela.toLowerCase())).collect(Collectors.toList());
        }
        if (proteiniSpinner.getValue() != 0) {
            jeloList = jeloList.stream().filter(s -> s.getProteini().compareTo(proteiniSpinner.getValue()) == 0).collect(Collectors.toList());
        }
        if (mastiSpinner.getValue() != 0) {
            jeloList = jeloList.stream().filter(s -> s.getMasti().compareTo(mastiSpinner.getValue()) == 0).collect(Collectors.toList());

        }
        if (uglikohidratiSpinner.getValue() != 0) {
            jeloList = jeloList.stream().filter(s -> s.getUgljikohidrati().compareTo(uglikohidratiSpinner.getValue()) == 0).collect(Collectors.toList());

        }
        jeloTableView.setItems(FXCollections.observableList(jeloList));
    }

    public void promjeni() {
        StringBuilder promjena=new StringBuilder();
        Serijalizacija<String> serijalizacija=new Serijalizacija<>();
        String pathString="dat//jeloSerijalizacija.bin";
        List<String> jeloListSerijalizacija=serijalizacija.deserijaliziraj(pathString);

        try {
            Path path=Path.of(pathString);
            if(Files.exists(path)){
                Files.delete(path);}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Jelo odabranoJelo = jeloTableView.getSelectionModel().getSelectedItem();
        promjena.append("Stara vrijednost, promjenjena od zaposlenika datuma "+ LocalDateTime.now()+" naziv "+odabranoJelo.getNaziv()+" masti "+odabranoJelo.getMasti().toString()+" ugljikohidrati "+odabranoJelo.getUgljikohidrati().toString()+" proteini "+odabranoJelo.getProteini().toString());
        String naziv = unosPodataka(nazivTextField);
        Integer masti = 0;
        Integer ugljikohidrati = 0;
        Integer proteini = 0;
        if (naziv== null) {
            naziv = odabranoJelo.getNaziv();
        }
        if (mastiSpinner.getValue() == 0) {
            masti = odabranoJelo.getMasti();
        } else {
            masti = mastiSpinner.getValue();
        }
        if (uglikohidratiSpinner.getValue() == 0) {
            ugljikohidrati = odabranoJelo.getUgljikohidrati();
        } else {
            ugljikohidrati = uglikohidratiSpinner.getValue();
        }
        if (proteiniSpinner.getValue() == 0) {
            proteini = odabranoJelo.getProteini();
        } else {
            proteini = proteiniSpinner.getValue();
        }
        Jelo jelo = new Jelo.Builder(odabranoJelo.getId(), naziv, odabranoJelo.getPodaci()).masti(masti).proteini(proteini).ugljikohidrati(ugljikohidrati).build();
        promjena.append("Nova vrijednost naziv "+naziv+" masti "+masti.toString()+" ugljikohidrati"+ugljikohidrati.toString()+" proteini "+proteini.toString()+" \n");
        jeloListSerijalizacija.add(promjena.toString());
        serijalizacija.serijaliziraj(jeloListSerijalizacija,pathString);
        BazaPodataka.promjeniJelo(jelo);
        listaJela = BazaPodataka.dohvatiJelo();
        jeloTableView.setItems(FXCollections.observableList(listaJela));

    }

public void obrisi(){
    Jelo jelo=jeloTableView.getSelectionModel().getSelectedItem();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Pozor");
    alert.setHeaderText("Brisanje");
    alert.setContentText("Želite li obrisati "+jelo.getNaziv());

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        BazaPodataka.obrisiJelo(jelo);
        listaJela=BazaPodataka.dohvatiJelo();
        jeloTableView.setItems(FXCollections.observableList(listaJela));
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
