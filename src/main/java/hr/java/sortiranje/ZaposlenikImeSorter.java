package hr.java.sortiranje;

import hr.java.entiteti.Zaposlenik;

import java.util.Comparator;

public class ZaposlenikImeSorter implements Comparator<Zaposlenik> {
    @Override
    public int compare(Zaposlenik o1, Zaposlenik o2) {
        if(o1.getPrezime().compareTo(o2.getPrezime())>0){
            return 1;
        }
        if(o1.getPrezime().compareTo(o2.getPrezime())<0){
            return -1;
        }
       else{
            if(o1.getIme().compareTo(o2.getIme())>0){
                return 1;
            }
            if(o1.getIme().compareTo(o2.getIme())<0){
                return -1;
            }
            else{
                return 0;
            }
        }
    }
}
