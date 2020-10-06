package com.example.edz_android_gui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.edz_android_gui.DAO.Subject;
import com.example.edz_android_gui.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SubjectAdapter extends BaseAdapter {
    private final Context context;
    private final List<Subject> subjects;
    private static LayoutInflater inflater = null;
    public SubjectAdapter(@NotNull Context context, @NotNull List<Subject> subjects){
        this.context = context;
        this.subjects = subjects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)
            v = inflater.inflate(R.layout.subject_layout,null);
        TextView subject = v.findViewById(R.id.subject_subject);
        subject.setText(subjects.get(position).getName());
        TextView avg = v.findViewById(R.id.subject_avg);
        avg.setText(String.valueOf(subjects.get(position).getAvg()));
        TextView proposed = v.findViewById(R.id.subject_proposed);
        proposed.setText(subjects.get(position).getProposed());
        TextView _final = v.findViewById(R.id.subject_fin);
        _final.setText(subjects.get(position).getFinal_());
        return v;
    }
}
