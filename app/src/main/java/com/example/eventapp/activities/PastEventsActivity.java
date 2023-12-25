package com.example.eventapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.eventapp.CustomBaseAdapter;
import com.example.eventapp.models.Event;
import com.example.eventapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PastEventsActivity extends AppCompatActivity{

    ListView listView;
    Button liveEventsBtn;

    List<Event> events;
    FirebaseDatabase db;
    DatabaseReference reference;
    CustomBaseAdapter customBaseAdapter;
    public final static int RESULT_NEW_EVENT_ADDED = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_events);

        try {
            customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), getData());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        listView = findViewById(R.id.customListView);
        listView.setAdapter(customBaseAdapter);

        listView = findViewById(R.id.customListView);
        listView.setAdapter(customBaseAdapter);
        liveEventsBtn = (Button) findViewById(R.id.liveEvents);
        liveEventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLiveEventsActivity();
            }
        });


    }

    public List<Event> getData() throws InterruptedException {
        Log.d("Past EVents", "Get Data");
        //Thread.sleep(5000);
        List<Event> eventList = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    String title = eventSnapshot.child("title").getValue(String.class);
                    String description = eventSnapshot.child("desc").getValue(String.class);
                    String date = eventSnapshot.child("date").getValue(String.class);
                    String username = eventSnapshot.child("user").getValue(String.class);
                    String created_at = eventSnapshot.child("created_at").getValue(String.class);

                    Event event = new Event(title, username, description, date);
                    event.setCreated_at(created_at);
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date dateOfEvent;
                    try {
                        dateOfEvent = dateFormat.parse(date);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    if (dateOfEvent != null && dateOfEvent.after(currentDate)) {
                        Log.d("future Date", String.valueOf(dateOfEvent));
                    } else if (dateOfEvent != null && dateOfEvent.before(currentDate)) {
                        Log.d("oppps ", "pasted");
                        eventList.add(event);
                    } else {
                        eventList.add(event);
                    }
                    CustomBaseAdapter adapter = (CustomBaseAdapter) listView.getAdapter();
                    adapter.notifyDataSetChanged();

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("fin  ", "proccess");

        Log.d("Dat list :", eventList.toString());
        return eventList;
    }

    public void openLiveEventsActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}