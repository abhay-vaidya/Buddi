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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import group.project.buddi.model.Dog;

/**
 * Created by Abhay on 10/05/2016.
 */
public class DogRecylerAdapter extends RecyclerView.Adapter<DogRecylerAdapter.RecyclerViewHolder> {

    private static List<Dog> m_dogList;
    private Context m_context;

    private boolean test = false;

    public DogRecylerAdapter(Context context, List<Dog> dogList) {

        m_dogList = dogList;
        m_context = context;
    }



    @Override
    public int getItemCount() {
        return m_dogList.size();
    }



    @Override
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, int i) {
        final Dog d = m_dogList.get(recyclerViewHolder.getAdapterPosition());
        recyclerViewHolder.vName.setText( d.getName() );
        recyclerViewHolder.vAge.setText( String.valueOf(d.getAge()) + " years old" );
        recyclerViewHolder.vBreed.setText( d.getBreed() );

        Picasso.with(m_context).load(d.getImageURL()).into(recyclerViewHolder.vImage);


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

    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardlist_layout, viewGroup, false);

        RecyclerViewHolder itemViewHolder = new RecyclerViewHolder(itemView);
        return itemViewHolder;
    }

    public void dismissPet(int pos){
        m_dogList.remove(pos);
        this.notifyItemRemoved(pos);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vAge;
        protected TextView vBreed;
        protected ImageView vImage;
        protected ImageButton vButton;

        public RecyclerViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.petName);
            vAge = (TextView) v.findViewById(R.id.petAge);
            vBreed= (TextView) v.findViewById(R.id.petBreed);
            vImage = (ImageView) v.findViewById(R.id.imageView);
            vButton = (ImageButton) v.findViewById(R.id.detailsButton);
        }
    }


    public void swap(List list){
        if (m_dogList != null) {
            m_dogList.clear();
            m_dogList.addAll(list);
        }
        else {
            m_dogList = list;
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

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

    private Drawable loadImageFromWeb(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            //Toast.makeText(m_context, "Error loading images.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}


