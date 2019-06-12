package com.example.administrator.myapplication.Jsonparsing;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.holder> {

    List<Datum> list=new ArrayList<>();
    Datum employee=null;
    Context context;
    public RecyclerviewAdapter(List<Datum> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem=layoutInflater.inflate(R.layout.row_item,parent,false);
        holder holder=new holder(listitem);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        employee=list.get(position);
       holder.employeeName.setText(employee.getId());
        holder.email.setText(employee.getEmail());
        holder.designation.setText(employee.getFirstName());
        holder.salary.setText(employee.getLastName());
        Picasso.with(context).load(employee.getAvatar()).into(holder.dob);
       // holder.dob.setText(employee.getAvatar());
      //  holder.contactNumber.setText(employee.getContactNumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        public TextView employeeName, designation, email, salary,contactNumber;
        ImageView dob;
        public holder(View view) {
            super(view);
            employeeName = (TextView) view.findViewById(R.id.employeeName);
            email = (TextView) view.findViewById(R.id.email);
            designation = (TextView) view.findViewById(R.id.designation);
            salary = (TextView) view.findViewById(R.id.salary);
            dob = (ImageView) view.findViewById(R.id.dob);
            contactNumber = (TextView) view.findViewById(R.id.contactNumber);  }
    }
}
