package com.example.edz_android_gui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.edz_android_gui.DAO.Day;
import com.example.edz_android_gui.R;

import java.util.List;

public class ExamsAdapter extends BaseAdapter {
    private static String getExamsList(List<String> exams){
        StringBuilder s = new StringBuilder();
        for(String exam : exams){
            s.append(exam).append("    ");
        }
        return s.toString();
    }

    private final List<Day> days;
    private final Context context;
    private final LayoutInflater inflater;

    public ExamsAdapter(Context context, List<Day> days){
        this.days = days;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)v=inflater.inflate(R.layout.exams_layout,null);
        TextView date = v.findViewById(R.id.date_exam);
        date.setText(days.get(position).getDate());
        TextView exams = v.findViewById(R.id.exams_exams);
        exams.setText(getExamsList(days.get(position).getExams()));
        return v;
    }
}
