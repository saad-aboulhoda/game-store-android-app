package com.n1akai.gamesstore.adapters;

import android.view.View;
import android.widget.Button;

import com.n1akai.gamesstore.models.Game;

public interface OnCartClickListener {
    void onCartClick(Game game, Button cart, Button check);
}
