package hr.java.entiteti;

import hr.java.iznimke.KrivaGrupaException;
import hr.java.iznimke.KriviRoditeljException;

public interface SigurnosnaProvjera {
     void provjeraRoditelja(Osoba osoba, Dijete dijete)throws KriviRoditeljException;
     void provjeraGrupe(Dijete dijete,String nazivGrupe)throws KrivaGrupaException;
}
