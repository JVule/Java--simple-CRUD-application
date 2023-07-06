package hr.java.entiteti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Odgajatelj extends Osoba implements Anketa, Serializable {
    Grupa grupa;
    Integer placa;
    List<Integer> listOcjenaRada;

    public List<Integer> getListOcjenaRada() {
        return listOcjenaRada;
    }

    public void setListOcjenaRada(List<Integer> listOcjenaRada) {
        this.listOcjenaRada = listOcjenaRada;
    }

    public Odgajatelj(Integer id, String ime, String prezime, Grupa grupa, Integer placa) {
        super(id, ime, prezime);
        this.grupa = grupa;
        this.placa = placa;
        this.listOcjenaRada=new ArrayList<>();
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public Integer getPlaca() {
        return placa;
    }

    public void setPlaca(Integer placa) {
        this.placa = placa;
    }
    public Double ocjeniOdgajatelja( Integer ocjena){
        Double ocjenaRada=0.0;
        this.getListOcjenaRada().add(ocjena);
        for(Integer ocjene: this.getListOcjenaRada()){
            ocjenaRada+=ocjena;
        }
        return ocjenaRada/this.getListOcjenaRada().size();
    }
}
