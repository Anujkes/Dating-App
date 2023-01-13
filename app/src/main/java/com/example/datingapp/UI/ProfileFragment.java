package com.example.datingapp.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.datingapp.R;
import com.example.datingapp.activity.EditProfileActivity;
import com.example.datingapp.auth.LoginActivity;
import com.example.datingapp.databinding.FragmentDatingBinding;
import com.example.datingapp.databinding.FragmentProfileBinding;
import com.example.datingapp.model.UserModel;
import com.example.datingapp.utils.Dialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(getLayoutInflater());


        Dialog dialog=new Dialog(getActivity());

        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists())
                        {
                            UserModel user=dataSnapshot.getValue(UserModel.class);

                            binding.userCity.setText(user.getCity());
                            binding.userName.setText(user.getName());
                            binding.userEmail.setText(user.getEmail());
                            binding.userNumber.setText(user.getNumber());

                            Glide.with(getContext())
                                    .load(user.getImage())
                                    .placeholder(R.drawable.man)
                                    .into(binding.userImage);


                        }


                    }
                });


          dialog.Dismiss();




          binding.logout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                 FirebaseAuth.getInstance().signOut();
                 startActivity(new Intent(getContext(), LoginActivity.class));
                 requireActivity().finish();

              }
          });

          binding.editProfile.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(getContext(), EditProfileActivity.class));
              }
          });







        return binding.getRoot();


    }
}