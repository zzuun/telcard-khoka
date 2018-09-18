package com.example.zunnorain.telcard_khokha.CartActivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.zunnorain.telcard_khokha.BankDetailsActivity.BankDeatailsActivity;
import com.example.zunnorain.telcard_khokha.Classes.Item;
import com.example.zunnorain.telcard_khokha.DBHandler.DBHandler;
import com.example.zunnorain.telcard_khokha.LoginSignup.LoginActivity;
import com.example.zunnorain.telcard_khokha.R;
import com.example.zunnorain.telcard_khokha.Session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartRecyclerItemTouchHelperListner, View.OnClickListener {

    private TextView totalPriceFotter;
    private LinearLayout procede_button;

    private RecyclerView recyclerView;
    private RelativeLayout coordinatorLayout;
    DBHandler dbHandler= new DBHandler(CartActivity.this);
    SessionManager sessionManager;

    private List<Item> listItems ;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalPriceFotter=(TextView)findViewById(R.id.total_price);
        procede_button=(LinearLayout) findViewById(R.id.buy_prcedure_button);
        sessionManager= new SessionManager(getApplicationContext());

        listItems=new ArrayList<>();
        listItems.clear();
        listItems=dbHandler.fetchAllItems();
        recyclerView=(RecyclerView)findViewById(R.id.order_recyclerview);
        coordinatorLayout=(RelativeLayout) findViewById(R.id.rootlayout);
        adapter= new CartAdapter( this , listItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        updatePrice(listItems);

        ItemTouchHelper.SimpleCallback itemTouchHelper = new CartRecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        procede_button.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        listItems =dbHandler.fetchAllItems();
        adapter.notifyDataSetChanged();

    }

    private void updatePrice(List<Item> listItems) {

        double totalPrice=0.0;
        for (Item item : listItems)
        {
            totalPrice+=item.getPrice();
        }
        totalPriceFotter.setText(""+totalPrice);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof CartAdapter.MyViewHolder) {

            String name = listItems.get(viewHolder.getAdapterPosition()).getComp_name();
            final Item deleteditem = listItems.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            dbHandler.deleteItem(deleteditem.getId());
            adapter.removeItem(deleteIndex);
            updatePrice(listItems);

            Snackbar snackbar = Snackbar.make(coordinatorLayout, name + " deleted", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHandler.addRestoreItem(deleteditem);
                    adapter.restoreItem(deleteditem, deleteIndex);
                    updatePrice(listItems);

                }

            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }

    }


    private void addItemToCart() {

        listItems =dbHandler.fetchAllItems();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
       int i = v.getId();
        if(i==R.id.buy_prcedure_button)
        {
            if(sessionManager.isLoggedIn())
            {
                startActivity(new Intent(CartActivity.this, BankDeatailsActivity.class));
            }
            else
            {
                startActivity(new Intent(CartActivity.this, LoginActivity.class));

            }
        }
    }
}
