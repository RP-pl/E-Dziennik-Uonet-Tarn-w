package com.example.edz_android_gui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.edz_android_gui.DAO.HourRow;
import com.example.edz_android_gui.R;

import java.util.List;

public class LessonAdapter extends BaseAdapter {
    private List<HourRow> lessons;
    private LayoutInflater inflater;
    public LessonAdapter(Context c, List<HourRow> lessons){
        this.lessons = lessons;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Object getItem(int position) {
        return lessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null) v= inflater.inflate(R.layout.lessons_layout,null);
        TextView lesson = v.findViewById(R.id.lessons_lesson);
        lesson.setText(lessons.get(position).getLesson());
        TextView duration = v.findViewById(R.id.lessons_duration);
        duration.setText(lessons.get(position).getDuration());
        TextView mon = v.findViewById(R.id.lessons_mon);
        mon.setText(lessons.get(position).getMon());
        TextView tue = v.findViewById(R.id.lessons_tue);
        tue.setText(lessons.get(position).getTue());
        TextView wen = v.findViewById(R.id.lessons_wen);
        wen.setText(lessons.get(position).getWen());
        TextView thu = v.findViewById(R.id.lessons_thu);
        thu.setText(lessons.get(position).getThu());
        TextView fri = v.findViewById(R.id.lessons_fri);
        fri.setText(lessons.get(position).getFri());
        return v;
    }
}
