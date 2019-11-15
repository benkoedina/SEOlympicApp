package com.example.seolympicapp;

public class Note {

     private  int id;
     private String note;
     private  String timestamp;
     private  int user_id;

    public Note(int id, String note, String timestamp, int user_id) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
        this.user_id=user_id;
    }

    public Note( String note, String timestamp) {
        this.note = note;
        this.timestamp = timestamp;
    }
    public Note( String note, String timestamp, int user_id) {
        this.note = note;
        this.timestamp = timestamp;
        this.user_id=user_id;
    }
    public  Note()
    {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
