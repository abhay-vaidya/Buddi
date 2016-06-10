package group.project.buddi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import group.project.buddi.model.Dog;

/**
 * Class to handle list of dogs
 *
 * @author Team Buddi
 * @version 1.0
 */
public class DogRecyclerAdapter extends RecyclerView.Adapter<DogRecyclerAdapter.RecyclerViewHolder> {

    // Initialize variables
    private static List<Dog> m_dogList;
    private Context m_context;

    /**
     * Constructor
     *
     * @param context context of activity
     * @param dogList list of dogs
     */
    public DogRecyclerAdapter(Context context, List<Dog> dogList) {
        m_dogList = dogList;
        m_context = context;
    }

    /**
     * Get size of dog list
     *
     * @return size of dog list
     */
    @Override
    public int getItemCount() {
        return m_dogList.size();
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, int i) {

        // Bind information to holder
        final Dog d = m_dogList.get(recyclerViewHolder.getAdapterPosition());
        recyclerViewHolder.vName.setText(d.getName());
        recyclerViewHolder.vAge.setText(String.valueOf(d.getAge()) + " years old");
        recyclerViewHolder.vBreed.setText(d.getBreed());
        Picasso.with(m_context).load(d.getImageURL()).into(recyclerViewHolder.vImage);

        // Open details screen when button pressed
        recyclerViewHolder.vButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("pet_id", d.getID());
                m_context.startActivity(intent);
                ((Activity) m_context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
    }

    /**
     * Inner class to handle item view
     *
     * @param viewGroup viewGroup
     * @param i         view type
     * @return itemViewHolder
     */
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardlist_layout, viewGroup, false);

        RecyclerViewHolder itemViewHolder = new RecyclerViewHolder(itemView);
        return itemViewHolder;
    }

    /**
     * Remove pet from list when swiped away
     *
     * @param pos the position of the pet
     */
    public void dismissPet(int pos) {
        m_dogList.remove(pos);
        this.notifyItemRemoved(pos);
    }

    /**
     * ViewHolder class to bind layout to data
     */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // Initialize variables
        protected TextView vName;
        protected TextView vAge;
        protected TextView vBreed;
        protected ImageView vImage;
        protected ImageButton vButton;

        // Bind items
        public RecyclerViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.petName);
            vAge = (TextView) v.findViewById(R.id.petAge);
            vBreed = (TextView) v.findViewById(R.id.petBreed);
            vImage = (ImageView) v.findViewById(R.id.imageView);
            vButton = (ImageButton) v.findViewById(R.id.detailsButton);
        }
    }

    /**
     * Swap two dogs
     *
     * @param list
     */
    public void swap(List list) {
        if (m_dogList != null) {
            m_dogList.clear();
            m_dogList.addAll(list);
        } else {
            m_dogList = list;
        }
        notifyDataSetChanged();
    }

    /**
     * Class to handle the details button
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton detailsButton;

        public ViewHolder(View itemView) {
            super(itemView);
            detailsButton = (ImageButton) itemView.findViewById(R.id.detailsButton);

            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }
    }

    /**
     * Method to load image from online
     *
     * @param url URL of image
     * @return a drawable type image
     */
    private Drawable loadImageFromWeb(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}


