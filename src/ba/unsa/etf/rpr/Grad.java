package ba.unsa.etf.rpr;

public class Grad {
    public String naziv;
    public Drzava drzava;
    public int id;
    public int brojStanovnika;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public String getNaziv() {
        return naziv;
    }

    public Grad(String naziv, int brojStanovnika) {
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
    }
    public Grad() {

    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }
}
