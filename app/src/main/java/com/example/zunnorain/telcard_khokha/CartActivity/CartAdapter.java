package com.example.zunnorain.telcard_khokha.CartActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zunnorain.telcard_khokha.Classes.Item;
import com.example.zunnorain.telcard_khokha.R;

import java.util.List;

/**
 * Created by Zunnorain on 03/06/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private List<Item> list;

    public CartAdapter(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {

        Item item = list.get(position);
        holder.name.setText(item.getComp_name());
        holder.logo.setImageResource(item.getComp_logo());
        holder.description.setText(item.getQty() + " cards of "+ item.getCat() + " rupees each");
        holder.price.setText(item.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Item item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name, description, price;
        public ImageView logo;
        public RelativeLayout view_background, view_foreground;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            price = (TextView) itemView.findViewById(R.id.price);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            view_background = (RelativeLayout) itemView.findViewById(R.id.view_background);
            view_foreground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);

        }
    }
}