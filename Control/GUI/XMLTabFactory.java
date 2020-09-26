package Control.GUI;

import Control.DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

@ApiStatus.Experimental
public class XMLTabFactory {
    private static Subject getOverallForSubjects(@NotNull List<Subject> subjects){
        Map<String,Double> map = new TreeMap<>();
        map.put("niedostateczny",1.0);
        map.put("dopuszczający",2.0);
        map.put("dostateczny",3.0);
        map.put("dobry",4.0);
        map.put("bardzo dobry",5.0);
        map.put("celujący",6.0);
        double allP=0,allF=0,allA=0;
        double cP=0,cF=0,cA=0;
        for(Subject subject : subjects){
            if(map.containsKey(subject.getProposed())){allP+=map.get(subject.getProposed());cP++;}
            if(map.containsKey(subject.getFinal_())){allF+=map.get(subject.getFinal_());cF++;}
            if(!Double.isNaN(subject.getAvg())){allA+=subject.getAvg();cA++;}

        }
        return new Subject("Overall",allA/cA, Double.toString(allP / cP),Double.toString(allF/cF));
    }
    public static @NotNull Tab getClassTab(@NotNull List<List<String>> klasa){
        List<PieChart> przedmioty = new LinkedList<>();
        List<ObservableList<PieChart.Data>> feed = new LinkedList<>();
        for (List<String> kl : klasa) {
            feed.add(FXCollections.observableArrayList(
                    new PieChart.Data("1\n" + kl.get(0) + " ocen", Integer.parseInt(kl.get(0))),
                    new PieChart.Data("2\n" + kl.get(1) + " ocen", Integer.parseInt(kl.get(1))),
                    new PieChart.Data("3\n" + kl.get(2) + " ocen", Integer.parseInt(kl.get(2))),
                    new PieChart.Data("4\n" + kl.get(3) + " ocen", Integer.parseInt(kl.get(3))),
                    new PieChart.Data("5\n" + kl.get(4) + " ocen", Integer.parseInt(kl.get(4))),
                    new PieChart.Data("6\n" + kl.get(5) + " ocen", Integer.parseInt(kl.get(5)))));
        }
        for(ObservableList<PieChart.Data> data :feed) {
            PieChart pc = new PieChart();
            pc.setData(data);
            pc.setClockwise(false);
            pc.setStartAngle(90);
            przedmioty.add(pc);
        }
        Tab kls = new Tab();
        kls.setClosable(false);
        kls.setText("Klasa");
        TabPane tp = new TabPane();
        tp.setPrefSize(640,480);
        kls.setContent(tp);
        for(int i=0;i<przedmioty.size();i++){
            Tab tab = new Tab();
            tab.setContent(przedmioty.get(i));
            tab.setText(klasa.get(i).get(6));
            tab.setClosable(false);
            tp.getTabs().add(tab);
        }
        return kls;
    }
    public static @NotNull Tab getGradesTab(@NotNull List<Grade> grades){
        Tab oce = new Tab();
        oce.setText("Oceny");
        oce.setClosable(false);
        ObservableList<Grade> gradeObservableList = FXCollections.observableArrayList(grades);
        TableView<Grade> table = new TableView<>();
        TableColumn<Grade,String> k1 = new TableColumn<>("Przedmiot");
        TableColumn<Grade,String> k2 = new TableColumn<>("Ocena");
        TableColumn<Grade,String> k3 = new TableColumn<>("Waga");
        TableColumn<Grade,String> k4 = new TableColumn<>("Nauczyciel");
        TableColumn<Grade,String> k5 = new TableColumn<>("Data");
        TableColumn<Grade,String> k6 = new TableColumn<>("Opis");
        k1.setCellValueFactory(new PropertyValueFactory<>("subject"));
        k2.setCellValueFactory(new PropertyValueFactory<>("grade"));
        k3.setCellValueFactory(new PropertyValueFactory<>("weight"));
        k4.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        k5.setCellValueFactory(new PropertyValueFactory<>("date"));
        k6.setCellValueFactory(new PropertyValueFactory<>("description"));
        k1.setPrefWidth(175);
        k2.setPrefWidth(50);
        k3.setPrefWidth(50);
        k4.setPrefWidth(206);
        k5.setPrefWidth(206);
        k6.setPrefWidth(288);
        k1.setSortable(false);
        k2.setSortable(false);
        k3.setSortable(false);
        k4.setSortable(false);
        k5.setSortable(false);
        k6.setSortable(false);
        //noinspection unchecked
        table.getColumns().addAll(k1,k2,k3,k4,k5,k6);
        table.setItems(gradeObservableList);
        table.setEditable(false);
        oce.setContent(table);
        return oce;
    }
    public static @NotNull Tab getHoursTab(@NotNull List<HourRow> hourRows){
        Tab pl = new Tab();
        pl.setClosable(false);
        pl.setText("Plan lekcji");
        ObservableList<HourRow> rowObservableList = FXCollections.observableList(hourRows);
        TableView<HourRow> table = new TableView<>();
        TableColumn<HourRow,String> k1 = new TableColumn<>("Lekcja");
        TableColumn<HourRow,String> k2 = new TableColumn<>("Czas Trwania");
        TableColumn<HourRow,String> k3 = new TableColumn<>("Poniedziałek");
        TableColumn<HourRow,String> k4 = new TableColumn<>("Wtorek");
        TableColumn<HourRow,String> k5 = new TableColumn<>("Środa");
        TableColumn<HourRow,String> k6 = new TableColumn<>("Czwartek");
        TableColumn<HourRow,String> k7 = new TableColumn<>("Piątek");
        k1.setCellValueFactory(new PropertyValueFactory<>("lesson"));
        k2.setCellValueFactory(new PropertyValueFactory<>("duration"));
        k3.setCellValueFactory(new PropertyValueFactory<>("mon"));
        k4.setCellValueFactory(new PropertyValueFactory<>("tue"));
        k5.setCellValueFactory(new PropertyValueFactory<>("wen"));
        k6.setCellValueFactory(new PropertyValueFactory<>("thu"));
        k7.setCellValueFactory(new PropertyValueFactory<>("fri"));
        k1.setPrefWidth(50);
        k2.setPrefWidth(50);
        k3.setPrefWidth(175);
        k4.setPrefWidth(175);
        k5.setPrefWidth(175);
        k6.setPrefWidth(175);
        k7.setPrefWidth(175);
        k1.setSortable(false);
        k2.setSortable(false);
        k3.setSortable(false);
        k4.setSortable(false);
        k5.setSortable(false);
        k6.setSortable(false);
        k7.setSortable(false);
        //noinspection unchecked
        table.getColumns().addAll(k1,k2,k3,k4,k5,k6,k7);
        table.setItems(rowObservableList);
        table.setColumnResizePolicy((a)-> Boolean.valueOf(""));
        pl.setContent(table);
        return pl;
    }
    public static @NotNull Tab getSubjectTab(@NotNull List<Subject> subjects){
        Tab przed = new Tab();
        przed.setText("Przedmioty");
        przed.setClosable(false);
        subjects.add(getOverallForSubjects(subjects));
        TableView<Subject> table = new TableView<>();
        TableColumn<Subject,String> k1 = new TableColumn<>("Przedmiot");
        TableColumn<Subject,Double> k2 = new TableColumn<>("Średnia");
        TableColumn<Subject,String> k3 = new TableColumn<>("Ocena proponowana");
        TableColumn<Subject,String> k4 = new TableColumn<>("Ocena końcowa");
        k1.setSortable(false);
        k2.setSortable(false);
        k3.setSortable(false);
        k4.setSortable(false);
        k1.setPrefWidth(244);
        k2.setPrefWidth(244);
        k3.setPrefWidth(244);
        k4.setPrefWidth(243);
        k1.setCellValueFactory(new PropertyValueFactory<>("name"));
        k2.setCellValueFactory(new PropertyValueFactory<>("avg"));
        k3.setCellValueFactory(new PropertyValueFactory<>("proposed"));
        k4.setCellValueFactory(new PropertyValueFactory<>("final_"));
        //noinspection unchecked
        table.getColumns().addAll(k1,k2,k3,k4);
        ObservableList<Subject> subjectObservableList = FXCollections.observableList(subjects);
        table.setItems(subjectObservableList);
        przed.setContent(table);
        return przed;
    }
    public static @NotNull Tab getExamsTab(@NotNull List<Day> days){
        Tab exam = new Tab();
        exam.setClosable(false);
        exam.setText("Sprawdziany");
        TableView<Day> table = new TableView<>();
        TableColumn<Day,String> k1 = new TableColumn<>("Data");
        TableColumn<Day,List<String>> k2 = new TableColumn<>("Sprawdziany");
        k1.setSortable(false);
        k2.setSortable(false);
        k1.setPrefWidth(100);
        k2.setPrefWidth(875);
        k1.setCellValueFactory(new PropertyValueFactory<>("date"));
        k2.setCellValueFactory(new PropertyValueFactory<>("exams"));
        //noinspection unchecked
        table.getColumns().addAll(k1,k2);
        table.setItems(FXCollections.observableList(days));
        exam.setContent(table);
        return exam;
    }
    public static Tab getMessagesTab(@NotNull List<Message> messages) throws NoSuchFieldException, IllegalAccessException {
        Tab t = new Tab();
        t.setClosable(false);
        t.setText("Wiadomości");
        ScrollPane scrollWrapper = new ScrollPane();
        Accordion messageBox = new Accordion();
        messageBox.setMaxWidth(540);
        scrollWrapper.setContent(messageBox);
        t.setContent(scrollWrapper);
        for(Message message :messages){

            TitledPane tp = new TitledPane();
            tp.setText(message.getFrom()+"\t\t\t\t"+message.getTopic()+"\t\t\t\t"+message.getDate());
            VBox labels = new VBox();
            for(String text : message.getText().split("\\\\n")) {
                Text txt = new Text();
                txt.setWrappingWidth(540);
                txt.setTextAlignment(TextAlignment.LEFT);
                txt.setText(text);
                labels.getChildren().add(txt);
            }
            tp.setContent(labels);
            messageBox.getPanes().add(tp);
        }
        return t;
    }

    public static @NotNull Tab getInfoTab(){
        Tab info = new Tab();
        VBox root = new VBox();
        root.getChildren().addAll(
                new Label("E-Dziennik wersja 1.0"),
                new Label("Autor: Radosław Pikul"),
                new Label("Aplikacja działa na zasadzie scrapera,\nwięc może działać wolno, jednak taka jest\nspecyfika technologii")
        );
        info.setText("Informacje");
        info.setClosable(false);
        info.setContent(root);
        root.getStyleClass().add("vbox");
        return info;
    }
}
