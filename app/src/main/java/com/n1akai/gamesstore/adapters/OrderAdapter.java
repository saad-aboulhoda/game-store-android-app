package com.n1akai.gamesstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.models.Game;
import com.n1akai.gamesstore.models.Order;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Order model) {
        holder.bindView(model);
        holder.itemView.setOnLongClickListener(v -> {
            if (mOnLongItemClickListener != null) {
                mOnLongItemClickListener.itemLongClicked(v, model);
            }

            return true;
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ref, totalPrice, orderDate;
        ImageView statusImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ref = itemView.findViewById(R.id.order_ref);
            totalPrice = itemView.findViewById(R.id.order_total_price);
            orderDate = itemView.findViewById(R.id.order_date);
            statusImg = itemView.findViewById(R.id.order_img_status);
        }

        public void bindView(Order order) {
            ref.setText("Ref: " + order.getRef());
            totalPrice.setText("Total pirce: " + order.getTotalPrice());
            orderDate.setText("Ordered at: " + formatedDate(order.getCreatedAt()));
            switch (order.getStatus()) {
                case Order.PENDING:
                    statusImg.setImageResource(R.drawable.s_pending);
                    break;
                case Order.COMPLETED:
                    statusImg.setImageResource(R.drawable.s_completed);
                    break;
                default:
                    statusImg.setImageResource(R.drawable.s_canceled);
            }
        }

        private String formatedDate(Long time) {
            Date date = new Date(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return simpleDateFormat.format(date);
        }
    }

    OnLongItemClickListener mOnLongItemClickListener;

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface OnLongItemClickListener {
        void itemLongClicked(View v, Order order);
    }

}
