package hr.java.glavna;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.Odgajatelj;
import hr.java.entiteti.Roditelj;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AnketaController {
    private final Logger logger= LoggerFactory.getLogger(AnketaController.class);
    @FXML
    private TextField odgajateljImeTextField;
    @FXML
    private ChoiceBox<Roditelj> roditeljChoiceBox;
    @FXML
    private ChoiceBox<Integer> ocjeneMenuButton;
    public void initialize(){
        Integer[] ocjeneBrojevi={1,2,3,4,5};
        ocjeneMenuButton.getItems().addAll(ocjeneBrojevi);
        roditeljChoiceBox.getItems().addAll(BazaPodataka.dohvatiRoditelje());
    }
    public void povratak() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/roditeljIzbornik.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
    public void spremi(){
        String imeOdgajatelja=odgajateljImeTextField.getText();
        System.out.println(ocjeneMenuButton.getValue());
        Odgajatelj odabraniOdgajatelj=null;
        Integer ocjena=0;
        if(ocjeneMenuButton.isPressed()){
            ocjena=ocjeneMenuButton.getValue();
        }
       if(imeOdgajatelja.isEmpty()==false){
           for(Odgajatelj odgajatelj:BazaPodataka.dohvatiOdgajatelje()){
               if(odgajatelj.toString().toLowerCase().compareTo(imeOdgajatelja.toLowerCase())==0){
                   odabraniOdgajatelj=odgajatelj;
               }
           }
       }
       if(odabraniOdgajatelj==null){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Pozor");
           alert.setHeaderText("");
           alert.setContentText("Ne postoji traženi odgajatelj");

           alert.showAndWait();
       }
       if(roditeljChoiceBox.isPressed() && ocjeneMenuButton.isPressed() && imeOdgajatelja.isEmpty()==false){
           logger.info("Prosjek odgajatelja "+odabraniOdgajatelj+ " "+odabraniOdgajatelj.ocjeniOdgajatelja(ocjena).toString());
           BazaPodataka.dodajOcjenuOdgajatelju(odabraniOdgajatelj,ocjena);
       }
        }


}
