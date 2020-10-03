package com.example.edz_android_gui;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button grades = findViewById(R.id.grades_button);
        grades.setOnClickListener((view)->{
            Fragment f = fragmentManager.findFragmentById(R.id.fragment);
            NavHostFragment.findNavController(f).navigate(R.id.to_gradesFragment);
        });
        Button subjects = findViewById(R.id.subjects_button);
        subjects.setOnClickListener((view)->{
            Fragment f = fragmentManager.findFragmentById(R.id.fragment);
            NavHostFragment.findNavController(f).navigate(R.id.to_subjectFragment);
        });
    }
}