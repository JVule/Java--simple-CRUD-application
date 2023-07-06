package hr.java.entiteti;

public abstract class Zaposlenik extends Osoba{
   private Integer placa;

    public Zaposlenik(Integer id, String ime, String prezime, Integer placa) {
        super(id, ime, prezime);
        this.placa = placa;
    }

    public Integer getPlaca() {
        return placa;
    }

    public void setPlaca(Integer placa) {
        this.placa = placa;
    }

}
