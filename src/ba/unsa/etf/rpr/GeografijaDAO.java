package ba.unsa.etf.rpr;

import java.security.cert.CollectionCertStoreParameters;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    private static void initialize() {
        instance = new GeografijaDAO();
    }

    public GeografijaDAO() {
        try {
            String url = "jdbc:sqlite:baza.db";
            conn = DriverManager.getConnection(url);
            novaTabela();
            upisi();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    private void upisi() {
        Grad grad1 = new Grad("Pariz", 2206488);
        Drzava drzava1 = new Drzava("Francuska", grad1);
        grad1.setDrzava(drzava1);
        gradovi.add(grad1);
        drzave.add(drzava1);
        Grad grad2 = new Grad("London", 8825000);
        Drzava drzava2 = new Drzava("Velika Britanija", grad2);
        grad2.setDrzava(drzava2);
        gradovi.add(grad2);
        drzave.add(drzava2);
        Grad grad3 = new Grad("Manchester", 545500);
        grad3.setDrzava(drzava2);
        gradovi.add(grad3);
        Grad grad4 = new Grad("Beč", 1899055);
        Drzava dr = new Drzava("Austrija", grad4);
        grad4.setDrzava(dr);
        gradovi.add(grad4);
        drzave.add(dr);
        Grad grad5 = new Grad("Graz", 280200);
        grad5.setDrzava(dr);
        gradovi.add(grad5);
        drzava1.setGlavniGrad(grad1);
        drzava2.setGlavniGrad(grad2);
        dr.setGlavniGrad(grad4);
    }

    Grad glavniGrad(Drzava drzava) {
        for (Drzava x : drzave) {
            if (x.getGlavniGrad().getNaziv().equals(drzava.getGlavniGrad().getNaziv())) {
                return x.getGlavniGrad();
            }
        }
        return null;
    }

    void obrisiDrzavu(String drzava) {
        boolean b = false;
        Drzava drzava1 = new Drzava();
        for (Grad g : gradovi) {
            if (g.getDrzava().getNaziv().equals(drzava)) {
                gradovi.remove(g);
            }
        }
        for (Drzava g : drzave) {
            if (g.getNaziv().equals(drzava)) {
                drzave.remove(g);
                b = true;
                drzava1 = g;
            }
        }
        if (!b) return;
        try {
            String sql = "DELETE FROM drzava WHERE naziv = ?";
            String sql2 = "DELETE FROM grad WHERE drzava = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            PreparedStatement statement1 = conn.prepareStatement(sql2);
            statement1.setInt(1, drzava1.getId());
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
                if(x.getNaziv().equals(grad.getNaziv()) || x.getBrojStanovnika() == grad.getBrojStanovnika() || x.getDrzava().equals(grad.getDrzava())) {
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
