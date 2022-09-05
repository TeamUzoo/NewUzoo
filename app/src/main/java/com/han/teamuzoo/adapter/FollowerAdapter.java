package com.han.teamuzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.han.teamuzoo.R;
import com.han.teamuzoo.model.Follower;

import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {

    Context context;
    List<Follower> followList;

    public FollowerAdapter(Context context, List<Follower> followList) {
        this.context = context;
        this.followList = followList;
    }

    @NonNull
    @Override
    public FollowerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.follower_row, parent, false);
        return new FollowerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerAdapter.ViewHolder holder, int position) {
        Follower follow = followList.get(position);

        holder.txtSuc.setText(""+follow.getSuccessed());
        holder.txtDea.setText(""+follow.getFailed());
        holder.txtName.setText(follow.getName());

    }

    @Override
    public int getItemCount() { return followList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView folRecyclerView;
        TextView txtSuc;
        TextView txtDea;
        TextView txtName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folRecyclerView = itemView.findViewById(R.id.folRecyclerView);
            txtSuc = itemView.findViewById(R.id.txtSuc);
            txtDea = itemView.findViewById(R.id.txtDea);
            txtName = itemView.findViewById(R.id.txtName);



        }
    }
}