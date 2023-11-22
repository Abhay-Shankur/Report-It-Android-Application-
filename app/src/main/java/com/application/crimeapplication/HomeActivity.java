package com.application.crimeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.application.crimeapplication.adapter.CrimeAddapter;
import com.application.crimeapplication.adapter.PersonAdapter;
import com.application.crimeapplication.databinding.ActivityHomeBinding;
import com.application.crimeapplication.model.Crime;
import com.application.crimeapplication.model.MissingPerson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    String screenType;

    private FirebaseFirestore firestore;

    private CrimeAddapter crimeAddapter;
    private ArrayList<Crime> crimeList;
    private PersonAdapter personAdapter;
    private ArrayList<MissingPerson> missingPersonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        screenType = getIntent().getStringExtra("Type");

        firestore = FirebaseFirestore.getInstance();

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        // Initialize the List and Adapter
        crimeList = new ArrayList<Crime>();
        crimeAddapter = new CrimeAddapter(this, crimeList);
        missingPersonList = new ArrayList<MissingPerson>();
        personAdapter = new PersonAdapter(this, missingPersonList);

        binding.recyclerview.setAdapter(screenType.equals("ADDCRIME")?crimeAddapter:personAdapter);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenType.equals("ADDCRIME")){
                    Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                    intent.putExtra("Type", "ADDCRIME");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                    intent.putExtra("Type", "ADDMISSING");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch details from Firestore and display them
        fetchDetailsFromFirestore();
    }

    private void fetchDetailsFromFirestore() {
        // Assuming you have a collection named "details" in Firestore
        firestore.collection(screenType.equals("ADDCRIME")?"Crimes":"Missing Persons")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Clear the existing list
                            if (screenType.equals("ADDCRIME")){
                                crimeList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // DocumentSnapshot data
                                    Crime obj = new Crime();
                                    obj.setImage(document.getString("image"));
                                    obj.setLocation(document.getString("location"));
                                    obj.setDescription(document.getString("description"));
                                    // Create a Details object and add it to the list
                                    crimeList.add(obj);
                                }
                                // Notify the adapter that the data set has changed
                                crimeAddapter.notifyDataSetChanged();
                            } else {
                                missingPersonList.clear();
                                for (QueryDocumentSnapshot document: task.getResult()){
                                    MissingPerson obj = new MissingPerson();
                                    obj.setImage(document.getString("image"));
                                    obj.setName(document.getString("name"));
                                    obj.setContact(document.getString("contact"));
                                    obj.setCity(document.getString("city"));
                                    obj.setBirthSpot(document.getString("birthSpot"));
                                    // Create a Details object and add it to the list
                                    missingPersonList.add(obj);
                                }
                                // Notify the adapter that the data set has changed
                                personAdapter.notifyDataSetChanged();
                            }
                        } else {
                            // Task failed with an exception
                            Toast.makeText(HomeActivity.this, "Error fetching details: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}