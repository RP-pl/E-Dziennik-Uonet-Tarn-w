package com.example.edz_android_gui.Parsers;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.edz_android_gui.DAO.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class XMLParser {
    private String xml;
    public XMLParser(String xml){
        this.xml = xml;
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

    @RequiresApi(api = Build.VERSION_CODES.R)
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
                List<String> subject = new LinkedList<>();
                subject.add(attrs.item(0).getTextContent());
                subject.add(attrs.item(1).getTextContent());
                subject.add(attrs.item(2).getTextContent());
                subject.add(attrs.item(3).getTextContent());
                subject.add(attrs.item(4).getTextContent());
                subject.add(attrs.item(5).getTextContent());
                subject.add(attrs.item(6).getTextContent());
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
        hourRows.add(new HourRow("Lesson","Duration","Mon","Tue","Wen","Thu","Fri"));
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
