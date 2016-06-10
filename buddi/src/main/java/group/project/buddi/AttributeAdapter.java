package group.project.buddi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Class to manage priority attribute ranking
 *
 * @author Team Buddi
 * @version 1.0
 */
public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.RecyclerViewHolder> {

    // Initialize variables
    private static List<String> attributes;

    /**
     * Constructor
     *
     * @param attributes the list of attributes to be ranked
     */
    public AttributeAdapter(List<String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Get the number of attributes
     *
     * @return number of attributes
     */
    @Override
    public int getItemCount() {
        return attributes.size();
    }

    /**
     * Bind information to recycler view holder
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        String d = attributes.get(i);
        recyclerViewHolder.vAttribute.setText(d);

    }

    /**
     * Get attributes list
     *
     * @return attributes
     */
    public List<String> getAttributes() {
        return attributes;
    }

    /**
     * Action for when item is moved
     *
     * @param fromPosition initial position
     * @param toPosition   final position
     * @return boolean
     */
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(attributes, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(attributes, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * Inflate layout upon initialization
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.ranking_card, viewGroup, false);

        RecyclerViewHolder itemViewHolder = new RecyclerViewHolder(itemView);
        return itemViewHolder;
    }

    /**
     * Inner RecyclerViewHolder class
     */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        protected TextView vAttribute;

        public RecyclerViewHolder(View v) {
            super(v);
            vAttribute = (TextView) v.findViewById(R.id.attribute);
        }
    }
}