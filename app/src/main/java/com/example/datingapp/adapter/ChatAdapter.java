package com.example.datingapp.adapter;

// message adapter is same as chat adapter //

import android.content.Context;
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
import com.example.datingapp.model.MessageModel;
import com.example.datingapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

   private Context context;
   private ArrayList<MessageModel> list;

   private int MSG_TYPE_RIGHT=1;
   private int MSG_TYPE_LEFT=0;

    public ChatAdapter() {
    }

    public ChatAdapter(Context context, ArrayList<MessageModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if( (list.get(position).getSenderId().toString()).equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString()))
            return MSG_TYPE_RIGHT;//1
        else
            return MSG_TYPE_LEFT;//0
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       if(viewType==MSG_TYPE_RIGHT)
        {
            View view1= LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            ViewHolder vh1 = new ViewHolder(view1);
           return vh1;
        }
       else
       {
           View view2= LayoutInflater.from(context).inflate(R.layout.receiver_layout,parent,false);
           ViewHolder vh2 = new ViewHolder(view2);
           return vh2;
       }



    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {


      holder.messageText.setText(list.get(position).getMessage());

        FirebaseDatabase.getInstance().getReference("users")
                .child(list.get(position).getSenderId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if(snapshot.exists())
                        {
                            UserModel data=snapshot.getValue(UserModel.class);

                            Glide.with(context).load(data.getImage()).placeholder(R.drawable.man).into(holder.image);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });









    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView messageText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            messageText=itemView.findViewById(R.id.messageText);
        }
    }
}
