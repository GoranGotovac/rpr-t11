package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;

import java.util.ResourceBundle;

public class Controller {
    @FXML
    public TextField upisDrzava;
    @FXML
    public TextField upisGrad;
    @FXML public Label text1;
    @FXML public Label text2;
    public Menu file;
    public Menu help;
    public Menu view;
    public Menu jezik;
    public Button glavniGrad;
    public Button izbrsiDrzavu;
    public Button ispisiGradove;
    public Button ispisiDrzavu;
    public Button brojStanovnika;
    public Button izvjestaj;
    public Label grad;
    public Label drzava;
    public MenuItem bosanski, engleski, njemacki, francuski;
    public void glavniGrad(ActionEvent actionEvent) {
        Grad g = GeografijaDAO.getInstance().glavniGrad(upisDrzava.getText());
        System.out.println("Glavni Grad je: "+g.getNaziv());
        text1.setText(g.getNaziv());
    }

    public void izbrsi(ActionEvent actionEvent) {
        GeografijaDAO.getInstance().obrisiDrzavu(upisDrzava.getText());
        text1.setText("Država obrisana");
    }

    public void ispisi(ActionEvent actionEvent) {
        glavniGrad(actionEvent);
        System.out.print("On ima " + GeografijaDAO.getInstance().glavniGrad(upisDrzava.getText()).getBrojStanovnika()+ " stanovnika");
    }

    public void ispisiDrzavu(ActionEvent actionEvent) {
        System.out.println("On se nalazi u " + GeografijaDAO.getInstance().nadjiDrzavu2(upisGrad.getText()).getNaziv());
        text2.setText(GeografijaDAO.getInstance().nadjiDrzavu2(upisGrad.getText()).getNaziv());
    }

    public void brojStanovnika(ActionEvent actionEvent) {
        System.out.println(upisGrad.getText()+" ima " + GeografijaDAO.getInstance().dajGrad(upisGrad.getText()));
        text2.setText(""+GeografijaDAO.getInstance().dajGrad(upisGrad.getText()).getBrojStanovnika());
    }

    public void izvjestaj(ActionEvent actionEvent) {
        try {
            new GradoviReport().showReport(GeografijaDAO.getInstance().getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    public void njemacki(ActionEvent actionEvent) {
        String property = "aa";
        MenuItem m = (MenuItem)actionEvent.getSource();
        switch (m.getId()) {
            case "bosanski":
                property = "Translation_bs";
                break;
            case "njemacki":
                property = "Translation_de";
                break;
            case "francuski":
                property = "Translation_fr";
                break;
            case "engleski":
                property = "Translation_en_US";
                break;

        }
        file.setText(ResourceBundle.getBundle(property).getString("file"));
        help.setText(ResourceBundle.getBundle(property).getString("help"));
        view.setText(ResourceBundle.getBundle(property).getString("view"));
        jezik.setText(ResourceBundle.getBundle(property).getString("Language"));
        glavniGrad.setText(ResourceBundle.getBundle(property).getString("glavnigrad"));
        izbrsiDrzavu.setText(ResourceBundle.getBundle(property).getString("izbrsidrzavu"));
        ispisiGradove.setText(ResourceBundle.getBundle(property).getString("ispisigradove"));
        ispisiDrzavu.setText(ResourceBundle.getBundle(property).getString("ispisidrzavu"));
        brojStanovnika.setText(ResourceBundle.getBundle(property).getString("brojstanovnika"));
        izvjestaj.setText(ResourceBundle.getBundle(property).getString("izvjestaj"));
        grad.setText(ResourceBundle.getBundle(property).getString("izaberitegrad"));
        drzava.setText(ResourceBundle.getBundle(property).getString("Izaberitedrzavu"));
        bosanski.setText(ResourceBundle.getBundle(property).getString("bosanski"));
        engleski.setText(ResourceBundle.getBundle(property).getString("engleski"));
        francuski.setText(ResourceBundle.getBundle(property).getString("francuski"));
        njemacki.setText(ResourceBundle.getBundle(property).getString("njemacki"));
    }

}
