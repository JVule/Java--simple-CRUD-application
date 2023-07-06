package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Grupa;
import hr.java.entiteti.Odgajatelj;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.util.Optional;

public class OdgajateljUnosController {
    @FXML
    private TextField imeTextField;
    @FXML
    private  TextField prezimeTextField;
    @FXML
    private ChoiceBox<Grupa> grupaChoiceBox;
    @FXML
    private  TextField placaTextField;
    public void initialize(){
        grupaChoiceBox.setItems(FXCollections.observableList(BazaPodataka.dohvatiGrupe()));
    }
    public void dodaj(){
        StringBuilder poruka=new StringBuilder();
        Integer id= BazaPodataka.dohvatiOdgajatelje().stream().mapToInt(s->s.getId()).max().getAsInt()+1;
        String ime=unosTexta(imeTextField);
        String prezime=unosTexta(prezimeTextField);
        String placaString=unosTexta(placaTextField);
        Grupa grupa=grupaChoiceBox.getValue();
        Integer placa=0;
        if(placaString!=null)
        { placa=Integer.parseInt(placaString);}
        if(ime == null){
            poruka.append("ime je obavezan podatak \n");
        }
        if(prezime == null){
            poruka.append("prezime je obavezan podatak \n");
        }
        if(grupa == null){
            poruka.append("grupa je obavezan podatak \n");
        }
        if(placa == 0){
            poruka.append("placa je obavezan podatak");
        }
        if(ime != null && prezime !=null && grupa!=null && placa!=0){
            Odgajatelj noviOdgajatelj=new Odgajatelj(id,ime,prezime,grupa,placa);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unos novog odgajatelja");
            alert.setHeaderText("Unos");
            alert.setContentText("Želite li unijeti novog odgajatelja");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                BazaPodataka.dodajOdgajatelja(noviOdgajatelj);
            } else {

            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Greška");
            alert.setContentText(poruka.toString());

            alert.showAndWait();
        }
    }
    private String unosTexta(TextField unos){
        if(unos.getText().isBlank()){
            return null;
        }
        else return unos.getText();
    }
}
