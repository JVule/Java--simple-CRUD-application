package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.EnergetskiPodaci;
import hr.java.entiteti.Entitet;
import hr.java.entiteti.Jelo;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class JeloUnosController {
    @FXML
    private TextField nazivJelaTextField;
    @FXML
    private Spinner<Integer> mastiSpinner;
    @FXML
    private Spinner<Integer> ugljikohidratiSpinner;
    @FXML
    private Spinner<Integer> proteiniSpinner;
    public void initialize(){
        SpinnerValueFactory<Integer> valueFactory1=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000);
        valueFactory1.setValue(0);
        SpinnerValueFactory<Integer> valueFactory2=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000);
        valueFactory2.setValue(0);
        SpinnerValueFactory<Integer> valueFactory3=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000);
        valueFactory3.setValue(0);
        mastiSpinner.setValueFactory(valueFactory1);
        ugljikohidratiSpinner.setValueFactory(valueFactory2);
        proteiniSpinner.setValueFactory(valueFactory3);

    }
    public void dodaj(){
        Jelo jelo=null;
        EnergetskiPodaci podaci= BazaPodataka.dohvatiJelo().get(0).getPodaci();
        String naziv=unosPodataka(nazivJelaTextField);
        Integer id=BazaPodataka.dohvatiJelo().stream().mapToInt(Entitet::getId).max().getAsInt()+1;
        if(naziv == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pozor");
            alert.setHeaderText(null);
            alert.setContentText("naziv jela je obavezan podatak");
            alert.showAndWait();
        }
        else {


            if (ugljikohidratiSpinner.getValue() != 0 && proteiniSpinner.getValue() != 0 && mastiSpinner.getValue() != 0) {
                 jelo = new Jelo.Builder(id, naziv, podaci).proteini(proteiniSpinner.getValue()).masti(mastiSpinner.getValue()).ugljikohidrati(ugljikohidratiSpinner.getValue()).build();
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pozor");
            alert.setHeaderText(null);
            alert.setContentText("Želite li spremiti novo jelo?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                BazaPodataka.dodajJelo(jelo);
            } else {

            }
        }
    }
    private String unosPodataka(TextField unos){
        if(unos.getText().isBlank()){
            return null;
        }
        else return unos.getText();
    }

}
