package com.application.crimeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.application.crimeapplication.databinding.ActivityHomeBinding;
import com.application.crimeapplication.model.MissingPerson;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://crime-application-7e7be-default-rtdb.asia-southeast1.firebasedatabase.app");
//        DatabaseReference myRef = database.getReference("Missing person");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                MissingPerson value = dataSnapshot.getValue(MissingPerson.class);
//                Log.d("TAG", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddPersonActivity.class);
                startActivity(intent);
            }
        });
    }
}