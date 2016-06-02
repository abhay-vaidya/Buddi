package group.project.buddi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Abhay on 10/05/2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.RecyclerViewHolder> {

    private static List<Data> petData;

    public DataAdapter(List<Data> petData) {
        this.petData = petData;
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

        public RecyclerViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.petName);
            vAge = (TextView) v.findViewById(R.id.petAge);
            vBreed= (TextView) v.findViewById(R.id.petBreed);
            vImage = (ImageView) v.findViewById(R.id.imageView);
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

}


