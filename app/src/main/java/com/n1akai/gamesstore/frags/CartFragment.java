package com.n1akai.gamesstore.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.n1akai.gamesstore.adapters.CartAdapter;
import com.n1akai.gamesstore.models.CartItem;
import com.n1akai.gamesstore.R;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    View v;
    TextView counts, totalGames, subTotal;
    ArrayList<CartItem> cartItems;
    FirebaseUser user;
    DatabaseReference mRef;
    NavController navController;
    int allGamesCount = 0;
    Double subTotalPrice = 0.0;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cart, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(v);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mRef = FirebaseDatabase.getInstance().getReference("carts").child(user.getUid());
            cartList();
            initView();
        } else {
            Toast.makeText(getContext(), "You have to login first", Toast.LENGTH_SHORT).show();
            navigateToUserLogin();
        }
    }

    private void initView() {
        counts = v.findViewById(R.id.cart_et_games_count);
        totalGames = v.findViewById(R.id.cart_tv_total_games);
        subTotal = v.findViewById(R.id.cart_tv_sub_total);
    }

    private void cartList() {
        cartItems = new ArrayList<>();
        RecyclerView rc = v.findViewById(R.id.rc_cart_list);
        CartAdapter adapter = new CartAdapter(cartItems);
        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(adapter);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s: snapshot.getChildren()) {
                    CartItem cartItem = s.getValue(CartItem.class);
                    cartItems.add(cartItem);
                    manageCount(cartItem);
                    manageSubTotalPrice(cartItem);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void manageCount(CartItem cartItem) {
        allGamesCount += cartItem.getAmount();
        counts.setText(allGamesCount+" games");
        totalGames.setText(allGamesCount+" games");
    }

    private void manageSubTotalPrice(CartItem cartItem) {
        subTotalPrice += Double.parseDouble(cartItem.getPrice());
        String subTotalPriceFormated = String.format("%.2f", subTotalPrice);
        subTotal.setText("$"+subTotalPriceFormated);
    }

    private void navigateToUserLogin() {
        NavDirections action = CartFragmentDirections.actionGlobalUserFragment();
        navController.navigate(action);
    }

}