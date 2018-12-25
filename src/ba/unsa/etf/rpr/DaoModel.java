package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DaoModel {
    public ArrayList<Grad> gradovi = GeografijaDAO.getInstance().gradovi();
    private ObservableList<Grad> status = FXCollections.observableArrayList();
    private ObservableList<Drzava> status2 = FXCollections.observableArrayList();
    public void dodajGradove() {
        for (Grad g: gradovi
             ) {
            status.add(g);
        }
    }
    public void dodajDrzave() {
        for (Grad g: gradovi
             ) {
            status2.add(g.getDrzava());
        }
    }

    public ObservableList<Grad> getStatus() {
        return status;
    }

    public void setStatus(ObservableList<Grad> status) {
        this.status = status;
    }

    public ObservableList<Drzava> getStatus2() {
        return status2;
    }

    public void setStatus2(ObservableList<Drzava> status2) {
        this.status2 = status2;
    }
}
