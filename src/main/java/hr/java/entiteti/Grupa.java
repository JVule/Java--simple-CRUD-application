package hr.java.entiteti;

import java.io.Serializable;
import java.util.List;

public class Grupa extends Entitet implements Serializable {
   private  String naziv;




    public Grupa(Integer id, String naziv) {
        super(id);
        this.naziv = naziv;

    }

    public String getNaziv() {
        return naziv;
    }

    @Override
    public String toString() {
        return "Grupa "+naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }



}
