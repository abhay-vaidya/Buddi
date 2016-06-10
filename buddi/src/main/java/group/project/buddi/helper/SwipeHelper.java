package group.project.buddi.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import group.project.buddi.DogRecyclerAdapter;

/**
 * Helper class for handling the swiping of Dog results.
 * @author Abhay Vaidya
 * @version 1.0
 */
public class SwipeHelper extends ItemTouchHelper.SimpleCallback{

    DogRecyclerAdapter adapter;

    /**
     * Contstructor
     * @param dragDirs
     * @param swipeDirs
     */
    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    /** Constructor
     *
     * @param adapter
     */
    public SwipeHelper(DogRecyclerAdapter adapter) {
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
