package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Dijete;
import hr.java.entiteti.Roditelj;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Optional;

public class RoditeljUnosController {
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private ChoiceBox<Dijete> dijeteChoiceBox;
    public void initialize(){
        dijeteChoiceBox.setItems(FXCollections.observableList(BazaPodataka.dohvatiDjecu()));
    }
    public void dodaj(){
        StringBuilder poruka=new StringBuilder();
        Integer id=BazaPodataka.dohvatiRoditelje().stream().mapToInt(s->s.getId()).max().getAsInt()+1;
        String ime=imeTextField.getText();
        String prezime=prezimeTextField.getText();
        String nazivDjeteta=null;
        if(dijeteChoiceBox.getValue()!=null){
            nazivDjeteta=dijeteChoiceBox.getValue().toString();
        }
        if(ime.isBlank()){
            poruka.append("ime je oavezan podatak \n");
        }
        if(prezime.isBlank()){
            poruka.append("prezime je obavezan podatak\n");

        }
        if(nazivDjeteta == null){
            poruka.append("ime djeteta je obavezan podatak");
        }
        if(!ime.isBlank() && !prezime.isBlank() && nazivDjeteta!= null){
            Roditelj roditelj=new Roditelj(id,ime,prezime,nazivDjeteta);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Dodavanje roditelja");
            alert.setHeaderText("");
            alert.setContentText("Želite li dodati roditelja?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                    BazaPodataka.dodajRoditelja(roditelj);
            } else {

            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("greška");
            alert.setHeaderText("greška");
            alert.setContentText(poruka.toString());

            alert.showAndWait();
        }
    }
}
