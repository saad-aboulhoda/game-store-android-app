package com.n1akai.gamesstore.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.adapters.FilterAdapter;
import com.n1akai.gamesstore.adapters.OrderAdapter;
import com.n1akai.gamesstore.models.Game;
import com.n1akai.gamesstore.models.Order;


public class OrdersFragment extends Fragment {

    OrderAdapter adapter;
    FirebaseUser user;
    RecyclerView rc;
    Query query;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc = view.findViewById(R.id.orders_rc);
        user = FirebaseAuth.getInstance().getCurrentUser();
        query = FirebaseDatabase.getInstance().getReference("orders").child(user.getUid()).orderByChild("createdAt");
        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        adapter = new OrderAdapter(options);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        rc.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}