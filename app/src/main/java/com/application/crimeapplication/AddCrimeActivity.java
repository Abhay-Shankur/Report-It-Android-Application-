package com.application.crimeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.application.crimeapplication.databinding.ActivityAddCrimeBinding;
import com.application.crimeapplication.model.Crime;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCrimeActivity extends AppCompatActivity {

    ActivityAddCrimeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crime);

        binding =ActivityAddCrimeBinding.inflate(getLayoutInflater());

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://crime-application-7e7be-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference myRef = database.getReference("Crimes");

                String location = binding.textInputLayoutLocation.getEditText().getText().toString();
                String desc = binding.textInputLayoutDesc.getEditText().getText().toString();
                Crime cm = new Crime(location, desc, "");
                try {
                    myRef.setValue(cm);
                    Toast.makeText(AddCrimeActivity.this, "Crime Added Succesfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(AddCrimeActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                    Log.d("AddCrimeActivity", "onClick: Aborted");
                }
            }
        });
    }
}