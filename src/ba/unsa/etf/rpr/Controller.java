package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.management.StringValueExp;

public class Controller {
    @FXML
    public TextField drzava;
    @FXML
    public TextField grad;
    @FXML public Label text1;
    @FXML public Label text2;

    public void glavniGrad(ActionEvent actionEvent) {
        Grad g = GeografijaDAO.getInstance().glavniGrad(drzava.getText());
        System.out.println("Glavni grad je: "+g.getNaziv());
        text1.setText(g.getNaziv());
    }

    public void izbrsi(ActionEvent actionEvent) {
        GeografijaDAO.getInstance().obrisiDrzavu(drzava.getText());
        text1.setText("Država obrisana");
    }

    public void ispisi(ActionEvent actionEvent) {
        glavniGrad(actionEvent);
        System.out.print("On ima " + GeografijaDAO.getInstance().glavniGrad(drzava.getText()).getBrojStanovnika()+ " stanovnika");
    }

    public void ispisiDrzavu(ActionEvent actionEvent) {
        System.out.println("On se nalazi u " + GeografijaDAO.getInstance().nadjiDrzavu2(grad.getText()).getNaziv());
        text2.setText(GeografijaDAO.getInstance().nadjiDrzavu2(grad.getText()).getNaziv());
    }

    public void brojStanovnika(ActionEvent actionEvent) {
        System.out.println(grad.getText()+" ima " + GeografijaDAO.getInstance().dajGrad(grad.getText()));
        text2.setText(""+GeografijaDAO.getInstance().dajGrad(grad.getText()).getBrojStanovnika());
    }
}
