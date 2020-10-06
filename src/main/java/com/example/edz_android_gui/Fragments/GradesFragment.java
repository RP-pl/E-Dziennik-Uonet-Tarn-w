package com.example.edz_android_gui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.edz_android_gui.DAO.Grade;
import com.example.edz_android_gui.Adapters.GradesAdapter;
import com.example.edz_android_gui.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GradesFragment extends Fragment {
    public List<Grade> grades;
    public GradesFragment(List<Grade> grades){
        this.grades = grades;
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grades_frag,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ListView listView = (ListView)view.findViewById(R.id.grades_list);
        listView.setAdapter(new GradesAdapter(this.getContext(),grades));
    }
}
