package com.example.seolympicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "datamanager";

    //table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_NOTE = "note";

    //common column names
    private static final String USER_ID = "user_id";

    //user column names
    private static final String USER_EMAIL="user_email";
    private static final String USER_PW="user_pw"; //password

    //note column names

    private static final String NOTE_ID="note_id";
    private static final String NOTE_NOTE="note_note";
    private static final String NOTE_TIMESTAMP="note_time";

    //create table statements

    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_EMAIL
            + " TEXT," + USER_PW + " TEXT" + ")";

    private static final String CREATE_TABLE_NOTE = "CREATE TABLE "
            + TABLE_NOTE + "(" + NOTE_ID + " INTEGER PRIMARY KEY," + NOTE_NOTE
            + " TEXT," + NOTE_TIMESTAMP + " DATETIME," + USER_ID + " INTEGER" + ")";


    //constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);

        // create new tables
        onCreate(db);

    }
    public long createUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_ID, user.getId());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PW, user.getPassword());

        // insert row
        long id = db.insert(TABLE_USER, null, values);

        return id;

    }

    //get a User
    public User getUser(long user_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

      //  Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        User u = new User();
        u.setId(c.getInt(c.getColumnIndex(USER_ID)));
        u.setEmail((c.getString(c.getColumnIndex(USER_EMAIL))));
        u.setPassword(c.getString(c.getColumnIndex(USER_PW)));

        return u;
    }

    //get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User u = new User();
                u.setId(c.getInt((c.getColumnIndex(USER_ID))));
                u.setEmail((c.getString(c.getColumnIndex(USER_EMAIL))));
                u.setPassword(c.getString(c.getColumnIndex(USER_PW)));

                // adding to todo list
                users.add(u);
            } while (c.moveToNext());
        }
        return users;
    }

    // create Note
    public long createNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOTE_ID, note.getId());
        values.put(NOTE_NOTE, note.getNote());
        values.put(NOTE_TIMESTAMP, note.getTimestamp());
        values.put(USER_ID,note.getUser_id());

        // insert row
        long id = db.insert(TABLE_NOTE, null, values);
        return id;

    }
    //get all Users Note
    public List<Note> getUserNotes(long user_id) {

        List<Note> notes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " WHERE "
                + USER_ID + " = " + user_id;

        //  Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Note n = new Note();
                n.setId(c.getInt(c.getColumnIndex(NOTE_ID)));
                n.setNote((c.getString(c.getColumnIndex(NOTE_NOTE))));
                n.setTimestamp(c.getString(c.getColumnIndex(NOTE_TIMESTAMP)));
                n.setUser_id(c.getInt(c.getColumnIndex(USER_ID)));

                notes.add(n);
            }while (c.moveToNext());
        }
        return notes;
    }

    public Note getANote(long note_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " WHERE "
                + NOTE_ID + " = " + note_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        Note n = new Note();
        n.setUser_id(c.getInt(c.getColumnIndex(USER_ID)));
        n.setNote((c.getString(c.getColumnIndex(NOTE_NOTE))));
        n.setTimestamp(c.getString(c.getColumnIndex(NOTE_TIMESTAMP)));

        return n;
    }
    //update user : email+pw

    public int updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PW, user.getPassword());

        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }
    //update the Note
    public int updateNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOTE_NOTE, note.getNote());

        // updating row
        return db.update(TABLE_NOTE, values, NOTE_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
    }

    public void deleteAllNotes(int user_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

            db.delete(TABLE_NOTE, USER_ID + " = ?",
                    new String[] { String.valueOf(user_id) });

    }

    public void deleteOneNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NOTE, NOTE_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });

    }
    public void deleteUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }
    public void deleteAllUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_USER );
    }
    public void deleteAllNotes()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_NOTE );
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
