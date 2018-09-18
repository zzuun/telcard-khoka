package com.example.zunnorain.telcard_khokha.OperatorActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zunnorain.telcard_khokha.Classes.OperatorClass;
import com.example.zunnorain.telcard_khokha.R;

import java.util.List;

/**
 * Created by Zunnorain on 03/06/2018.
 */

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.MyViewHolder> {

    private Activity activity;
    private List<OperatorClass> arr;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView item_list;
        private TextView comp_name;
        private ImageView comp_logo;
        public MyViewHolder(View view) {
            super(view);
            comp_name = (TextView) view.findViewById(R.id.comp_name);
            comp_logo = (ImageView) view.findViewById(R.id.comp_logo);
            item_list =(CardView) view.findViewById(R.id.item_list);
        }
    }

    public OperatorAdapter(List<OperatorClass> arr, Context context, Activity activity) {
        this.arr = arr;
        this.context=context;
        this.activity=activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list_item, parent, false);

        final MyViewHolder myViewHolder = new MyViewHolder(itemView);

        myViewHolder.item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backFunction(arr.get(myViewHolder.getAdapterPosition()).getName(),arr.get(myViewHolder.getAdapterPosition()).getLogo());
            }
        });
        return  myViewHolder;
    }

    private void backFunction(String name, int logo) {


        Intent intent= new Intent();
        intent.putExtra("operatorName",name);
        intent.putExtra("operatorLogo",logo);
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        OperatorClass c = arr.get(position);
        holder.comp_logo.setImageResource(c.getLogo());
        holder.comp_name.setText(c.getName());

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

}
