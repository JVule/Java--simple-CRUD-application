package hr.java.entiteti;

import java.io.Serializable;

public class Kuharica extends Zaposlenik implements Serializable {
   private Smjena smjena;
   private  Jelo jelo;

    public Kuharica(Integer id, String ime, String prezime, Integer placa, Smjena smjena, Jelo jelo) {
        super(id, ime, prezime, placa);
        this.smjena = smjena;
        this.jelo = jelo;
    }
public static Kuharica valueOf(Integer id, String ime, String prezime,Integer placa, Jelo jelo){
        return new Kuharica(id,ime,prezime,placa,Smjena.DEFAULT,jelo);
}
public  void setValueKuharicaSmjena(String smjena){
        if(smjena.toLowerCase().compareTo("ujutro")==0){
            this.setSmjena(Smjena.UJUTRO);
        }
    if(smjena.toLowerCase().compareTo("popodne")==0){
        this.setSmjena(Smjena.POPODNE);
    }
}
    public Smjena getSmjena() {
        return smjena;
    }

    public void setSmjena(Smjena smjena) {
        this.smjena = smjena;
    }

    public Jelo getJelo() {
        return jelo;
    }

    public void setJelo(Jelo jelo) {
        this.jelo = jelo;
    }
}
