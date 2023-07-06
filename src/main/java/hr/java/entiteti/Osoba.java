package hr.java.entiteti;

public abstract class Osoba  extends Entitet{
   private String ime;
   private String prezime;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Osoba(Integer id, String ime, String prezime) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return (ime+" "+prezime);
    }
}
