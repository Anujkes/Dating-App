package com.example.datingapp.UI;

import android.os.Bundle;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datingapp.R;
import com.example.datingapp.adapter.DatingAdapter;
import com.example.datingapp.databinding.ActivityLoginBinding;
import com.example.datingapp.databinding.FragmentDatingBinding;
import com.example.datingapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;


public class DatingFragment extends Fragment {
    private  ArrayList<UserModel> list;
     private FragmentDatingBinding binding;
     private CardStackLayoutManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding= FragmentDatingBinding.inflate(getLayoutInflater());


         getData();
        return binding.getRoot();
    }



    private void getData() {

         list=new ArrayList<>();
        DatabaseReference myRef;
        FirebaseDatabase.getInstance().getReference("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if(snapshot.exists())
                      {
                          for(DataSnapshot d:snapshot.getChildren())
                          {
                              UserModel data=d.getValue(UserModel.class);
                              if( (data.getNumber().toString() ).equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString()))
                                  continue;
                              else
                                list.add(data);
                          }
                         Collections.shuffle(list);
                          init();
                          binding.cardStackView.setLayoutManager(manager);
                          binding.cardStackView.setItemAnimator(new DefaultItemAnimator());
                          DatingAdapter ad=new DatingAdapter(getContext(),list);
                           binding.cardStackView.setAdapter(ad);
                      }
                      else
                      {
                          Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                      }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void init() {
        manager= new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {

                if(manager.getTopPosition()==list.size())
                {
                    Toast.makeText(getContext(), "Last profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });

        manager.setVisibleCount(3);
        manager.setTranslationInterval(0.6f);
        manager.setScaleInterval(0.8f);
        manager.setMaxDegree(20f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setStackFrom(StackFrom.Right);
    }
}