package com.n1akai.gamesstore.frags;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.models.CartItem;
import com.n1akai.gamesstore.models.Game;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameDetailFragment extends Fragment {

    View view;
    Game game;
    DatabaseReference cartItemDR = null;
    FirebaseUser user;
    NavController navController;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        navController = Navigation.findNavController(view);
        game = GameDetailFragmentArgs.fromBundle(getArguments()).getGame();
        initView();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            cartItemDR = FirebaseDatabase.getInstance().getReference("carts").child(user.getUid()).child(game.getId());
        }

        setupImageSlider();
    }

    private void initView() {
        ((TextView) view.findViewById(R.id.detail_tv_title)).setText(game.getTitle());
        ((TextView) view.findViewById(R.id.detail_tv_price)).setText("$"+game.getPrice());
        ((TextView) view.findViewById(R.id.detail_tv_developer)).setText(game.getDeveloper());
        ((TextView) view.findViewById(R.id.detail_tv_publisher)).setText(game.getPublisher());
        ((TextView) view.findViewById(R.id.detail_tv_platform)).setText(game.getPlatforms());
        ((TextView) view.findViewById(R.id.detail_tv_description)).setText(game.getDescription());
        ((TextView) view.findViewById(R.id.detail_tv_release_date)).setText(formatedDate(game.getReleaseDate()));
        ((Button) view.findViewById(R.id.detail_button_add_to_cart)).setOnClickListener(v -> addToCart(game, (Button) v));
        Picasso.get().load(game.getThumbnailUrl()).into(((ImageView) view.findViewById(R.id.detail_iv_thumbnail)));
    }

    private void addToCart(Game game, Button cartBtn) {
        if (cartItemDR == null) {
            navigateToUserLogin();
            return;
        }
        cartItemDR.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                CartItem cartItem = task.getResult().getValue(CartItem.class);
                if (cartItem != null)
                    cartItem.plus();
                else {
                    cartItem = new CartItem(game.getId(), game.getTitle(), game.getDescription(), game.getPrice(), game.getPosterUrl());
                }
                cartItemDR.setValue(cartItem).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        cartBtn.setText("Added");
                        cartBtn.setEnabled(false);
                        Toast.makeText(getContext(), "Added Successfuly!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setupImageSlider() {
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        for (String imgUrl: game.getImages())
            slideModels.add(new SlideModel(imgUrl, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
    }

    private String formatedDate(Long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return simpleDateFormat.format(date);
    }

    private void navigateToUserLogin() {
        NavDirections action = GameDetailFragmentDirections.actionGlobalUserFragment();
        navController.navigate(action);
    }
}