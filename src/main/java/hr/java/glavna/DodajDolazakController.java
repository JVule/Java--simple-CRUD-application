package hr.java.glavna;
import hr.java.baza.BazaPodataka;
import hr.java.entiteti.*;
import hr.java.iznimke.KrivaGrupaException;
import hr.java.niti.DodajDolazakNit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DodajDolazakController {
    private final Logger logger= LoggerFactory.getLogger("DodajDolazakController.java");
       @FXML
    private TextField imeRoditeljaTextField;
       @FXML
    private TextField imeDjetetaTextField;
@FXML
private TextField vrijemeDolaskaTextField;
       @FXML
    private DatePicker dateTimePicker;
       @FXML
    private TextField nazivGrupeTextField;
    public void prikaziGlavniEkran()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplikacija.class.getResource("/roditeljIzbornik.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Aplikacija.getMainStage().setTitle("Projekt-Vrtić");
        Aplikacija.getMainStage().setScene(scene);
        Aplikacija.getMainStage().show();
    }
    public  void spremiNoviDolazak(){
        DodajDolazakNit dodajDolazakNit=new DodajDolazakNit(imeRoditeljaTextField,imeDjetetaTextField,vrijemeDolaskaTextField,dateTimePicker,nazivGrupeTextField);
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.execute(dodajDolazakNit);
        executorService.shutdown();
    }


}
