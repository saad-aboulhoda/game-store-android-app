package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.n1akai.gamesstore.models.Genre;
import com.n1akai.gamesstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    LinkedHashMap<String, Genre> genres;

    public GenreAdapter(LinkedHashMap<String, Genre> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList list = new ArrayList(genres.keySet());
        Genre genre = genres.get(list.get(position));
        holder.title.setText(genre.getTitle());
        Picasso.get().load(genre.getImgUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.genre_title);
            img = itemView.findViewById(R.id.genre_img);
        }
    }
}
