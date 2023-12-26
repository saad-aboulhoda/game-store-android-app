package com.n1akai.gamesstore.adapters;

import android.view.View;

import com.n1akai.gamesstore.models.Game;

public interface OnCartClickListener {
    void onCartClick(Game game, View cart, View check);
}
