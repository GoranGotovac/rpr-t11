package ba.unsa.etf.rpr;

public class Drzava {
    public  int id;
    public String naziv;
    public Grad grad;
    public Grad glavniGrad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Grad getGlavniGrad() {
        return glavniGrad;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }

    public Drzava(String naziv, Grad grad) {
        this.naziv = naziv;
        this.grad = grad;
    }
    public Drzava() {

    }
    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}
