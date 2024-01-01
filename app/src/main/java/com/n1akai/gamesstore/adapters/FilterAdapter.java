package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.models.Game;
import com.squareup.picasso.Picasso;

public class FilterAdapter extends FirebaseRecyclerAdapter<Game, FilterAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FilterAdapter(@NonNull FirebaseRecyclerOptions<Game> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Game model) {
        holder.bindView(model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_layout, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, genres;
        ImageView poster, platforms;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_poster);
            title = itemView.findViewById(R.id.tv_title);
            genres = itemView.findViewById(R.id.tv_genres);
            platforms = itemView.findViewById(R.id.iv_platforms);
        }

        public void bindView(Game game) {
            Picasso.get().load(game.getPosterUrl()).into(poster);
            title.setText(game.getTitle());
            if (game.getPlatforms().toLowerCase().contains("windows")) {
                platforms.setImageResource(R.drawable.ic_windows);
            }
        }
    }
}
