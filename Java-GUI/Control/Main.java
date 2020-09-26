package Control;

import Control.GUI.XMLTabFactory;
import Control.HTTP.HTTPController;
import Control.Threading.TabService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import Control.GUI.CustomDialog;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import Control.HTTP.XMLHttpController;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Optional;
import java.util.concurrent.*;

public class Main extends Application {
    Pair<String, String> p;

    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException, ParserConfigurationException, SAXException, NoSuchFieldException, IllegalAccessException {

            BorderPane root = new BorderPane();
            Platform.runLater(()->{
                ProgressIndicator p = new ProgressIndicator();
                p.getStylesheets().add("stylesheet.css");
                p.getStyleClass().add("progress-indicator");
                root.setCenter(p);
            });
            root.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(new File("logo4EdzIcon.png"))), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
            stage.setScene(new Scene(root,975,540));
            stage.show();
            stage.setTitle("E-Dziennik");
            stage.getIcons().add(new Image(new FileInputStream(new File("logo4EdzIcon.png"))));
            Dialog<Pair<String,String>> dialog = CustomDialog.makeCustomDialog();
            dialog.initOwner(stage);
            Optional<Pair<String,String>> op = dialog.showAndWait();
            if(op.isPresent()) {
                p = op.get();
            }
            else{
                throw new IOException("NIE WPIASNO HASLA LUB LOGINU");
            }
        TabService service = new TabService(stage,p);
            service.setOnSucceeded((e)->{
                stage.setScene(service.getValue());
           });
            service.start();
        }


    public static void main(String[] args){
        Application.launch(args);
    }
}
