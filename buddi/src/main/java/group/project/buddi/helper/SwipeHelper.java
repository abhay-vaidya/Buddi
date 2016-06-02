package group.project.buddi.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import group.project.buddi.DataAdapter;

/**
 * Created by Abhay on 15/05/2016.
 */
public class SwipeHelper extends ItemTouchHelper.SimpleCallback{


    DataAdapter adapter;


    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelper(DataAdapter adapter) {
        super(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT );
        this.adapter = adapter;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
      adapter.dismissPet(viewHolder.getAdapterPosition());
      adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

    }
}
