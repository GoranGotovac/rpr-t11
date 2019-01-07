package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.cert.CollectionCertStoreParameters;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GeografijaDAO {
    private Connection conn;
    private PreparedStatement stmt;
    private static GeografijaDAO instance = null;
    public ArrayList<Grad> gradovi = new ArrayList<>();
    public ArrayList<Drzava> drzave = new ArrayList<>();
    private ObservableList<Grad> status = FXCollections.observableArrayList();
    private ObservableList<Drzava> status2 = FXCollections.observableArrayList();

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

    private static void initialize() {
        instance = new GeografijaDAO();
    }

    public GeografijaDAO() {
        try {
            String url = "jdbc:sqlite:resources/baza.db";
            conn = DriverManager.getConnection(url);
            novaTabela();

            try{ PreparedStatement stmt = conn.prepareStatement("SELECT * FROM grad WHERE naziv = ?");
                stmt.setString(1,"Beč");
                ResultSet rs =stmt.executeQuery();
                if(!rs.next() || rs.isClosed()) {

                    upisi();
                }
        } catch (Exception e) {
            e.printStackTrace();
        } }
        catch (Exception e) {
                e.printStackTrace();
        }
    }

    private void novaTabela() {
        String grad = "CREATE TABLE IF NOT EXISTS grad\n" +
                "(\n" +
                "    id int PRIMARY KEY,\n" +
                "    naziv text,\n" +
                "    broj_stanovnika int,\n" +
                "    drzava int,\n" +
                "    CONSTRAINT grad_drzava_id_fk FOREIGN KEY (drzava) REFERENCES drzava (id)\n" +
                ");";
        String drzava = "CREATE TABLE IF NOT EXISTS drzava\n" +
                "(\n" +
                "    id int PRIMARY KEY,\n" +
                "    naziv text,\n" +
                "    glavni_grad int,\n" +
                "    CONSTRAINT drzava_grad_id_fk FOREIGN KEY (glavni_grad) REFERENCES grad (id)\n" +
                ");";
        try {
            PreparedStatement gradovi = conn.prepareStatement(grad);
            PreparedStatement drzave = conn.prepareStatement(drzava);
            gradovi.execute();
            drzave.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GeografijaDAO getInstance() {
        if (instance == null) initialize();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }
    Drzava nadjiDrzavu2(String grad) {
        for (Grad g: gradovi
             ) {
            if(g.getNaziv().equals(grad))
                return g.getDrzava();
        }
        return null;
    }
    Grad dajGrad(String grad) {
        for (Grad g: gradovi
             ) {
            if(g.getNaziv().equals(grad))
                return g;
        }
        return null;
    }
    private void upisi() {
        Grad grad1 = new Grad("Pariz", 2206488);
        Drzava drzava1 = new Drzava("Francuska", grad1);
        grad1.setDrzava(drzava1);
        dodajGrad(grad1);
        drzava1.setGlavniGrad(grad1);
        dodajDrzavu(drzava1);
        Grad grad2 = new Grad("London", 8825000);
        Drzava drzava2 = new Drzava("Velika Britanija", grad2);
        grad2.setDrzava(drzava2);
        dodajGrad(grad2);
        drzava2.setGlavniGrad(grad2);
        dodajDrzavu(drzava2);
        Grad grad3 = new Grad("Manchester", 545500);
        grad3.setDrzava(drzava2);
        dodajGrad(grad3);
        Grad grad4 = new Grad("Beč", 1899055);
        Drzava dr = new Drzava("Austrija", grad4);
        grad4.setDrzava(dr);
        dodajGrad(grad4);
        dr.setGlavniGrad(grad4);
        dodajDrzavu(dr);
        Grad grad5 = new Grad("Graz", 280200);
        grad5.setDrzava(dr);
        dodajGrad(grad5);
    }
    public Connection getConn() {
        return conn;
    }
    Grad glavniGrad(String drzava) {
        for (Drzava x : drzave) {
            if (x.getNaziv().equals(drzava)) {
                return x.getGlavniGrad();
            }
        }
        return null;
    }

    void obrisiDrzavu(String drzava) {
        boolean b = false;
        int i= 0;
        int j=0;
        ArrayList<Grad> gradovi1 = new ArrayList<>();
        ArrayList<Drzava> drzave1 = new ArrayList<>();
        Drzava drzava2 = nadjiDrzavu(drzava);
        for (Grad g : gradovi) {
            if (g.getDrzava().getNaziv().equals(drzava)) {
                gradovi1.add(g);
            }
            i++;
        }
        for(Drzava x : drzave) {
            if(x.getNaziv().equals(drzava)) {
                drzave1.add(x);
                b = true;
            }
            j++;
        }
        gradovi.removeAll(gradovi1);
        drzave.removeAll(drzave1);
        if (!b) return;
        try {
            String sql = "DELETE FROM drzava WHERE naziv = ?";
            String sql2 = "DELETE FROM grad WHERE drzava = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            PreparedStatement statement1 = conn.prepareStatement(sql2);
            statement1.setInt(1, drzava2.getId());
            statement1.executeUpdate();
            statement.setString(1, drzava);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ArrayList<Grad> gradovi() {
        Collections.sort(gradovi, new Comparator<Grad>() {
            @Override
            public int compare(Grad o1, Grad o2) {
                if (o1.getBrojStanovnika() < o2.getBrojStanovnika()) return 1;
                else if (o1.getBrojStanovnika() > o2.getBrojStanovnika()) return -1;
                else return 0;
            }
        });
        return gradovi;
    }

    void dodajGrad(Grad grad) {
        String sql = "INSERT OR REPLACE INTO grad(naziv, broj_stanovnika, drzava) VALUES(?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, grad.getNaziv());
            statement.setInt(2, grad.getBrojStanovnika());
            statement.setInt(3, grad.getDrzava().getId());
            gradovi.add(grad);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void dodajDrzavu(Drzava drzava) {
        String sql = "INSERT OR REPLACE INTO drzava(naziv, glavni_grad) VALUES(?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, drzava.getNaziv());
            statement.setInt(2, drzava.getGlavniGrad().getId());
            drzave.add(drzava);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void izmijeniGrad(Grad grad) {
        String sql = "UPDATE grad SET naziv=?, broj_stanovnika=?, drzava=?";
        try {

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,grad.getNaziv());
            statement.setInt(2,grad.getBrojStanovnika());
            statement.setInt(3,grad.getDrzava().getId());
            statement.execute();
            for(Grad x : gradovi) {
                if(x.getNaziv().equals(grad.getNaziv())  || x.getBrojStanovnika() == grad.getBrojStanovnika() || x.getDrzava().equals(grad.getDrzava())) {
                    gradovi.remove(x);
                }
            }
            gradovi.add(grad);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    Drzava nadjiDrzavu(String drzava) {
        for (Drzava d: drzave
             ) {
            if(d.getNaziv().equals(drzava)) return d;

        }
        return null;
    }
}
