package hr.java.entiteti;

import java.io.Serializable;

public class Dijete extends Osoba implements Serializable {
   private Roditelj roditelj;

   private Integer dob;
   private Grupa grupa;

    public Dijete(Integer id, String ime, String prezime, Roditelj roditelj, Integer dob,Grupa grupa) {
        super(id, ime, prezime);
        this.roditelj = roditelj;
        this.dob = dob;
        this.grupa= grupa;
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public Roditelj getRoditelj() {
        return roditelj;
    }

    public void setRoditelj(Roditelj mama) {
        this.roditelj = mama;
    }



    public Integer getDob() {
        return dob;
    }

    public void setDob(Integer dob) {
        this.dob = dob;
    }


}
