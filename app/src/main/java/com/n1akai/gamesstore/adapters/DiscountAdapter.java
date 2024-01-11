package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.n1akai.gamesstore.models.Discount;
import com.n1akai.gamesstore.R;

import java.util.ArrayList;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {

    ArrayList<Discount> discounts;
    OnItemClickListener clickListener;

    public DiscountAdapter(ArrayList<Discount> discounts) {
        this.discounts = discounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discount discount = discounts.get(position);
        holder.bind(discount);
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public void setOnGameClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, discount, newPrice;
        ImageView img;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.text_view_title);
            img = itemView.findViewById(R.id.imgae_view_img);
            price = itemView.findViewById(R.id.text_view_price);
            discount = itemView.findViewById(R.id.text_view_discount_perc);
            newPrice = itemView.findViewById(R.id.text_view_discount_new_price);
        }

        public void bind(Discount discount) {
            title.setText(discount.getTitle());
            price.setText("$"+discount.getPrice());
            this.discount.setText("-"+discount.getDiscount()*100+"%");
            String nPrice = String.format("%.2f", (discount.getPrice() * (1-discount.getDiscount())));
            newPrice.setText("$"+nPrice);
            img.setImageResource(discount.getImg());
//            itemView.setOnClickListener(v -> {
//                clickListener.onGameClick();
//            });
        }
    }
}
