package hr.java.entiteti;

import java.io.Serializable;

public class Jelo extends Entitet implements Serializable {
    private String naziv;
    private Integer masti;
    private Integer proteini;
    private Integer ugljikohidrati;
    private EnergetskiPodaci podaci;
    public static class Builder extends Entitet implements  Serializable{
        private String naziv;
        private Integer masti;
        private Integer proteini;
        private Integer ugljikohidrati;
        private EnergetskiPodaci podaci;

        public Builder(Integer id,String naziv,EnergetskiPodaci podaci) {
            super(id);
            this.naziv = naziv;
            this.podaci=podaci;
        }

        public Builder masti(Integer masti) {
            this.masti = masti;
            return this;
        }

        public Builder proteini(Integer proteini) {
            this.proteini = proteini;
            return this;
        }

        public Builder ugljikohidrati(Integer ugljikohidrati) {
            this.ugljikohidrati = ugljikohidrati;
            return this;
        }
        public Jelo build(){
            return new Jelo(this);
        }
    }
        private Jelo(Builder builder) {
        super(builder.getId());
            this.naziv=builder.naziv;
            this.masti = builder.masti;
            this.proteini = builder.proteini;
            this.ugljikohidrati = builder.ugljikohidrati;
            this.podaci=builder.podaci;
        }

    @Override
    public String toString() {
        return getNaziv();
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getMasti() {
        return masti;
    }

    public void setMasti(Integer masti) {
        this.masti = masti;
    }

    public Integer getProteini() {
        return proteini;
    }

    public void setProteini(Integer proteini) {
        this.proteini = proteini;
    }

    public Integer getUgljikohidrati() {
        return ugljikohidrati;
    }

    public void setUgljikohidrati(Integer ugljikohidrati) {
        this.ugljikohidrati = ugljikohidrati;
    }

    public EnergetskiPodaci getPodaci() {
        return podaci;
    }

    public void setPodaci(EnergetskiPodaci podaci) {
        this.podaci = podaci;
    }
}

