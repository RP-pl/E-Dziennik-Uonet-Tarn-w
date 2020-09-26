package Control.GUI;

import Control.DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.regex.Pattern;

@Deprecated
public class TabFactory {

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

    public static @NotNull Tab getClassTab(@NotNull List<String[]> klasa){
        List<PieChart> przedmioty = new LinkedList<>();
        List<ObservableList<PieChart.Data>> feed = new LinkedList<>();
        for (String[] kl : klasa) {
            feed.add(FXCollections.observableArrayList(
                    new PieChart.Data("1\n" + kl[0] + " ocen", Integer.parseInt(kl[0])),
                    new PieChart.Data("2\n" + kl[1] + " ocen", Integer.parseInt(kl[1])),
                    new PieChart.Data("3\n" + kl[2] + " ocen", Integer.parseInt(kl[2])),
                    new PieChart.Data("4\n" + kl[3] + " ocen", Integer.parseInt(kl[3])),
                    new PieChart.Data("5\n" + kl[4] + " ocen", Integer.parseInt(kl[4])),
                    new PieChart.Data("6\n" + kl[5] + " ocen", Integer.parseInt(kl[5]))));
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
        tp.getStyleClass().add("table-view");
        tp.setPrefSize(640,480);
        kls.setContent(tp);
        for(int i=0;i<przedmioty.size();i++){
            Tab tab = new Tab();
            tab.setContent(przedmioty.get(i));
            tab.setText(klasa.get(i)[6]);
            tab.setClosable(false);
            tp.getTabs().add(tab);
        }
        return kls;
    }
    public static @NotNull Tab getGradesTab(@NotNull String oceny){
        List<Grade> grades = new LinkedList<>();
        Tab oce = new Tab();
        oce.setText("Oceny");
        oce.setClosable(false);
        String[] splittedPrzedmiot = oceny.split("\\|\\|");
        for(int i=0;i<splittedPrzedmiot.length;i+=4){
            for(String ocena : splittedPrzedmiot[i+1].split("\n")){
                String[] grade = ocena.split("\t");
                if(grade.length==5) {
                    grades.add(new Grade(splittedPrzedmiot[i], grade[0], grade[1].split(":")[1], grade[2].split(":")[1], grade[3].split(":")[1], grade[4].split(":")[1]));

                }
            }
        }
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
        table.getStyleClass().add("table-view");
        oce.setContent(table);
        return oce;
    }
    public static @NotNull Tab getHoursTab(@NotNull List<String> plan){
        Tab pl = new Tab();
        pl.setClosable(false);
        pl.setText("Plan lekcji");
        List<HourRow> hourRows = new LinkedList<>();
        for(String row : plan){
            String[] strings = row.split("\t");
            hourRows.add(new HourRow(strings[0].trim(),strings[1].trim(),strings[2].trim(),strings[3].trim(),strings[4].trim(),strings[5].trim(),strings[6].trim()));
        }
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
        table.getStyleClass().add("table-view");
        table.setColumnResizePolicy((a)-> Boolean.valueOf(""));
        pl.setContent(table);
        return pl;
    }
    public static @NotNull Tab getSubjectTab(@NotNull String oceny){
        List<Subject> subjects = new LinkedList<>();
        Tab przed = new Tab();
        przed.setText("Przedmioty");
        przed.setClosable(false);
        Pattern pattern =Pattern.compile("\\d[+-]*");
        String[] prz = oceny.split("\\|\\|");
        for(int i=0;i<prz.length;i+=4){
            double il = 0;
            double all= 0;
            for(String oce : prz[i+1].split("\n")){
                String[] oc = oce.split("\t");
                if(oc.length==5){
                double waga = Double.parseDouble(String.valueOf(oc[1].split(" ")[1].charAt(0)));
                if(pattern.matcher(oc[0]).matches()){
                    if(oc[0].length()==1){
                        il += Double.parseDouble(String.valueOf(oc[0].charAt(0))) * waga;
                        all += waga;
                    }
                    else if(oc[0].charAt(1)=='-') {
                        il += Double.parseDouble(String.valueOf(oc[0].charAt(0))) * waga -0.33*waga;
                        all += waga;
                    }
                    else if(oc[0].charAt(1)=='+') {
                        il += Double.parseDouble(String.valueOf(oc[0].charAt(0))) * waga +0.33*waga;
                        all += waga;
                    }
                }
            }
            }
            subjects.add(new Subject(prz[i],il/all,prz[i+2],prz[i+3]));
        }
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
        k4.setMinWidth(243);
        k1.setCellValueFactory(new PropertyValueFactory<>("name"));
        k2.setCellValueFactory(new PropertyValueFactory<>("avg"));
        k3.setCellValueFactory(new PropertyValueFactory<>("proposed"));
        k4.setCellValueFactory(new PropertyValueFactory<>("final_"));
        table.getColumns().clear();
        //noinspection unchecked
        table.getColumns().addAll(k1,k2,k3,k4);
        ObservableList<Subject> subjectObservableList = FXCollections.observableList(subjects);
        table.setItems(subjectObservableList);
        table.setColumnResizePolicy((a)-> Boolean.valueOf(""));
        table.getStyleClass().add("table-view");
        przed.setContent(table);
        return przed;
    }
    public static @NotNull Tab getInfoTab(){
        Tab info = new Tab();
        VBox root = new VBox();
        root.getChildren().addAll(
                new Label("E-Dziennik wersja 0.3 beta"),
                new Label("Autor: Radosław Pikul"),
                new Label("Aplikacja działa na zasadzie scrapera,\nwięc może działać wolno, jednak taka jest\nspecyfika technologii")
        );
        info.setText("Informacje");
        info.setClosable(false);
        info.setContent(root);
        return info;
    }
    public static @NotNull Tab getExamsTab(@NotNull List<List<String>> exams){
        Tab exam = new Tab();
        exam.setClosable(false);
        exam.setText("Sprawdziany");
        List<Day> days = new LinkedList<>();
        for (List<String> strings : exams) {
            String date = strings.get(0);
            List<String> spraw = new LinkedList<>();
            for (int j = 1; j < strings.size(); j++) {
                spraw.add(strings.get(j));
            }
            days.add(new Day(date, spraw));
        }
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
}
