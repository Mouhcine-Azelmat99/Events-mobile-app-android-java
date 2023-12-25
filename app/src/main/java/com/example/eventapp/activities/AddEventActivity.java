package com.example.eventapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eventapp.models.Event;
import com.example.eventapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference reference;
    Button addEvent ;
    public final static int RESULT_NEW_EVENT_ADDED = 100;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyNotificationChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        addEvent =(Button) findViewById(R.id.button_add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event();
                EditText titleEditText=(EditText)findViewById(R.id.edit_text_title);
                EditText descEditText=(EditText)findViewById(R.id.edit_text_description);
                EditText dateEditText=(EditText)findViewById(R.id.edit_text_date);
                EditText userEditText=(EditText)findViewById(R.id.edit_text_username);

                event.setTitle(titleEditText.getText().toString());
                event.setDesc(descEditText.getText().toString());
                event.setUser(userEditText.getText().toString());
                event.setDate(dateEditText.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                event.setCreated_at(sdf.format(new Date()));

                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Events");
                reference.child(event.getEvent_id()).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //openHomeActivity();
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                        builder.setTitle("Success!");
                        builder.setMessage("Your operation was successful!");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when the button is clicked, like closing the dialog
                                dialog.dismiss();
                                openHomeActivity();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("This channel is used for my app's notifications.");

                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(channel);
                }

                sendNotification(event);
            }
        });

    }


    public void openHomeActivity() {

        Intent intent = new Intent(this,MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void sendNotification(Event event)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("ENSAH EVENTS APP")
                .setContentText("Alert: New event published. Tap to view!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create an intent to launch your desired activity
        //Intent intent = new Intent(this, MainActivity.class); // Replace with your desired activity

        Intent intent = new Intent(this, ShowEventDetails.class);
        intent.putExtra("event", event); // Pass the ID or relevant event identifier
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Create a pending intent that will be triggered when the notification is clicked
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the pending intent on the notification builder
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}