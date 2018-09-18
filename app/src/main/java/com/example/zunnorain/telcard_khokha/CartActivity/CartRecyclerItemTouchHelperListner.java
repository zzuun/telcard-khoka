package com.example.zunnorain.telcard_khokha.CartActivity;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Zunnorain on 03/06/2018.
 */

interface CartRecyclerItemTouchHelperListner  {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction , int position);

}
