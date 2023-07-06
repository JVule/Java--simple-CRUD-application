package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Dijete;
import hr.java.entiteti.Grupa;
import hr.java.entiteti.Roditelj;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
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
    private ChoiceBox<Roditelj> roditeljChoiceBox;
    public void initialize(){
        List<Roditelj> listRoditelj=BazaPodataka.dohvatiRoditelje();
        roditeljChoiceBox.setItems(FXCollections.observableList(listRoditelj));
    }
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
        if(roditeljChoiceBox.getValue() != null){
            roditelj=roditeljChoiceBox.getValue();
        }

        if(dobTextField.getText().isBlank()==false){
            dob=Integer.parseInt(dobTextField.getText());
        }

        if(dob!=0 && ime!=null && prezime!=null && roditelj!=null) {
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
