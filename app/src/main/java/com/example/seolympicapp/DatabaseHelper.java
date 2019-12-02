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
    private static final String TABLE_CLIENT = "client";

    //common column names
    private static final String USER_ID = "user_id";

    //user column names
    private static final String USER_EMAIL="user_email";
    private static final String USER_PW="user_pw"; //password

    //note column names
    private static final String NOTE_ID="note_id";
    private static final String NOTE_NOTE="note_note";
    private static final String NOTE_TIMESTAMP="note_time";

    //client column names
    private static final String CLIENT_ID="client_id";
    private static final String CLIENT_NAME="client_name";
    private static final String CLIENT_EMAIL="client_email";
    private static final String CLIENT_WEBSITE="client_website";
    private static final String CLIENT_TEL="client_tel";
    private static final String CLIENT_COMPANY="client_company";
    private static final String CLIENT_ADDRESS="client_address";


    //create table statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_EMAIL
            + " TEXT," + USER_PW + " TEXT" + ")";

    private static final String CREATE_TABLE_NOTE = "CREATE TABLE "
            + TABLE_NOTE + "(" + NOTE_ID + " INTEGER PRIMARY KEY," + NOTE_NOTE
            + " TEXT," + NOTE_TIMESTAMP + " DATETIME," + USER_ID + " INTEGER" + ")";

    private static final String CREATE_TABLE_CLIENTS = "CREATE TABLE "
            + TABLE_CLIENT + "(" + CLIENT_ID + " INTEGER PRIMARY KEY," + CLIENT_NAME
            + " TEXT," + CLIENT_EMAIL + " TEXT," + CLIENT_WEBSITE + " TEXT," + CLIENT_TEL
            + " TEXT," + CLIENT_COMPANY + " TEXT," + CLIENT_ADDRESS + " TEXT," + USER_ID + " INTEGER" + ")";


    //constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_CLIENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);

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

    public void deleteOneNote(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, NOTE_ID + " = ?",
                new String[] { String.valueOf(id) });

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
    // create Note
    public long createClient(Client client) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLIENT_ID, client.getId());
        values.put(CLIENT_NAME, client.getName());
        values.put(CLIENT_EMAIL, client.getEmail());
        values.put(CLIENT_WEBSITE, client.getWebsite());
        values.put(CLIENT_TEL, client.getTel());
        values.put(CLIENT_COMPANY, client.getCompany());
        values.put(CLIENT_ADDRESS, client.getAddress());
        values.put(USER_ID,client.getUser_id());

        // insert row
        long id = db.insert(TABLE_CLIENT, null, values);
        return id;

    }

    public List<Client> getUserClients(long user_id) {

        List<Client> clients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT + " WHERE "
                + USER_ID + " = " + user_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Client client = new Client();
                client.setId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                client.setName((c.getString(c.getColumnIndex(CLIENT_NAME))));
                client.setEmail(c.getString(c.getColumnIndex(CLIENT_EMAIL)));
                client.setWebsite(c.getString(c.getColumnIndex(CLIENT_WEBSITE)));
                client.setTel(c.getString(c.getColumnIndex(CLIENT_TEL)));
                client.setCompany(c.getString(c.getColumnIndex(CLIENT_COMPANY)));
                client.setAddress(c.getString(c.getColumnIndex(CLIENT_ADDRESS)));
                client.setUser_id(c.getInt(c.getColumnIndex(USER_ID)));
                clients.add(client);
            }while (c.moveToNext());
        }
        return clients;
    }

    public Client getAClient(long client_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT + " WHERE "
                + CLIENT_ID + " = " + client_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        Client client = new Client();
        client.setUser_id(c.getInt(c.getColumnIndex(USER_ID)));
        client.setName(c.getString(c.getColumnIndex(CLIENT_NAME)));
        client.setEmail(c.getString(c.getColumnIndex(CLIENT_EMAIL)));
        client.setWebsite(c.getString(c.getColumnIndex(CLIENT_WEBSITE)));
        client.setTel(c.getString(c.getColumnIndex(CLIENT_TEL)));
        client.setCompany(c.getString(c.getColumnIndex(CLIENT_COMPANY)));
        client.setAddress(c.getString(c.getColumnIndex(CLIENT_ADDRESS)));

        return client;
    }

    public List<Client> getAllClients() {
        List<Client> clients= new ArrayList<Client>();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Client client = new Client();
                client.setId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                client.setUser_id(c.getInt(c.getColumnIndex(USER_ID)));
                client.setName(c.getString(c.getColumnIndex(CLIENT_NAME)));
                client.setEmail(c.getString(c.getColumnIndex(CLIENT_EMAIL)));
                client.setWebsite(c.getString(c.getColumnIndex(CLIENT_WEBSITE)));
                client.setTel(c.getString(c.getColumnIndex(CLIENT_TEL)));
                client.setCompany(c.getString(c.getColumnIndex(CLIENT_COMPANY)));
                client.setAddress(c.getString(c.getColumnIndex(CLIENT_ADDRESS)));

                // adding to todo list
                clients.add(client);
            } while (c.moveToNext());
        }
        return clients;
    }
    public int updateClient(Client client) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLIENT_NAME,client.getName());
        values.put(CLIENT_EMAIL,client.getEmail());
        values.put(CLIENT_WEBSITE,client.getWebsite());
        values.put(CLIENT_TEL,client.getTel());
        values.put(CLIENT_ADDRESS,client.getAddress());
        values.put(CLIENT_COMPANY,client.getCompany());

        // updating row
        return db.update(TABLE_CLIENT, values, CLIENT_ID + " = ?",
                new String[] { String.valueOf(client.getId()) });
    }
    public void deleteAllClients()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_CLIENT );
    }
    public void deleteAllClients(int user_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENT, USER_ID + " = ?",
                new String[] { String.valueOf(user_id) });
    }
    public void deleteAClient(int client_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENT, CLIENT_ID + " = ?",
                new String[] { String.valueOf(client_id) });

    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
