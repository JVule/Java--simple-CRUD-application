package hr.java.entiteti;

import java.io.Serializable;

public class Roditelj extends Osoba implements Serializable {
    String nazivDijete;

    public Roditelj(Integer id, String ime, String prezime, String nazivDijete) {
        super(id, ime, prezime);
        this.nazivDijete = nazivDijete;
    }

    public String getDijete() {
        return nazivDijete;
    }

    public void setDijete(String dijete) {
        this.nazivDijete = dijete;
    }


}
