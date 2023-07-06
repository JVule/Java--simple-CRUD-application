package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Dijete;
import hr.java.entiteti.Grupa;
import hr.java.entiteti.Roditelj;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.OptionalInt;

public class DijeteDodajController {
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField dobTextField;
    @FXML
    private TextField grupaTextField;
    @FXML
    private TextField roditeljTextField;
    public void dodaj(){
        String ime=imeTextField.getText();
        String prezime=prezimeTextField.getText();
        Grupa grupa=null;
        Roditelj roditelj=null;
        Integer dob=0;
        OptionalInt maxId=BazaPodataka.dohvatiDjecu().stream().mapToInt(s->s.getId()).max();
        Integer id= maxId.getAsInt()+1;
        Optional <Grupa>grupaOptional=Optional.empty();
        Optional<Roditelj>roditeljOptional=Optional.empty();
        if(grupaTextField.getText().isBlank()==false) {
            grupaOptional = BazaPodataka.dohvatiGrupuPoImenu(grupaTextField.getText());
        }
        if(grupaOptional.isPresent()){
            grupa=grupaOptional.get();
        }
        if(!roditeljTextField.getText().isBlank()){
            roditeljOptional=BazaPodataka.dohvatiRoditeljPoImenu(roditeljTextField.getText());
        }
        if(roditeljOptional.isPresent()){
            roditelj=roditeljOptional.get();
        }
        if(dobTextField.getText().isBlank()==false){
            dob=Integer.parseInt(dobTextField.getText());
        }

        if(dob!=0 && ime!=null && prezime!=null && grupaOptional.isEmpty()==false && roditeljOptional.isEmpty()==false) {
            Dijete dijete = new Dijete(id, ime, prezime, roditelj, dob, grupa);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pozor");
            alert.setHeaderText("Dodaj dijete");
            alert.setContentText("Nastaviti?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                BazaPodataka.dodajDijete(dijete);
            } else {

            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pozor");
            alert.setHeaderText("Greška");
            alert.setContentText("Ne mogu dodati dijete");

            alert.showAndWait();
        }
    }

}
