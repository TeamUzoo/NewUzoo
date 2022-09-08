package com.han.teamuzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.han.teamuzoo.R;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.MyPet;
import com.han.teamuzoo.model.MyPetList;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MyPetAdapter extends RecyclerView.Adapter<MyPetAdapter.ViewHolder> {

    Context context;
    List<MyPet> myPetList;

    public MyPetAdapter(Context context, List<MyPet> myPetList) {
        this.context = context;
        this.myPetList = myPetList;
    }

    @NonNull
    @Override
    public MyPetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mypet_row, parent, false);
        return new MyPetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPetAdapter.ViewHolder holder, int position) {
        MyPet myPet = myPetList.get(position);

        holder.txtPetName.setText(myPet.getPetName());

        Glide.with(context).load(Config.IMAGE_URL+myPet.getPetUrl() )
                .placeholder(R.drawable.ic_pet)
                .into(  holder.roundedImageView  );
    }

    @Override
    public int getItemCount() {
        return myPetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView txtPetName;
        RoundedImageView roundedImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);

            roundedImageView = itemView.findViewById(R.id.roundedImageView);
            txtPetName = itemView.findViewById(R.id.txtPetName);


        }
    }
}
