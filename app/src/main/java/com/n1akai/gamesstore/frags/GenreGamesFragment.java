package com.n1akai.gamesstore.frags;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.adapters.FilterAdapter;
import com.n1akai.gamesstore.adapters.OnItemClickListener;
import com.n1akai.gamesstore.models.Game;
import com.n1akai.gamesstore.models.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreGamesFragment extends Fragment {


    FilterAdapter adapter;
    RecyclerView rc;
    List<Game> games;
    ProgressBar loadingCircular;

    public GenreGamesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre_games, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        games = new ArrayList<>();
        Genre theGenre = GenreGamesFragmentArgs.fromBundle(getArguments()).getGenre();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("games");
        mRef.orderByChild("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadingCircular.setVisibility(View.GONE);
                games.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Game game = s.getValue(Game.class);
                    for (Genre genre: game.getGenres()) {
                        if (genre.getId().equals(theGenre.getId())) {
                            games.add(game);
                            break;
                        }
                    }

                }
                adapter = new FilterAdapter(games);
                rc.setLayoutManager(new LinearLayoutManager(getContext()));
                rc.setAdapter(adapter);
                adapter.setOnClickGameListener(game -> navigateToGameDetail(game));
                if (games.size() == 0) {
                    Toast.makeText(getContext(), getResources().getString(R.string.zero_game_found), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        rc = getView().findViewById(R.id.rc_games);
        loadingCircular = getView().findViewById(R.id.loading_circular);
    }

    private void navigateToGameDetail(Game game) {
        NavDirections action = GenreGamesFragmentDirections.actionGenreGamesFragmentToGameDetailFragment(game, game.getTitle());
        Navigation.findNavController(getView()).navigate(action);
    }
}