package com.n1akai.gamesstore.adapters;

import android.widget.Button;

import com.n1akai.gamesstore.models.Game;

public interface OnCartBtnClickListener {
    void onCartClick(Game game, Button cart, Button check);
}
