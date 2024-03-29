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
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{

    Context context;
    ArrayList<MissingPerson> list;

    public PersonAdapter(Context context, ArrayList<MissingPerson> list) {
        this.context = context;
        this.list = list;
    }

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
        holder.bind(mp);
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
            imageView = view.findViewById(R.id.imageViewCrime);
            name = view.findViewById(R.id.textViewName);
            contact = view.findViewById(R.id.textViewContact);
            city = view.findViewById(R.id.textViewlocation);
            birthspot = view.findViewById(R.id.textViewDecription);
        }

        public void bind(MissingPerson obj){
            Glide.with(itemView.getContext()).load(obj.getImage()).into(imageView);
            name.setText(obj.getName());
            contact.setText(obj.getContact());
            city.setText(obj.getCity());
            birthspot.setText(obj.getBirthSpot());
        }

    }
}
