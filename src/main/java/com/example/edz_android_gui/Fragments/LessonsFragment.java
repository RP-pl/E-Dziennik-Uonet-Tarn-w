package com.example.edz_android_gui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.edz_android_gui.Adapters.LessonAdapter;
import com.example.edz_android_gui.DAO.HourRow;
import com.example.edz_android_gui.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LessonsFragment  extends Fragment {
    private List<HourRow> hourRowList;
    public LessonsFragment(List<HourRow> hourRows){
        this.hourRowList = hourRows;
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lessons_frag,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ListView lv = (ListView) view.findViewById(R.id.lessons_list);
        lv.setAdapter(new LessonAdapter(this.getContext(),hourRowList));
        super.onViewCreated(view, savedInstanceState);
    }
}
