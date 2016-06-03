package group.project.buddi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Abhay on 02/06/2016.
 */
public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.RecyclerViewHolder> {
    private static List<String> attributes;

    public AttributeAdapter(List<String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        String d = attributes.get(i);
        recyclerViewHolder.vAttribute.setText(d);

    }

    public List<String> getAttributes() { return attributes; }

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

    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.ranking_card, viewGroup, false);

        RecyclerViewHolder itemViewHolder = new RecyclerViewHolder(itemView);
        return itemViewHolder;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        protected TextView vAttribute;

        public RecyclerViewHolder(View v) {
            super(v);
            vAttribute = (TextView) v.findViewById(R.id.attribute);
        }
    }


}