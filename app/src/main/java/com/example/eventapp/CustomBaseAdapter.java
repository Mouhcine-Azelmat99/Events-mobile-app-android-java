package com.example.eventapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eventapp.activities.ShowEventDetails;
import com.example.eventapp.models.Event;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    List<Event> events;
    LayoutInflater inflater;
    Context ctxt ;


    public CustomBaseAdapter(Context context, List<Event> eventList){
        this.events = eventList;
        this.ctxt = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Log.d("show event : ",events.get(i).toString());
        view = inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView titleText = view.findViewById(R.id.title);
        TextView dateText = view.findViewById(R.id.date);
        TextView userText = view.findViewById(R.id.username);
        TextView createdAtText = view.findViewById(R.id.created_at);

        titleText.setText((CharSequence) events.get(i).getTitle());
        dateText.setText((CharSequence) events.get(i).getDate());
        userText.setText((CharSequence) events.get(i).getUser());
        createdAtText.setText((CharSequence) events.get(i).getCreated_at());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event clickedEvent = events.get(i);
                Intent intent = new Intent(ctxt, ShowEventDetails.class);
                intent.putExtra("event", clickedEvent); // Pass the ID or relevant event identifier
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctxt.startActivity(intent);

            }
        });



        return view;

    }

}
