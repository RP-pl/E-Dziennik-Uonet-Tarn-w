package com.example.edz_android_gui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.edz_android_gui.DAO.HourRow;
import com.example.edz_android_gui.DAO.Message;
import com.example.edz_android_gui.R;

import java.util.List;

public class MsgAdapter extends BaseAdapter {
    private List<Message> msgs;
    private LayoutInflater inflater;
    public MsgAdapter(Context c, List<Message> lessons){
        this.msgs = lessons;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)v= inflater.inflate(R.layout.msg_layout,null);
        TextView from = v.findViewById(R.id.msg_from);
        from.setText(msgs.get(position).getFrom());
        TextView topic = v.findViewById(R.id.msg_topic);
        topic.setText(msgs.get(position).getTopic());
        TextView date = v.findViewById(R.id.msg_date);
        date.setText(msgs.get(position).getDate());
        return v;
    }

}
