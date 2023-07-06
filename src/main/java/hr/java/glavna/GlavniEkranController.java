package hr.java.glavna;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class GlavniEkranController {

    private static final Logger logger= LoggerFactory.getLogger(GlavniEkranController.class);

    @FXML
    private TextField korisnickoImeTextField;
    @FXML
    private PasswordField lozinkaPasswordField;

    HashMap<String,String> mapaKorisnika;
    public void prikaziGlavniEkran()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/glavniEkran.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }

    public void initialize(){
        mapaKorisnika=new HashMap<>();
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(new File("dat/korisnici.txt")))){
            List<String> datotekaString=bufferedReader.lines().collect(Collectors.toList());
            for(int i=0;i <= datotekaString.size()/2;i+=2){
                    String key, value;
                    key=datotekaString.get(i);
                    value=datotekaString.get(i+1);
                    mapaKorisnika.put(key,value);
            }
        }
        catch(IOException ex){
            logger.error("greska prilikom ucitavanja datoteke s korisnicima");
        }

    korisnickoImeTextField.setPromptText("korisničko ime");
    lozinkaPasswordField.setPromptText("lozinka");
}
    public void prikaziRoditeljEkran()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/roditeljIzbornik.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
    public void prikaziZaposlenikEkran() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/izbornik.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }

    public void ulogiraj() throws NoSuchAlgorithmException {
        Enkripcija enkripcija=new Enkripcija();
        String korisnickoIme=korisnickoImeTextField.getText();
        String lozinka=enkripcija.encryptString(lozinkaPasswordField.getText());
        if(provjeriKorisnika(korisnickoIme,lozinka,mapaKorisnika)==true){
            if(korisnickoIme.compareTo("roditelj")==0){
                try {
                    prikaziRoditeljEkran();
                } catch (IOException e) {
                    logger.error("ne mogu otvoriti roditelj glavni ekran");
                    throw new RuntimeException(e);
                }
            }
            if(korisnickoIme.compareTo("zaposlenik")==0){
                try {
                    prikaziZaposlenikEkran();
                } catch (IOException e) {
                    logger.error("ne mogu otvoriti zaposlenik ekran");
                    throw new RuntimeException(e);
                }
            }
        }


    }
    public boolean provjeriKorisnika(String ime, String lozinka, HashMap<String, String> mapa){
        StringBuilder poruka=new StringBuilder();
        boolean vrijednost=false;
        if(ime.isEmpty()==true){
            poruka.append("unesite korisnicko ime ");
        }
        if(lozinka.isEmpty()==true){
            poruka.append("unesite lozinku ");
        }
        if(ime.isEmpty() || lozinka.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pozor");
            alert.setHeaderText("Ulogiranje neuspješno");
            alert.setContentText(poruka.toString());

            alert.showAndWait();
        }
        if(ime.isEmpty()==false && lozinka.isEmpty()==false){
            if(mapa.containsKey(ime)==true){
                if(lozinka.compareTo(mapa.get(ime))==0){
                    vrijednost=true;
                    return vrijednost;
                }
            }
        }

        if(mapa.containsKey(ime)==false && ime.isEmpty()==false){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pozor");
            alert.setHeaderText("Ulogiranje neuspješno");
            alert.setContentText("neispravno korisničko ime");

            alert.showAndWait();
        }
        if(mapa.containsKey(ime)){
        if(lozinka.compareTo(mapa.get(ime))!=0 && lozinka.isEmpty()==false){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pozor");
            alert.setHeaderText("Ulogiranje neuspješno");
            alert.setContentText("neispravna lozinka");

            alert.showAndWait();
        }}

        return vrijednost;
    }
    }


