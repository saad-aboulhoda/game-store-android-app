package com.n1akai.gamesstore.frags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.n1akai.gamesstore.adapters.CartAdapter;
import com.n1akai.gamesstore.models.Cart;
import com.n1akai.gamesstore.R;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    View v;
    ArrayList<Cart> carts;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cart, container, false);
        cartList();
        gamesCount();
        return v;
    }


    private void cartList() {
        carts = new ArrayList<>();
        carts.add(new Cart("Kaze and the Wild Masks", "Go on a journey as Kaze in this 90’s classics inspired platformer. When the Crystal Islands get cursed, Kaze needs to save her friend Hogo while facing enraged living vegetables. Find the Wild Masks to unleash the powers of the legendary guardians and master land, sky and sea.", 5.99, R.drawable.new_release_img_3, 1));
        RecyclerView rc = v.findViewById(R.id.rc_cart_list);
        CartAdapter adapter = new CartAdapter(carts);
        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(adapter);
    }

    private void gamesCount() {
        TextView counts = v.findViewById(R.id.cart_et_games_count);
        TextView totalGames = v.findViewById(R.id.cart_tv_total_games);
        int sum = 0;
        for (Cart cart: carts) {
            sum += cart.getAmount();
        }
        counts.setText(sum+" games");
        totalGames.setText(sum+" games");
    }

}