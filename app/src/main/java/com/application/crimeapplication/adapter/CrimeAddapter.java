package com.application.crimeapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.crimeapplication.R;
import com.application.crimeapplication.model.Crime;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CrimeAddapter extends RecyclerView.Adapter<CrimeAddapter.ViewHolder>{

    Context context;
    ArrayList<Crime> list;

    public CrimeAddapter(Context context, ArrayList<Crime> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CrimeAddapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crime_card, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CrimeAddapter.ViewHolder holder, int position) {
        Crime obj = list.get(position);
        holder.bind(obj);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView city;
        TextView description;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageView = view.findViewById(R.id.imageViewCrime);
            city = view.findViewById(R.id.textViewlocation);
            description = view.findViewById(R.id.textViewDecription);

        }
        @SuppressLint("SetTextI18n")
        public void bind(Crime details) {
            Glide.with(itemView.getContext()).load(details.getImage()).into(imageView);
            description.setText("Description: " + details.getDescription());
            city.setText("Address: " + details.getLocation());
        }
    }
}
