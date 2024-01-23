package com.n1akai.gamesstore.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.n1akai.gamesstore.models.Discount;
import com.n1akai.gamesstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DiscountAdapter extends FirebaseRecyclerAdapter<Discount ,DiscountAdapter.ViewHolder> {

    OnItemClickListener clickListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DiscountAdapter(@NonNull FirebaseRecyclerOptions<Discount> options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, Discount discount) {
        holder.bind(discount);
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
            this.discount.setText("-"+Double.parseDouble(discount.getDiscount())*100+"%");
            String nPrice = String.format("%.2f", (Double.parseDouble(discount.getPrice()) * (1-Double.parseDouble(discount.getDiscount()))));
            newPrice.setText("$"+nPrice);
            CircularProgressDrawable cpd = new CircularProgressDrawable(itemView.getContext());
            cpd.setStrokeWidth(5f);
            cpd.setCenterRadius(30f);
            cpd.start();
            Picasso.get().load(discount.getImg()).placeholder(cpd).into(img);
            Log.d("MYTAG", discount.getTitle());
        }
    }
}
