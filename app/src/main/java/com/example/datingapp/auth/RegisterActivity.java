package com.example.datingapp.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Toast;

import com.example.datingapp.MainActivity;
import com.example.datingapp.R;
import com.example.datingapp.databinding.ActivityLoginBinding;
import com.example.datingapp.databinding.ActivityRegisterBinding;
import com.example.datingapp.model.UserModel;
import com.example.datingapp.utils.Dialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {


    private ActivityRegisterBinding binding;
    private Uri imgUri=null;
    private Dialog dialog;
    private final int REQ=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.uploadText.setVisibility(View.GONE);



        binding.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });



        binding.contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }


        });

    }

    private void validateData() {


        if(binding.userName.getText().toString().isEmpty())
        {
            binding.userName.setError("Empty");

        }
        else if(binding.userEmail.getText().toString().isEmpty())
        {
            binding.userEmail.setError("Empty");

        }
        else if(binding.userCity.getText().toString().isEmpty())
        {
            binding.userCity.setError("Empty");

        }
        else if(imgUri==null)
        {
            Toast.makeText(this, "Please upload your Image", Toast.LENGTH_SHORT).show();
        }
        else if(!binding.terms.isChecked()) {
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
        }
        else
        {
            uploadImage();
        }
    }

    private void uploadImage() {




        dialog=new Dialog(RegisterActivity.this);//start showing//
        StorageReference sRef;
        sRef= FirebaseStorage.getInstance().getReference("profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile.jpg");

        sRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploadData(uri);
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.Dismiss();
                        Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.Dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void openGallery() {

        Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(pickImage,REQ);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK)
        {
           imgUri =data.getData();
            binding.userImage.setImageURI(imgUri);


        }



    }

    private void uploadData(Uri uri) {
        Toast.makeText(this, "Upload data", Toast.LENGTH_SHORT).show();
//name.email,image,city//
        UserModel data=new UserModel(
                binding.userName.getText().toString(),
                binding.userEmail.getText().toString(),
                uri.toString(),
                binding.userCity.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),"","","","",""
        );




        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.Dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       dialog.Dismiss();
                        Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}