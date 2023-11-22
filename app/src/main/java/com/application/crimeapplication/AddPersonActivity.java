package com.application.crimeapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.application.crimeapplication.databinding.ActivityAddPersonBinding;
import com.application.crimeapplication.model.MissingPerson;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddPersonActivity extends AppCompatActivity {

    ActivityAddPersonBinding binding;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;

//    private  FirebaseDatabase


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Initiize Firebase elemnents
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://crime-application-7e7be-default-rtdb.asia-southeast1.firebasedatabase.app");
//        DatabaseReference myRef = database.getReference("Missing person");

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
                            Toast.makeText(AddPersonActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                            // Get the URL of the uploaded image
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Display the image URL in a Toast message
                                    saveDetailsToFirestore(uri.toString());
                                    Toast.makeText(AddPersonActivity.this, "Image URL: " + uri.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful uploads
                            Toast.makeText(AddPersonActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveDetailsToFirestore(String imageUrl) {
        // Create a map to store details
        String name = binding.textInputLayoutName.getEditText().getText().toString();
        String contact = binding.textInputLayoutContact.getEditText().getText().toString();
        String city = binding.textInputLayoutCity.getEditText().getText().toString();
        String births = binding.editTextBirthSpot.getText().toString();

        MissingPerson mp = new MissingPerson(imageUri.toString(), name, contact, city, births);

        // Add the details to Firestore
        firestore.collection("Missing Persons")
                .add(mp)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(AddPersonActivity.this, "Details uploaded to Firestore", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPersonActivity.this, "Failed to upload details to Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 3812 && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            binding.imageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }
//
//    }
}