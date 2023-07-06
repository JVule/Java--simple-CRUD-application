package hr.java.niti;

import hr.java.baza.BazaPodataka;
import hr.java.entiteti.*;
import hr.java.iznimke.KrivaGrupaException;
import hr.java.iznimke.NemaRoditeljException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class DodajDolazakNit implements Runnable {
    private final Logger logger= LoggerFactory.getLogger(DodajDolazakNit.class);
    private TextField imeRoditeljaTextField;

    private TextField imeDjetetaTextField;

    private TextField vrijemeDolaskaTextField;

    private DatePicker dateTimePicker;

    private TextField nazivGrupeTextField;

    public DodajDolazakNit(TextField imeRoditeljaTextField, TextField imeDjetetaTextField, TextField vrijemeDolaskaTextField, DatePicker dateTimePicker, TextField nazivGrupeTextField) {
        this.imeRoditeljaTextField = imeRoditeljaTextField;
        this.imeDjetetaTextField = imeDjetetaTextField;
        this.vrijemeDolaskaTextField = vrijemeDolaskaTextField;
        this.dateTimePicker = dateTimePicker;
        this.nazivGrupeTextField = nazivGrupeTextField;
    }

    @Override
    public void run() {
        Platform.runLater(()->{
            Serijalizacija<String> serijalizacija=new Serijalizacija<>();
            String pathString="dat//dolazakSerijalizacija.bin";
            List<String>listDolasciSerijalizacija=serijalizacija.deserijaliziraj(pathString);
            try {
                Path path=Path.of(pathString);
                if(Files.exists(path)){
                    Files.delete(path);}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String imeRoditelja=imeRoditeljaTextField.getText();
            String imeDjeteta=imeDjetetaTextField.getText();
            String nazivGrupe=nazivGrupeTextField.getText();
            Integer id=0;
            LocalDate datum=dateTimePicker.getValue();
            String vrijemeDolaskaString=vrijemeDolaskaTextField.getText();
            LocalTime vrijemeDolaska=null;
            LocalDateTime  datumDolaska;
            StringBuilder poruka=new StringBuilder();
            Dijete dijete=null;
            if(imeRoditelja.isEmpty()){
                poruka.append("unesite ime i prezime \n");
            }
            if(imeDjeteta.isEmpty()){
                poruka.append("unesite ime i prezime djeteta \n");
            }
            if(datum==null){
                poruka.append("unesite datum dolaska \n");
            }
            if(vrijemeDolaskaString.isEmpty()){
                poruka.append("unesite vrijeme dolaska \n");
            }
            if(nazivGrupe.isEmpty()){
                poruka.append("unesite naziv grupe \n");
            }
            if(datum!=null && imeRoditelja.isEmpty()==false && imeDjeteta.isEmpty()==false && vrijemeDolaskaString.isEmpty()==false&&nazivGrupe.isEmpty()==false){
                Optional<Roditelj> Optionalroditelj= BazaPodataka.dohvatiRoditeljPoImenu(imeRoditelja);
                Roditelj roditelj=Optionalroditelj.get();
                if(roditelj==null){
                    String porukaError="nema takvog roditelja";
                    logger.error(porukaError);
                    throw new NemaRoditeljException(porukaError);

                }
                Odgajatelj odgajatelj=BazaPodataka.dohvatiOdgajateljaPoGrupi(nazivGrupe);
                for(Dijete potencijalnoDijete:BazaPodataka.dohvatiDjecu()){
                    if(potencijalnoDijete.toString().toLowerCase().contains(imeDjeteta.toLowerCase())){
                        dijete=potencijalnoDijete;
                    }
                }
                if(dijete == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Pozor");
                    alert.setHeaderText(null);
                    alert.setContentText("Dijete ne postoji u bazi, javite se adminima");

                    alert.showAndWait();
                    logger.error("provjeri dijete u bazi " +imeDjeteta);
                    throw new NemaRoditeljException("provjeri dijete u bazi " +imeDjeteta);
                }
                vrijemeDolaska=LocalTime.parse(vrijemeDolaskaString);
                datumDolaska=LocalDateTime.of(datum,vrijemeDolaska);
                Grupa grupa=null;
                Optional<Grupa> optionalGrupa=BazaPodataka.dohvatiGrupuPoImenu(nazivGrupe);
                if(optionalGrupa.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Greška");
                    alert.setHeaderText("");
                    alert.setContentText("ne postoji unesena grupa");

                    alert.showAndWait();
                }
                else{
                    grupa=optionalGrupa.get();
                    List<Dolazak> listaDolazaka=BazaPodataka.dohvatiDolaske();
                    OptionalInt maxId=listaDolazaka.stream().mapToInt(s->s.getId()).max();
                    id= maxId.getAsInt()+1;
                    Dolazak noviDolazak=new Dolazak(id,roditelj,dijete,odgajatelj,datumDolaska,nazivGrupe);
                    String promjena="Dodan je novi dolazak od roditelja datuma " +LocalDateTime.now()+ " roditelj "+roditelj.toString()+" dijete "+dijete+" odgajatelj "+odgajatelj+" datum dolaska "+datumDolaska.toString()+" u grupu "+grupa.getNaziv()+"\n";
                    listDolasciSerijalizacija.add(promjena);
                    noviDolazak.provjeraRoditelja(roditelj,dijete);
                    try {
                        if (dijete != null) {
                            noviDolazak.provjeraGrupe(dijete,nazivGrupe);
                        }
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Potvrda");
                        alert.setHeaderText("");
                        alert.setContentText("Želite li potvrditi novi posjet?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            BazaPodataka.dodajDolazak(noviDolazak);
                            serijalizacija.serijaliziraj(listDolasciSerijalizacija,pathString);
                        } else {

                        }
                    } catch (KrivaGrupaException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("");
                        alert.setHeaderText("Pozor");
                        alert.setContentText(e.getMessage());

                        alert.showAndWait();
                        throw new RuntimeException(e);
                    }

                }


            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("Pozor");
                alert.setContentText(poruka.toString());

                alert.showAndWait();
            }
        });
    }
}
