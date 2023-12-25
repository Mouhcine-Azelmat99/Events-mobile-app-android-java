package com.example.eventapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.eventapp.models.Event;
import com.example.eventapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowEventDetails extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference reference;

    String event_id;
    Event event;

    TextView titletext ;
    TextView desctext ;
    TextView datetext ;
    TextView createdtext ;
    TextView usertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_details);


        this.event = getIntent().getParcelableExtra("event");

        Log.d("Event show page" , this.event.toString());

        titletext= findViewById(R.id.titleEvent);
        desctext= findViewById(R.id.descEvent);
        createdtext= findViewById(R.id.created_atEvent);
        datetext= findViewById(R.id.dateEvent);
        usertext= findViewById(R.id.userEvent);

        titletext.setText(this.event.getTitle());
        desctext.setText(this.event.getDate());
        usertext.setText(this.event.getDesc());
        createdtext.setText(this.event.getCreated_at());
        datetext.setText(this.event.getUser());

    }

    private void displayEventDetails(String eventId) {

    }


}