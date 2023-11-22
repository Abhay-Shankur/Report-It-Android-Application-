package com.application.crimeapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.application.crimeapplication.databinding.ActivityAddBinding;
import com.application.crimeapplication.model.Crime;
import com.application.crimeapplication.model.MissingPerson;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;
    private String screenType;


    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Initiize Firebase elemnents
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();

//        Getting Screen Type
        screenType = getIntent().getStringExtra("Type");

        if (screenType.equals("ADDCRIME")) {
            binding.textInputLayoutName.setVisibility(View.INVISIBLE);
            binding.textInputLayoutContact.setVisibility(View.INVISIBLE);

            binding.imageButton.setImageDrawable(getDrawable(R.drawable.crime1));
            binding.TextInputEditTextCity.setHint("Full Address");
            binding.editTextBirthSpot.setHint("Add Description about the Crime");
        }


        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(v);
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(v);
            }
        });
    }

    //    Onclick for ImageView Button
    public void pickImage(View view) {
        // Open the file picker
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Display the selected image in ImageView
            binding.imageButton.setImageURI(imageUri);
        }
    }

    public void uploadImage(View view) {
        if (imageUri != null) {
            // Create a unique name for the image in Firebase Storage
            String imageName = "images/" + System.currentTimeMillis() + ".jpg";

            StorageReference imageRef = storageReference.child(imageName);

            // Upload image to Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Image uploaded successfully
                            Toast.makeText(AddActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                            // Get the URL of the uploaded image
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Display the image URL in a Toast message
                                    saveDetailsToFirestore(uri.toString());
                                    Toast.makeText(AddActivity.this, "Image URL: " + uri.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful uploads
                            Toast.makeText(AddActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDetailsToFirestore(String imageUrl) {
        // Create a map to store details

        Crime objc = new Crime();
        MissingPerson objm = new MissingPerson();

        if (screenType.equals("ADDCRIME")){
            objc.setImage(imageUri.toString());
            objc.setLocation(binding.textInputLayoutCity.getEditText().getText().toString());
            objc.setDescription(binding.editTextBirthSpot.getText().toString());

        } else{
            objm.setImage(imageUri.toString());
            objm.setName(binding.textInputLayoutName.getEditText().getText().toString());
            objm.setContact(binding.textInputLayoutContact.getEditText().getText().toString());
            objm.setCity(binding.textInputLayoutCity.getEditText().getText().toString());
            objm.setBirthSpot(binding.editTextBirthSpot.getText().toString());

            // Add the details to Firestore
//            firestore.collection("Missing Persons")
//                    .add(obj)
//                    .addOnSuccessListener(new OnSuccessListener() {
//                        @Override
//                        public void onSuccess(Object o) {
//                            Toast.makeText(AddActivity.this, "Details uploaded to Firestore", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(AddActivity.this, "Failed to upload details to Firestore", Toast.LENGTH_SHORT).show();
//                        }
//                    });
        }

        // Add the details to Firestore
        firestore.collection(screenType.equals("ADDCRIME")?"Crimes":"Missing Persons")
                .add(screenType.equals("ADDCRIME")? objc : objm)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(AddActivity.this, "Details uploaded to Firestore", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Failed to upload details to Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}