package com.example.eventapp.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

@SuppressLint("ParcelCreator")
public class Event implements Parcelable {

    private String event_id;
    private String title;
    private String desc;
    private String date;
    private String user;

    @Override
    public String toString() {
        return "Event{" +
                "event_id='" + event_id + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public Event() {
        this.event_id = UUID.randomUUID().toString();

    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Event(String title, String user,String desc,String date) {
        this.event_id = UUID.randomUUID().toString();
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.user = user;
        this.created_at = new Date().toString();
    }

    private String created_at;

    @Override
    public int describeContents() {
        return 0;
    }
    protected Event(Parcel in) {
        event_id = in.readString();
        title = in.readString();
        date = in.readString();
        desc = in.readString();
        user= in.readString();
        created_at = in.readString();
    }
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(event_id);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(user);
        dest.writeString(date);
        dest.writeString(created_at);
    }
}
