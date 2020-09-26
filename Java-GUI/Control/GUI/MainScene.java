package Control.GUI;

import Control.HTTP.HTTPController;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.util.Pair;
import java.io.IOException;
import java.util.concurrent.Callable;

public class MainScene implements Callable<Scene> {
    HTTPController httpController;
    public MainScene(Pair<String,String> p) throws IOException, InterruptedException {
        this.httpController = new HTTPController(p.getKey(),p.getValue());
    }
    public Scene call() throws InterruptedException, IllegalAccessException, IOException {
        this.httpController.getToken();
        TabPane menu = new TabPane();
        //OCENY
        menu.getTabs().add(TabFactory.getGradesTab(this.httpController.oceny()));
        //KLASA
        menu.getTabs().add(TabFactory.getClassTab(this.httpController.klasa()));
        //PLAN
        menu.getTabs().add(TabFactory.getHoursTab(this.httpController.plan()));
        //PRZEDMIOTY
        menu.getTabs().add(TabFactory.getSubjectTab(this.httpController.oceny()));
        //SPRAWDZIANY
        menu.getTabs().add(TabFactory.getExamsTab(this.httpController.testy()));
        //INFORMACJE
        menu.getTabs().add(TabFactory.getInfoTab());


        this.httpController.close();

        return new Scene(menu);
    }


}
