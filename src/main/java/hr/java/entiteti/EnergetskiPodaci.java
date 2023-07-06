package hr.java.entiteti;

import java.io.Serializable;

public record EnergetskiPodaci(Double gramaza, Double energija) implements NutritivnaVrijednost, Serializable {
    public EnergetskiPodaci(Double gramaza, Double energija) {
        this.gramaza = gramaza;
        this.energija = energija;
    }

    @Override
    public Double gramaza() {
        return gramaza;
    }

    @Override
    public Double energija() {
        return energija;
    }
   public  Double energetskaVrijednost(){
        Double rjesenje=(3*energija+2*gramaza)/5;
        return rjesenje;
    }
}
