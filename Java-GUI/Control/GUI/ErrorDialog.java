package Control.GUI;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ErrorDialog {
    public static Dialog<String> getErrorDialog() throws FileNotFoundException {
        Dialog<String> dialog = new Dialog<>();
        Stage s = (Stage) dialog.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image(new FileInputStream(new File("logo4EdzIcon.png"))));
        dialog.setTitle("Błąd");
        dialog.setHeaderText("Login lub hasło są niepoprawne");
        ButtonType bt = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
        dialog.getDialogPane().getButtonTypes().add(bt);
        dialog.setResultConverter((buttonType)-> {
            if (buttonType == bt) {
                return null;
            }
                    return null;
                }
        );
    return dialog;
    }
}
