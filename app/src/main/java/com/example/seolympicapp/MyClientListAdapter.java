package com.example.seolympicapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyClientListAdapter extends RecyclerView.Adapter<MyClientListAdapter.ViewHolder> {
    //adapter for the Client List recycler view
    private List<Client> clientList;
    private Context context;
    private DatabaseHelper db;

    public MyClientListAdapter(List<Client> clientList, Context context, DatabaseHelper db)
    {
        this.clientList = clientList;
        this.context = context;
        this.db= db;
    }
    @NonNull
    @Override
    public MyClientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflates the layout for one Item, contact_item contains the design for 1 Note item
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.contact_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Client myClient= clientList.get(position);
        holder.tv_name.setText(myClient.getName());
        holder.tv_email.setText(myClient.getEmail());
        holder.tv_website.setText(myClient.getWebsite());
        holder.tv_tel.setText(myClient.getTel());
        holder.tv_company.setText(myClient.getCompany());
        holder.tv_address.setText(myClient.getAddress());

        //if we click on an item
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Position",position+"");
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Your Note");
                builder.setMessage("Do you want to delete this contact?");

                //we have the posibility to delete the requested contact item
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Client client = clientList.get(position);
                        //delete from the database, notify the adapter and update the adapters list
                        clientList.remove(position);
                        notifyItemRemoved(position);
                        db.deleteAClient(client.getId());
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {

        return clientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView tv_name;
        public TextView tv_email;
        public TextView tv_website;
        public  TextView tv_tel;
        public TextView tv_company;
        public TextView tv_address;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_email = itemView.findViewById(R.id.tv_email);
            this.tv_website = itemView.findViewById(R.id.tv_website);
            this.tv_tel=itemView.findViewById(R.id.tv_tel);
            this.tv_company=itemView.findViewById(R.id.tv_company);
            this.tv_address=itemView.findViewById(R.id.tv_address);
            linearLayout =itemView.findViewById(R.id.linear_layout_clients);
        }
    }
}
