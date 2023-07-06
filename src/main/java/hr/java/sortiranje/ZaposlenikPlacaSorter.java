package hr.java.sortiranje;

import hr.java.entiteti.Zaposlenik;

import java.util.Comparator;

public class ZaposlenikPlacaSorter implements Comparator<Zaposlenik> {
    @Override
    public int compare(Zaposlenik o1, Zaposlenik o2) {
        if(o1.getPlaca().compareTo(o2.getPlaca())>0){
            return 1;
        }
        else  if(o1.getPlaca().compareTo(o2.getPlaca())<0){
            return -1;
        }
        else{
            return 0;
        }
    }
}
