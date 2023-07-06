package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KuharicaUnosController {
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private ChoiceBox<Smjena> smjenaChoiceBox;
    @FXML
    private ChoiceBox<Jelo> jeloChoiceBox;
    @FXML
    private TextField placaTextField;
    private List<Smjena> smjenaList;
    private  List<Jelo> listJela;
    public void initialize(){
        smjenaList=new ArrayList<>();
        smjenaList.add(Smjena.UJUTRO);
        smjenaList.add(Smjena.POPODNE);
        smjenaList.add(null);
        smjenaChoiceBox.setItems(FXCollections.observableList(smjenaList));
        listJela= BazaPodataka.dohvatiJelo();
        listJela.add(null);
        jeloChoiceBox.setItems(FXCollections.observableList(listJela));
    }
    public void spremi(){
        StringBuilder poruka=new StringBuilder();
        Integer placa=0;
        Integer id= BazaPodataka.dohvatiKuharice().stream().mapToInt(s->s.getId()).max().getAsInt()+1;
        Jelo jelo=null;
        Smjena smjena=null;
        String ime=unosPodataka(imeTextField);
        String prezime=unosPodataka(prezimeTextField);
        if(unosPodataka(placaTextField)!=null){
         placa=Integer.parseInt(unosPodataka(placaTextField));
        }
        else{
            poruka.append("plaća je obavezan podatak\n");
        }

        if(ime == null){
            poruka.append("ime je obavezan podatak\n");
        }
        if(prezime == null){
            poruka.append("prezime je obavezan podatak\n");
        }
        if(jeloChoiceBox.getValue() == null){
            poruka.append("jelo je obavezan podatak\n");
        }
        if(smjenaChoiceBox.getValue() == null){
            poruka.append("smjena je obavezan podatak\n");
        }
if(ime != null && prezime!=null && jeloChoiceBox.getValue()!=null && smjenaChoiceBox.getValue() != null && placa!=0){
    smjena=smjenaChoiceBox.getValue();
    jelo=jeloChoiceBox.getValue();
    Kuharica kuharica=new Kuharica(id,ime,prezime,placa,smjena,jelo);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Pozor");
    alert.setHeaderText(null);
    alert.setContentText("Želite li spremiti novu kuharicu");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        BazaPodataka.dodajKuharicu(kuharica);
    } else {

    }
}
else{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Pozor");
    alert.setHeaderText("greška");
    alert.setContentText(poruka.toString());

    alert.showAndWait();
}
    }
    private String unosPodataka(TextField unos){
        if(unos.getText().isBlank()){
            return null;
        }
        else return unos.getText();
    }

}
