package Control.GUI;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomDialog {
    public static Dialog<Pair<String,String>> makeCustomDialog() throws FileNotFoundException {
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        Stage s = (Stage) dialog.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image(new FileInputStream(new File("logo4EdzIcon.png"))));
        dialog.setTitle("Logowanie");
        dialog.setHeaderText("Podaj login i haslo do e-dziennika");
        ButtonType bt = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
        dialog.getDialogPane().getButtonTypes().add(bt);
        VBox dialogBox = new VBox();
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();
        dialogBox.getChildren().addAll(new Label("LOGIN:"),loginField,new Label("HASLO:"),passwordField);
        dialog.getDialogPane().setContent(dialogBox);
        dialog.getDialogPane().setGraphic(new ImageView(new Image(new FileInputStream(new File("logo4EdzIcon.png")),64,64,false,true)));
        dialog.setResultConverter((buttonType)-> {
            if (buttonType == bt) {
                return new Pair<>(loginField.getText(), passwordField.getText());
            }
            else {return new Pair<>("ERROR","ERROR");}
        });
        return dialog;
    }
}
