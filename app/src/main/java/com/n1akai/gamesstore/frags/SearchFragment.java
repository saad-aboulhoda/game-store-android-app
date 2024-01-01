package com.n1akai.gamesstore.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.adapters.FilterAdapter;
import com.n1akai.gamesstore.models.Game;

public class SearchFragment extends Fragment {


    FilterAdapter adapter;
    RecyclerView rc;
    Query query;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc = view.findViewById(R.id.rc_search);

        EditText et = getActivity().findViewById(R.id.edit_text_main_search);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                processSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) 
            adapter.stopListening();

    }

    private void processSearch(String s) {
        query = FirebaseDatabase.getInstance().getReference("games").orderByChild("title").startAt(s).endAt(s+"\uf8ff");
        FirebaseRecyclerOptions<Game> options = new FirebaseRecyclerOptions.Builder<Game>()
                .setQuery(query, Game.class)
                .build();

        adapter = new FilterAdapter(options);
        adapter.startListening();
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        rc.setAdapter(adapter);
    }
}