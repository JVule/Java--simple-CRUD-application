package hr.java.entiteti;

import hr.java.iznimke.KrivaGrupaException;
import hr.java.iznimke.KriviRoditeljException;
import hr.java.iznimke.PredugBoravakException;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Dolazak extends Entitet implements SigurnosnaProvjera, Serializable {
    private Roditelj roditelj;
    private Dijete dijete;
    private Odgajatelj odgajatelj;
    private LocalDateTime vrijemeDolaska;
    private String nazivGrupe;

    public Dolazak(Integer id, Roditelj roditelj, Dijete dijete, Odgajatelj odgajatelj, LocalDateTime vrijemeDolaska, String naziv) {
        super(id);
        this.roditelj = roditelj;
        this.dijete = dijete;
        this.odgajatelj = odgajatelj;
        this.vrijemeDolaska = vrijemeDolaska;
        this.nazivGrupe=naziv;
    }


    public String getNazivGrupe() {
        return nazivGrupe;
    }

    public void setNazivGrupe(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
    }


    public Roditelj getRoditelj() {
        return roditelj;
    }

    public void setRoditelj(Roditelj roditelj) {
        this.roditelj = roditelj;
    }

    public Dijete getDijete() {
        return dijete;
    }

    public void setDijete(Dijete dijete) {
        this.dijete = dijete;
    }

    public Odgajatelj getOdgajatelj() {
        return odgajatelj;
    }

    public void setOdgajatelj(Odgajatelj odgajatelj) {
        this.odgajatelj = odgajatelj;
    }

    public LocalDateTime getVrijemeDolaska() {
        return vrijemeDolaska;
    }

    public void setVrijemeDolaska(LocalDateTime vrijemeDolaska) {
        this.vrijemeDolaska = vrijemeDolaska;
    }
     public void provjeraRoditelja(Osoba osoba, Dijete dijete)throws KriviRoditeljException{
        if(osoba==null){
            throw new KriviRoditeljException("ne postoji navedeni roditelj");
        }
        if(osoba.toString().compareTo(dijete.getRoditelj().toString())!=0){
            throw new KriviRoditeljException("krivi roditelj "+osoba.toString());
        }
    }
    public void provjeraGrupe(Dijete dijete,String nazivGrupe)throws KrivaGrupaException{
        if(!dijete.getGrupa().getNaziv().toLowerCase().contains(nazivGrupe.toLowerCase())){
            throw new KrivaGrupaException("dijete se nalazi u krivoj grupi: "+nazivGrupe+" potrebno "+dijete.getGrupa().getNaziv());
        }
    }
    public void provjeraBoravka()throws PredugBoravakException {
        LocalTime vrijeme=LocalTime.of(this.vrijemeDolaska.getHour(),this.vrijemeDolaska.getMinute());
        if(vrijeme.getHour()-10<0){
            throw new PredugBoravakException("Predug boravak djeteta "+dijete);
        }
    }
}
