package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static javafx.application.Application.launch;
import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {

    public static String ispisiGradove() {
        List<Grad> gradovi = GeografijaDAO.getInstance().gradovi();
        String s="";
        for (Grad g: gradovi
        ) {
            s+=g.getNaziv()+" ("+g.getDrzava().getNaziv()+") - "+ g.getBrojStanovnika()+"\n";
        }
        return s;
    }
    public static void glavniGrad() {
        Scanner ulaz = new Scanner(System.in);
        String s = ulaz.nextLine();
        Grad grad = GeografijaDAO.getInstance().glavniGrad(s);
        System.out.println("Glavni grad države " + grad.getDrzava().getNaziv()+" je " + grad.getNaziv());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("dao.fxml"));
        primaryStage.setTitle("Geografija");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }
}