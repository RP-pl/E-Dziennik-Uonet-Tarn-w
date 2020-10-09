package com.example.edz_android_gui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.edz_android_gui.Adapters.LessonAdapter;
import com.example.edz_android_gui.Adapters.MsgAdapter;
import com.example.edz_android_gui.DAO.HourRow;
import com.example.edz_android_gui.DAO.Message;
import com.example.edz_android_gui.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessagesFragment extends Fragment {
    private List<Message> msgList;
    public MessagesFragment(List<Message> hourRows){
        this.msgList = hourRows;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages_frag,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ListView lv = view.findViewById(R.id.msg_list);
        lv.setAdapter(new MsgAdapter(this.getContext(),msgList));
        lv.setOnItemClickListener((parent,view1,pos,id)->{
            TextView t = view.findViewById(R.id.msg_text_field);
            StringBuilder s = new StringBuilder();
            for(String str : msgList.get(pos).getText().split("\\\\n")){
               s.append(str+"\n");
            }
            t.setText(s.toString());
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
