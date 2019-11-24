package com.example.seolympicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyClientListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

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
    public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.contact_item, parent, false);
        MyListAdapter.ViewHolder viewHolder = new MyListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {

        final Client myClient= clientList.get(position);
        //holder.textView.setText(myNote.getNote());
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
            linearLayout =itemView.findViewById(R.id.linear_layout);
        }
    }
}
