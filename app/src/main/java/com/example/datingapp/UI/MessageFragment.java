package com.example.datingapp.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datingapp.R;
import com.example.datingapp.adapter.DatingAdapter;
import com.example.datingapp.adapter.MessageUserAdapter;
import com.example.datingapp.databinding.FragmentMessageBinding;
import com.example.datingapp.model.UserModel;
import com.example.datingapp.utils.Dialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class MessageFragment extends Fragment {

    private FragmentMessageBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentMessageBinding.inflate(getLayoutInflater());



        Dialog dialog=new Dialog(getActivity());



        String currentId= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference myRef;


        FirebaseDatabase.getInstance().getReference("chats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<String> list =new ArrayList<>();
                        ArrayList<String> newlist=new ArrayList<>();

                         for(DataSnapshot d:snapshot.getChildren())
                         {
                             if(d.getKey().contains(currentId))
                             {
                                list.add(d.getKey().replace(currentId,""));
                                newlist.add(d.getKey());
                             }
                         }

                        try {
                            MessageUserAdapter ad =new MessageUserAdapter(getContext(),list ,newlist);
                            binding.recyclerView.setAdapter(ad);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.Dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






















        return binding.getRoot();
    }






}