package Control.HTTP;

import Control.DAO.*;
import Control.GUI.CustomDialog;
import Control.GUI.ErrorDialog;
import javafx.collections.FXCollections;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class XMLHttpController {
    public static HttpResponse<String> sendRequest(String login, String password, HttpClient httpClient) throws ExecutionException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:64/data/")).header("login",login).header("password",password).build();
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> resp = response.get();
        return resp;
    }
    Pair<String, String> p;
    private static String decode(String login,String password){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < login.length(); i++) {
            stringBuilder.append((char)((int)login.charAt(i)+2));
        }
        stringBuilder.append("XX");
        for (int i = 0; i < password.length(); i++) {
            if(password.charAt(i)=='z') stringBuilder.append('2');
            else if(password.charAt(i)=='y') stringBuilder.append('1');
            else stringBuilder.append((char)((int)password.charAt(i)+2));
        }
        return stringBuilder.toString();
    }
    private final String xml;
    public XMLHttpController(String login,String password) throws ExecutionException, InterruptedException, IOException {
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(120)).build();
        HttpResponse<String> resp = sendRequest(login,password,httpClient);
        while(resp.body().equals("error_msg")){
            Dialog<String> d = ErrorDialog.getErrorDialog();
            d.showAndWait();
            Dialog<Pair<String,String>> dialog = CustomDialog.makeCustomDialog();
            Optional<Pair<String,String>> op = dialog.showAndWait();
            if(op.isPresent()) {
                p = op.get();
                resp = sendRequest(p.getKey(), p.getValue(), httpClient);
            }
            else{
                throw new IOException("NIE WPIASNO HASLA LUB LOGINU");
            }

        }
        this.xml = resp.body();
    }

    public List<Grade> getGrades() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        Node grades = document.getElementsByTagName("przedmioty_uczen").item(0);
        List<Grade> gradeList = new LinkedList<>();
        NodeList subjects = grades.getChildNodes();
        for(int i=0;i<subjects.getLength();i++){
            Node subject = subjects.item(i);
            Element subj = (Element) subject;
            String name = subj.getElementsByTagName("nazwa_przedmiotu").item(0).getTextContent();
            Node oceny = subj.getElementsByTagName("oceny").item(0);
            for(int j=0;j<oceny.getChildNodes().getLength();j++){
                Element oce = (Element) oceny.getChildNodes().item(j);
                NodeList oceAttrs = oce.getChildNodes();
                gradeList.add(new Grade(name,oceAttrs.item(0).getTextContent(),oceAttrs.item(1).getTextContent(),oceAttrs.item(2).getTextContent(),oceAttrs.item(3).getTextContent(),oceAttrs.item(4).getTextContent()));
            }
        }
        return gradeList;
    }

    public List<Subject> getSubjects() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        Node grades = document.getElementsByTagName("przedmioty_uczen").item(0);
        List<Subject> subjectList = new LinkedList<>();
        NodeList subjects = grades.getChildNodes();
        for(int i=0;i<subjects.getLength();i++){
            Node subject = subjects.item(i);
            Element subj = (Element) subject;
            String name = subj.getElementsByTagName("nazwa_przedmiotu").item(0).getTextContent();
            String proposed = subj.getElementsByTagName("proponowana").item(0).getTextContent();
            String final_ = subj.getElementsByTagName("koncowa").item(0).getTextContent();
            Node oceny = subj.getElementsByTagName("oceny").item(0);
            double all=0,count=0;
            Pattern match = Pattern.compile("\\d[+-]*");
            for(int j=0;j<oceny.getChildNodes().getLength();j++) {
                Element oce = (Element) oceny.getChildNodes().item(j);
                if(oce.getTagName().equals("ocena")) {
                    NodeList oceAttrs = oce.getChildNodes();
                    String stp = oceAttrs.item(0).getTextContent();
                    double waga = Double.parseDouble(oceAttrs.item(1).getTextContent().trim().replace(",", "."));
                    if(match.matcher(String.valueOf(stp)).matches()){
                        if(stp.length()==1) {
                            all += Double.parseDouble(String.valueOf(stp.charAt(0))) * waga;
                        }
                        else if (stp.charAt(1) == '+') {
                            all += Double.parseDouble(String.valueOf(stp.charAt(0))) * waga + 0.33 * waga;
                        } else if (stp.charAt(1) == '-') {
                            all += Double.parseDouble(String.valueOf(stp.charAt(0))) * waga - 0.33 * waga;
                        }
                        count+=waga;
                    }
                }
            }
            subjectList.add(new Subject(name,all/count,proposed,final_));
        }
        return subjectList;
    }

    public List<List<String>> getClassGrades() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        NodeList grades = document.getElementsByTagName("przedmioty_klasa").item(0).getChildNodes();
        List<List<String>> classList = new LinkedList<>();
        for(int i=0;i<grades.getLength();i++){
            Node grade = grades.item(i);
            NodeList attrs = grade.getChildNodes();
            if((attrs.item(0)!=null)) {
                List<String> subject = List.of(attrs.item(0).getTextContent(), attrs.item(1).getTextContent(), attrs.item(2).getTextContent(), attrs.item(3).getTextContent(), attrs.item(4).getTextContent(), attrs.item(5).getTextContent(), attrs.item(6).getTextContent());
                classList.add(subject);
            }
        }
        return classList;
    }

    public List<HourRow> getLessons() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        NodeList rows = document.getElementsByTagName("plan_lekcji").item(0).getChildNodes();
        List<HourRow> hourRows = new LinkedList<>();
        for(int i=0;i<rows.getLength();i++){
            NodeList row = rows.item(i).getChildNodes();
            hourRows.add(new HourRow(row.item(0).getTextContent(),row.item(1).getTextContent(),row.item(2).getTextContent(),row.item(3).getTextContent(),row.item(4).getTextContent(),row.item(5).getTextContent(),row.item(6).getTextContent()));
        }
        return hourRows;
    }

    public List<Day> getExams() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        NodeList days = document.getElementsByTagName("dni").item(0).getChildNodes();
        List<Day> dayList = new LinkedList<>();
        for(int i=0;i<days.getLength();i++){
            NodeList day = days.item(i).getChildNodes();
            List<String> exams = new LinkedList<>();
            for(int j=0;j<day.item(1).getChildNodes().getLength();j++){
                exams.add(day.item(1).getChildNodes().item(j).getTextContent());
            }
            dayList.add(new Day(day.item(0).getTextContent(),exams));
        }
        return dayList;
    }
    public List<Message> getMessages() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        NodeList messages = document.getElementsByTagName("messages").item(0).getChildNodes();
        List<Message> messageList = new LinkedList<>();
        for(int i=0;i<messages.getLength();i++){
            NodeList message = messages.item(i).getChildNodes();
            messageList.add(new Message(message.item(0).getTextContent(),message.item(1).getTextContent(),message.item(2).getTextContent(),message.item(3).getTextContent()));
        }
        return messageList;
    }
}
