package com.han.teamuzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.han.teamuzoo.R;
import com.han.teamuzoo.model.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    Context context;
    List<Shop> shopList;

    public ShopAdapter(Context context, List<Shop> shopList) {
        this.context = context;
        this.shopList = shopList;
    }



    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_row, parent, false);
        return new ShopAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        Shop shop = shopList.get(position);

        holder.txtPrice.setText(""+shop.getCost());
        holder.intPetNameId.setText(shop.getPetName());
        holder.imgPetId.setImageResource(shop.getSpecies());
        holder.txtSpecies.setText(shop.getSpecies());

    }

    @Override
    public int getItemCount() { return shopList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView petCardView;
        TextView txtPrice;
        TextView intPetNameId;
        ImageView imgPetId;
        TextView txtSpecies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            petCardView = itemView.findViewById(R.id.petCardView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            intPetNameId = itemView.findViewById(R.id.PetNameId);
            imgPetId = itemView.findViewById(R.id.imgPetId);
            txtSpecies = itemView.findViewById(R.id.txtSpecies);

                }
            }

}

