package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.n1akai.gamesstore.models.Genre;
import com.n1akai.gamesstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class GenreAdapter extends FirebaseRecyclerAdapter<Genre, GenreAdapter.ViewHolder> {


    public GenreAdapter(@NonNull FirebaseRecyclerOptions<Genre> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, Genre genre) {
        holder.title.setText(genre.getTitle());
        CircularProgressDrawable cpd = new CircularProgressDrawable(holder.itemView.getContext());
        cpd.setStrokeWidth(5f);
        cpd.setCenterRadius(30f);
        cpd.start();
        Glide.with(holder.itemView)
                .load(genre.getImgUrl())
                .placeholder(cpd)
                .into(holder.img);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.genre_title);
            img = itemView.findViewById(R.id.genre_img);
        }
    }
}
