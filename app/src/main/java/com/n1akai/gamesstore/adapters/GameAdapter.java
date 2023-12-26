package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.n1akai.gamesstore.models.Game;
import com.n1akai.gamesstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    LinkedHashMap<String, Game> latestGames;
    OnItemClickListener clickListener;
    OnCartClickListener cartClickListener;

    public GameAdapter(LinkedHashMap<String, Game> latestGames) {
        this.latestGames = latestGames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_release_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(latestGames.keySet());
        Game game = latestGames.get(keys.get(position));
        if (game != null) {
            holder.title.setText(game.getTitle());
            String price = "$"+game.getPrice();
            holder.price.setText(price);
            holder.itemView.setOnClickListener(v -> {
                clickListener.onGameClick(game);
            });
            holder.cartBtn.setOnClickListener(v -> cartClickListener.onCartClick(game, ((Button) v), holder.check));
            Picasso.get().load(game.getPosterUrl()).into(holder.img);
        }

    }

    @Override
    public int getItemCount() {
        return latestGames.size();
    }

    public void setOnGameClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnCartClickListener(OnCartClickListener cartClickListener) {
        this.cartClickListener = cartClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price;
        ImageView img;
        Button cartBtn, check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_new_release_title);
            img = itemView.findViewById(R.id.image_view_new_release_img);
            price = itemView.findViewById(R.id.text_view_new_release_price);
            cartBtn = itemView.findViewById(R.id.button_new_release_cart);
            check = itemView.findViewById(R.id.button_new_release_valid);
        }
    }
}
