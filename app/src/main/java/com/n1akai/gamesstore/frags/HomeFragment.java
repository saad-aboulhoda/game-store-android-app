package com.n1akai.gamesstore.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.n1akai.gamesstore.adapters.DiscountAdapter;
import com.n1akai.gamesstore.adapters.GenreAdapter;
import com.n1akai.gamesstore.adapters.GameAdapter;
import com.n1akai.gamesstore.adapters.OnItemClickListener;
import com.n1akai.gamesstore.models.Discount;
import com.n1akai.gamesstore.models.Genre;
import com.n1akai.gamesstore.models.Game;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.viewmodels.HomeFragmentViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class HomeFragment extends Fragment {

    View v;
    ImageSlider imageSlider;
    ArrayList<SlideModel> slideImgs;
    RecyclerView genresRV, discountsRV, newReleasesRV;
    NavController navController;
    LinkedHashMap<String, Genre> genres;
    GenreAdapter adapter;
    ChildEventListener gamesListener, genresListener;
    LinkedHashMap<String, Game> latestGames;
    GameAdapter gameAdapter;


    DatabaseReference genresDR, gamesDR;

    HomeFragmentViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;
        navController = Navigation.findNavController(v);
        genresDR = FirebaseDatabase.getInstance().getReference("genres");
        gamesDR = FirebaseDatabase.getInstance().getReference("games");
        initView();
        //searchBar();
        sliderImage();
        genres();
        discounts();
        latestGames();
    }

    public void initView() {
        imageSlider = v.findViewById(R.id.image_slider);
        genresRV = v.findViewById(R.id.recycler_view_genres);
        discountsRV = v.findViewById(R.id.recycler_view_discounts);
        newReleasesRV = v.findViewById(R.id.recycler_view_new_releases);
    }

    public void sliderImage() {
        viewModel.sliderImage();
        slideImgs = viewModel.getSlideImgs();
        imageSlider.setImageList(slideImgs);
    }

    private void genres() {
        genres = new LinkedHashMap<>();
        genresListener();
        adapter = new GenreAdapter(genres);
        genresRV.setHasFixedSize(true);
        genresRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        genresRV.setAdapter(adapter);
    }

    private void genresListener() {
        genresListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Genre genre = snapshot.getValue(Genre.class);
                genres.put(genre.getId(), genre);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Genre genre = snapshot.getValue(Genre.class);
                genres.put(genre.getId(), genre);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                genres.remove(snapshot.getValue(Genre.class).getId());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        genresDR.addChildEventListener(genresListener);


    }

    private void discounts() {
        ArrayList<Discount> discounts = new ArrayList<>();
        discounts.add(new Discount("Shattered: Tale of the Forgotten King", R.drawable.discount_img_1, 29.99, 0.8));
        discounts.add(new Discount("Beacon Pines", R.drawable.discount_img_2, 19.99, 0.4));
        discounts.add(new Discount("SCARF", R.drawable.discount_img_3, 14.99, 0.5));
        DiscountAdapter adapter = new DiscountAdapter(discounts);

        discountsRV.setHasFixedSize(true);
        discountsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        discountsRV.setAdapter(adapter);
    }

    private void latestGames() {
        latestGames = new LinkedHashMap<>();
        gamesListener();
        gameAdapter = new GameAdapter(latestGames);
        gameAdapter.setOnGameClickListener(game -> {
            navigateToGameDetail(game);
        });
        newReleasesRV.setHasFixedSize(true);
        newReleasesRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        newReleasesRV.setAdapter(gameAdapter);
    }

    private void gamesListener() {
        gamesListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Game game = snapshot.getValue(Game.class);
                latestGames.put(game.getId(), game);
                gameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Game game = snapshot.getValue(Game.class);
                latestGames.put(game.getId(), game);
                gameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                latestGames.remove(snapshot.getValue(Game.class).getId());
                gameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        gamesDR.addChildEventListener(gamesListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gamesDR.removeEventListener(gamesListener);
        genresDR.removeEventListener(genresListener);
    }

    private void navigateToGameDetail(Game game) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToGameDetailFragment(game, game.getTitle());
        navController.navigate(action);
    }
}