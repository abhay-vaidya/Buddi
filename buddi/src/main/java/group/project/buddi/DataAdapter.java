package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Abhay on 10/05/2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.RecyclerViewHolder> {

    private static List<Data> petData;
    private Context context;
    private int id;

    public DataAdapter(Context context, List<Data> petData) {

        this.petData = petData;
        this.context = context;
    }



    @Override
    public int getItemCount() {
        return petData.size();
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        Data d = petData.get(i);
        recyclerViewHolder.vName.setText(d.name);
        recyclerViewHolder.vAge.setText(d.age);
        recyclerViewHolder.vBreed.setText(d.breed);
        recyclerViewHolder.vImage.setImageDrawable(d.image);

        id = d.id;

        recyclerViewHolder.vButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();
                intent.putExtra("pet_id", id);
                context.startActivity(intent);
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
       petData.remove(pos);
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
        if (petData != null) {
            petData.clear();
            petData.addAll(list);
        }
        else {
            petData = list;
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
                    Toast.makeText(v.getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}


