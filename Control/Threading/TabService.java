package Control.Threading;

import Control.GUI.XMLTabFactory;
import Control.HTTP.XMLHttpController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.xml.sax.SAXException;

public class TabService  extends Service<Scene> {
    private final Pair<String, String> p;
    private final Stage stage;

    public TabService(Stage stage, Pair<String,String> p ){
        this.p = p;
        this.stage = stage;
    }

    @Override
    protected Task<Scene> createTask() {
        return new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {
                try{
                    TabPane menu = new TabPane();
                    XMLHttpController httpController = new XMLHttpController(p.getKey(),p.getValue());
                    //OCENY
                    menu.getTabs().add(XMLTabFactory.getGradesTab(httpController.getGrades()));
                    //KLASA
                    //menu.getTabs().add(XMLTabFactory.getClassTab(httpController.getClassGrades()));
                    //PLAN
                    menu.getTabs().add(XMLTabFactory.getHoursTab(httpController.getLessons()));
                    //PRZEDMIOTY
                    menu.getTabs().add(XMLTabFactory.getSubjectTab(httpController.getSubjects()));
                    //SPRAWDZIANY
                    menu.getTabs().add(XMLTabFactory.getExamsTab(httpController.getExams()));
                    //WIADOMOŚCI
                    menu.getTabs().add(XMLTabFactory.getMessagesTab(httpController.getMessages()));
                    //INFORMACJE
                    menu.getTabs().add(XMLTabFactory.getInfoTab());
                    Scene sc = new Scene(menu,975,540);
                    sc.getStylesheets().add("stylesheet.css");
                    menu.getStyleClass().add("tab-pane");
                    return sc;
                } catch (SAXException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
