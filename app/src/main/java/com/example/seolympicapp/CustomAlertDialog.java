package com.example.seolympicapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomAlertDialog {

     public static Note noteR ;

    public static void showOpenDialog(Context context, final DatabaseHelper db, final MyListAdapter adapter) {

        final View view = LayoutInflater.from(context).inflate(R.layout.alert_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c);

                 EditText et_newN = view.findViewById(R.id.update);
                 Note note = new Note(et_newN.getText().toString(), formattedDate,1);
                 noteR = note;

                 Log.d("Add", note.toString());
                db.createNote(note);

            }

        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public Note note()
    {
        return noteR;
    }
}
