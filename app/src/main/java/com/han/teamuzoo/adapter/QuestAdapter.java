package com.han.teamuzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.han.teamuzoo.R;
import com.han.teamuzoo.model.Quest;

import java.util.List;

public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.ViewHolder> {

    Context context;
    List<Quest> questList;

    public QuestAdapter(Context context, List<Quest> questList) {
        this.context = context;
        this.questList = questList;
    }

    @NonNull
    @Override
    public QuestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestAdapter.ViewHolder holder, int position) {
        Quest quest = questList.get(position);

        holder.txtTitle.setText(quest.getTitle());

    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtTitle;
        TextView txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);


        }
    }
}