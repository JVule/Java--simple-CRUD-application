package hr.java.entiteti;

import hr.java.sortiranje.ZaposlenikImeSorter;
import hr.java.sortiranje.ZaposlenikPlacaSorter;

import java.util.List;
import java.util.stream.Collectors;

public class Kolektiv <T extends Zaposlenik> extends Entitet{
    private List<T> listaZaposlenika;

    public Kolektiv(Integer id, List<T> listaZaposlenika) {
        super(id);
        this.listaZaposlenika = listaZaposlenika;
    }

    public List<T> getListaZaposlenika() {
        return listaZaposlenika;
    }

    public void setListaZaposlenika(List<T> listaZaposlenika) {
        this.listaZaposlenika = listaZaposlenika;
    }
    public void sortirajZaposlenikePoPlaci(){
        listaZaposlenika=this.listaZaposlenika.stream().sorted(new ZaposlenikPlacaSorter()).collect(Collectors.toList());
    }
    public void sortirajZaposlenikePoImenu(){
        listaZaposlenika=this.listaZaposlenika.stream().sorted(new ZaposlenikImeSorter()).collect(Collectors.toList());
    }


}
