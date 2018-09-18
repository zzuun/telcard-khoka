package com.example.zunnorain.telcard_khokha.CartActivity;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by Zunnorain on 03/06/2018.
 */

public class CartRecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private CartRecyclerItemTouchHelperListner listener;
    public CartRecyclerItemTouchHelper(int dragDirs, int swipeDirs , CartRecyclerItemTouchHelperListner listener) {
        super(dragDirs, swipeDirs);
        this.listener=listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        if(listener!=null)
        {
            listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder!=null)
        {
            View foregroundview =((CartAdapter.MyViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onSelected(foregroundview);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        View foregroundview =((CartAdapter.MyViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().clearView(foregroundview);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundview =((CartAdapter.MyViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundview,dX,dY,actionState,isCurrentlyActive);

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundview =((CartAdapter.MyViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundview,dX,dY,actionState,isCurrentlyActive);

    }
}


