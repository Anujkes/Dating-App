package com.example.datingapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.datingapp.R;
import com.example.datingapp.adapter.ChatAdapter;
import com.example.datingapp.databinding.ActivityMessageBinding;
import com.example.datingapp.model.MessageModel;
import com.example.datingapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.message.getText().toString().isEmpty()) {
                    Toast.makeText(MessageActivity.this, "Write something", Toast.LENGTH_SHORT).show();

                }
                else {
                    storeData(binding.message.getText().toString());

                    verifyChatId();
                }
            }});


        verifyChatId();


    }



    private void getData(String chatId) {


        FirebaseDatabase.getInstance().getReference("chats")
                .child(chatId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<MessageModel> list=new ArrayList<>();
                        if(snapshot.exists())
                        {
                            for (DataSnapshot d : snapshot.getChildren()) {



                                MessageModel data = d.getValue(MessageModel.class);

                                  list.add(data);


                            }
                        }
                        ChatAdapter ad=new ChatAdapter(MessageActivity.this,list);
                        binding.recyclerView2.setAdapter(ad);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MessageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private String senderId;
    private String chatId ;


    private void verifyChatId() {

        senderId= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        String receiverId= getIntent().getStringExtra("userId");

         chatId=senderId+receiverId;
        String reverseChatId=receiverId+senderId;


        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("chats");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(chatId))
                {
                    getData(chatId);
                }
                else if(snapshot.hasChild(reverseChatId))
                {
                    chatId=reverseChatId;
                  getData(chatId);
                }



            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void storeData(String msg) {
        binding.message.setText(null);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("chats").child(chatId);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        String date=dateFormat.format(cal.getTime());

        DateFormat timeFormat = new SimpleDateFormat("HH:mm a");
        String time =timeFormat.format(cal.getTime());


        MessageModel data=new MessageModel(date,msg,senderId,time);




        ref.child(ref.push().getKey()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    binding.message.setText(null);
                    Toast.makeText(MessageActivity.this, "Message send", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(MessageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}