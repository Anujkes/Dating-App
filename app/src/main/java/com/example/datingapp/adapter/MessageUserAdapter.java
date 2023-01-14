package com.example.datingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datingapp.R;
import com.example.datingapp.activity.MessageActivity;
import com.example.datingapp.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageUserAdapter extends RecyclerView.Adapter<MessageUserAdapter.ViewHolder>{

    private Context context;
    private ArrayList<String> list;
  private ArrayList<String> chatKey;


    public MessageUserAdapter() {
    }

    public MessageUserAdapter(Context context, ArrayList<String> list,ArrayList<String> chatKey) {
        this.context = context;
        this.list = list;
        this.chatKey=chatKey;
    }

    @NonNull
    @Override
    public MessageUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_item_layout, parent, false);
        return new MessageUserAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageUserAdapter.ViewHolder holder, int position) {

     FirebaseDatabase.getInstance().getReference("users")
                     .child(list.get(holder.getAdapterPosition())).addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     if(snapshot.exists())
                     {
                        UserModel data=snapshot.getValue(UserModel.class);


                         holder.name.setText(data.getName());

                         Glide.with(context).load(data.getImage()).placeholder(R.drawable.man).into(holder.image);





                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {
                     Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });


     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i=new Intent(context, MessageActivity.class);
             i.putExtra("chatId",chatKey.get(holder.getAdapterPosition()));
             i.putExtra("userId",list.get(holder.getAdapterPosition()));
             context.startActivity(i);
         }
     });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       ImageView image;
       TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
        }
    }
}
