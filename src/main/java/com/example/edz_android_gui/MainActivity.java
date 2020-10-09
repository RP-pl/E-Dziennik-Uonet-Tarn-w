package com.example.edz_android_gui;
import android.app.Dialog;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.edz_android_gui.Fragments.*;
import com.example.edz_android_gui.Parsers.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ExamsFragment examsFragment;
    GradesFragment gradesFragment;
    SubjectFragment subjectFragment;
    String xml;
    private LessonsFragment lessonFragment;
    private MessagesFragment msgFragment;
    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.dummy_frag);
        Dialog d = new Dialog(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(d.getWindow().getAttributes());
        params.width = 300;
        params.height = 150;
        d.getWindow().setAttributes(params);
        d.setContentView(R.layout.logging_dialog);
        d.setTitle("EDZ");
        TextView log = d.findViewById(R.id.log_field);
        TextView pass = d.findViewById(R.id.pass_field);
        Button submit = d.findViewById(R.id.submit_button);
        submit.setOnClickListener((v)->{
            this.login =  log.getText().toString();
            this.password = (String) pass.getText().toString();
            d.dismiss();
        });
        d.show();
        d.setOnCancelListener((dialogInterface)->{
            d.show();
        });
        d.setOnDismissListener((v)->{
        RequestQueue r = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://DESKTOP-I5R31U5:64/data/", response -> {
            this.xml = response;
            XMLParser xmlParser = new XMLParser(xml);
            try {
                this.gradesFragment = new GradesFragment(xmlParser.getGrades());
                this.subjectFragment = new SubjectFragment(xmlParser.getSubjects());
                this.examsFragment = new ExamsFragment(xmlParser.getExams());
                this.lessonFragment = new LessonsFragment(xmlParser.getLessons());
                this.msgFragment = new MessagesFragment(xmlParser.getMessages());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            setContentView(R.layout.activity_main);
            Button grades = findViewById(R.id.grades_button);

            grades.setOnClickListener((view)->{
                FragmentTransaction tr = fragmentManager.beginTransaction();
                tr.replace(R.id.fragment,gradesFragment);
                tr.addToBackStack(null);
                tr.commit();
            });

            Button subjects = findViewById(R.id.subjects_button);

            subjects.setOnClickListener((view)->{
                FragmentTransaction tr = fragmentManager.beginTransaction();
                tr.replace(R.id.fragment,subjectFragment);
                tr.commit();
            });

            Button exams = findViewById(R.id.exams_button);
            exams.setOnClickListener((view)->{
                FragmentTransaction tr = fragmentManager.beginTransaction();
                tr.replace(R.id.fragment,examsFragment);
                tr.commit();
            });

            Button lessons = findViewById(R.id.lessons_button);
            lessons.setOnClickListener((view)->{
                FragmentTransaction t = fragmentManager.beginTransaction();
                t.replace(R.id.fragment,lessonFragment);
                t.commit();
            });

            Button msgs = findViewById(R.id.msg_button);
            msgs.setOnClickListener((vs) -> {
                FragmentTransaction t = fragmentManager.beginTransaction();
                t.replace(R.id.fragment,msgFragment);
                t.commit();
            });

            FragmentTransaction t = fragmentManager.beginTransaction();
            t.replace(R.id.fragment,gradesFragment);
            t.addToBackStack(null);
            t.commit();
        }, (error) -> {
            //Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> map = new TreeMap<>();
                map.put("login",login);
                map.put("password",password);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(120000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        r.add(stringRequest);
        });
    }
}