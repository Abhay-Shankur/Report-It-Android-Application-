package com.application.crimeapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.crimeapplication.R;
import com.application.crimeapplication.model.MissingPerson;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{

    Context context;
    ArrayList<MissingPerson> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_person, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MissingPerson mp = list.get(position);
        holder.name.setText(mp.getName());
        holder.contact.setText(mp.getContact());
        holder.city.setText(mp.getCity());
        holder.birthspot.setText(mp.getBirthSpot());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView contact;
        TextView city;
        TextView birthspot;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageView = view.findViewById(R.id.imageView);
            name = view.findViewById(R.id.textViewName);
            contact = view.findViewById(R.id.textViewContact);
            city = view.findViewById(R.id.textViewCity);
            birthspot = view.findViewById(R.id.textViewBirthSpot);

        }

    }
}
