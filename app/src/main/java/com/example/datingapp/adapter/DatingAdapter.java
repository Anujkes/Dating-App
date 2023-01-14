package com.example.datingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datingapp.R;
import com.example.datingapp.activity.MessageActivity;
import com.example.datingapp.databinding.FragmentDatingBinding;
import com.example.datingapp.databinding.ItemUserLayoutBinding;
import com.example.datingapp.model.UserModel;

import java.util.ArrayList;

public class DatingAdapter extends RecyclerView.Adapter<DatingAdapter.ViewHolder> {


    private Context context;
     private ArrayList<UserModel> list;

    public DatingAdapter() {
    }

    public DatingAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_user_layout, parent, false);
        return new DatingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatingAdapter.ViewHolder holder, int position) {
    UserModel data=list.get(holder.getAdapterPosition());

    holder.userName.setText(list.get(holder.getAdapterPosition()).getName());
    holder.userEmail.setText(list.get(position).getEmail());

      Glide.
         with(context)
              .load(list.get(holder.getAdapterPosition()).getImage())
                 .into(holder.userImage);


      holder.chat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(context,MessageActivity.class);
              intent.putExtra("userId",list.get(holder.getAdapterPosition()).getNumber());

              context.startActivity(intent);

          }
      });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

  ImageView userImage,videoCall,chat,fav;
       TextView userName,userEmail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage=itemView.findViewById(R.id.userImage);
            userEmail=itemView.findViewById(R.id.userEmail);
          userName=itemView.findViewById(R.id.userName);
            videoCall=itemView.findViewById(R.id.videoCall);
            chat=itemView.findViewById(R.id.chat);
             fav=itemView.findViewById(R.id.fav);




        }
    }
}
