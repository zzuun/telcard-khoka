package com.example.zunnorain.telcard_khokha.HistoryActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zunnorain.telcard_khokha.CartActivity.CartAdapter;
import com.example.zunnorain.telcard_khokha.Classes.HistoryItem;
import com.example.zunnorain.telcard_khokha.Classes.Item;
import com.example.zunnorain.telcard_khokha.R;

import java.util.List;

/**
 * Created by Zunnorain on 09/06/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<HistoryItem> list;
    private Context context;

    public HistoryAdapter(List<HistoryItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoryItem historyItem = list.get(position);
        holder.name.setText(historyItem.getComp_name());
        holder.logo.setImageResource(historyItem.getComp_logo());
        holder.description.setText(historyItem.getQty() + " cards of "+historyItem.getCat() + " rupees each");
        holder.price.setText(historyItem.getPrice() + "");
        holder.date.setText(historyItem.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price,date;
        public ImageView logo;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            price = (TextView) itemView.findViewById(R.id.price);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            date=(TextView)itemView.findViewById(R.id.date);
        }
    }
}
