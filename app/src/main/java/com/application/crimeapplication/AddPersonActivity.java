package com.application.crimeapplication;

import androidx.annotation.NonNull;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddPersonActivity extends AppCompatActivity {

    ActivityAddPersonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPersonBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 3812);
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Bitmap bitmap = ((BitmapDrawable) binding.imageButton.getDrawable()).getBitmap();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] data = baos.toByteArray();
//
//                StorageReference storageRef = storage.getReference("Images");
//                UploadTask uploadTask = storageRef.putBytes(data);
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                        // ...
//                        Toast.makeText(AddPersonActivity.this, "Image Success", Toast.LENGTH_SHORT).show();
//                    }
//                });


                FirebaseDatabase database = FirebaseDatabase.getInstance("https://crime-application-7e7be-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference myRef = database.getReference("Missing person");

                String name = binding.textInputLayoutName.getEditText().getText().toString();
                String contact = binding.textInputLayoutContact.getEditText().getText().toString();
                String city = binding.textInputLayoutCity.getEditText().getText().toString();
                String birthspot = binding.editTextBirthSpot.getText().toString();

                MissingPerson mp = new MissingPerson("", name, contact, city, birthspot);
                try {
                    myRef.setValue(mp);
                    Toast.makeText(AddPersonActivity.this, "Person Added Succesfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(AddPersonActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                    Log.d("AddPersonActivity", "onClick: Aborted");
                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3812 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            binding.imageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }
}