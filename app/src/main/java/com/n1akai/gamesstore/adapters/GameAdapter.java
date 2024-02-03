package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.n1akai.gamesstore.models.Game;
import com.n1akai.gamesstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GameAdapter extends FirebaseRecyclerAdapter<Game, GameAdapter.ViewHolder> {

    OnItemClickListener clickListener;
    OnCartBtnClickListener cartClickListener;


    public GameAdapter(@NonNull FirebaseRecyclerOptions<Game> options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_release_layout, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Game game) {
        holder.bind(game);
        holder.itemView.setOnClickListener(v -> {
            clickListener.onGameClick(game);
        });
        holder.cartBtn.setOnClickListener(v -> cartClickListener.onCartClick(game, ((Button) v), holder.check));
    }

    public void setOnGameClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnCartClickListener(OnCartBtnClickListener cartClickListener) {
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

        public void bind(Game game) {
            title.setText(game.getTitle());
            String price = "$"+game.getPrice();
            this.price.setText(price);
            CircularProgressDrawable cpd = new CircularProgressDrawable(itemView.getContext());
            cpd.setStrokeWidth(5f);
            cpd.setCenterRadius(30f);
            cpd.start();
            Picasso.get().load(game.getPosterUrl())
                    .placeholder(cpd)
                    .into(img);
        }
    }
}
