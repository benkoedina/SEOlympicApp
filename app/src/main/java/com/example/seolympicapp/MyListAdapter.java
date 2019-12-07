package com.example.seolympicapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    //Adapter for the NoteActivity

    private List<Note> noteList;
    private Context context;
    private DatabaseHelper db;

    public MyListAdapter(List<Note> noteList, Context context,DatabaseHelper db) {
        this.noteList=noteList;
        this.context = context;
        this.db = db;
    }


    @NonNull
    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflates the layout for one Item, list_item contains the design for 1 Note item
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyListAdapter.ViewHolder holder, final int position) {

        //get the Note from the "position" position from the list
        final Note myNote= noteList.get(position);
        holder.textView.setText(myNote.getNote());

        //if we click on an item
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),"click on item: " + position,Toast.LENGTH_LONG).show();

                //alertDialog to check the Note separately
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Your Note");
                builder.setMessage(myNote.getNote() + " " + myNote.getTimestamp());

                //alertDialog Modify Button functionality
                builder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //new alertDialog for the modified elements
                        final View view = LayoutInflater.from(context).inflate(R.layout.alert_layout, null);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setView(view);
                        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText et_update = view.findViewById(R.id.update);
                                Log.d("Update", et_update.getText().toString());
                                Note note = noteList.get(position);
                                Note noteUp = new Note(note.getId(),et_update.getText().toString(),note.getTimestamp(),note.getUser_id());

                                //we update the  list, adapter, database
                                noteList.set(position,noteUp);
                                notifyItemChanged(position);
                                db.updateNote(noteUp);
                            }
                        });
                        AlertDialog d= dialog.create();
                        d.show();
                    }
                });

                //alertDialog Delete Button functionality
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Note note = noteList.get(position);

                        //remove from the database, and notify the adapter, and update the list
                        noteList.remove(position);
                        notifyItemRemoved(position);
                        db.deleteOneNote(note.getId());
                    }
                });

                //alertDialog Cancel Button functionality
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.tv_note);
            linearLayout =itemView.findViewById(R.id.linear_layout);
        }
    }
}

