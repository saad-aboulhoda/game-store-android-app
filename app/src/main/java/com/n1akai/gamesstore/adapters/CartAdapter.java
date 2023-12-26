package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.n1akai.gamesstore.models.Cart;
import com.n1akai.gamesstore.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    ArrayList<Cart> carts;

    public CartAdapter(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.bind(cart);
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, story, price;
        ImageView poster, plus, minus, trash;
        EditText amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cart_tv_title);
            story = itemView.findViewById(R.id.cart_tv_story);
            price = itemView.findViewById(R.id.cart_tv_price);
            poster = itemView.findViewById(R.id.cart_iv_poster);
            plus = itemView.findViewById(R.id.cart_iv_plus);
            minus = itemView.findViewById(R.id.cart_iv_minus);
            trash = itemView.findViewById(R.id.cart_iv_trash);
            amount = itemView.findViewById(R.id.cart_et_amount);
        }

        public void bind(Cart cart) {
            title.setText(cart.getTitle());
            story.setText(cart.getStory());
            price.setText("$"+cart.getPrice());
            poster.setImageResource(cart.getImg());
            amount.setText(""+cart.getAmount());
        }
    }
}
