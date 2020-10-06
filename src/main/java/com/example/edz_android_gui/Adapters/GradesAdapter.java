package com.example.edz_android_gui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import com.example.edz_android_gui.DAO.Grade;
import com.example.edz_android_gui.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GradesAdapter extends BaseAdapter {
    private final List<Grade> grades;
    private Context context;
    private static LayoutInflater inflater = null;
    public GradesAdapter(@NonNull Context context, @NonNull List<Grade> objects) {
        this.grades = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null)
                v = inflater.inflate(R.layout.grade_layout,null);
            TextView sub = v.findViewById(R.id.sub_field);
            sub.setText(grades.get(position).getSubject());
            TextView gr = v.findViewById(R.id.gr_field);
            gr.setText(grades.get(position).getGrade());
            TextView weight = v.findViewById(R.id.wg_field);
            weight.setText(grades.get(position).getWeight());
            TextView date = v.findViewById(R.id.date_field);
            date.setText(grades.get(position).getDate());
            TextView descr = v.findViewById(R.id.descr_field);
            descr.setText(grades.get(position).getDescription());
            TextView te = v.findViewById(R.id.tea_field);
            te.setText(grades.get(position).getTeacher());

        return v;
    }
}
