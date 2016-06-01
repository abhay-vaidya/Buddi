package group.project.buddi;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import group.project.buddi.helper.SwipeHelper;


public class MatchesFragment extends Fragment {

    private List<Data> data = new ArrayList<Data>();
    private DataAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.petList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        loadJSON();

        return rootView;
    }

    private void loadJSON() {
        Ion.with(getActivity())
                .load("http://animalservices.planet404.com/api/v1/dogs")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        for (int i=0; i<result.size(); i++) {
                            JsonObject dog = result.get(i).getAsJsonObject();
//                            textView.append(dog.get("name").getAsString() + '\n' + dog.get("reference_num").getAsString() + "\n\n");
//                            Toast.makeText(getActivity(), dog.get("name").getAsString(), Toast.LENGTH_SHORT).show();

                            Data ci = new Data();
                            ci.name = dog.get("name").getAsString();
                            ci.age = "6 weeks old";
                            ci.breed = "Golden Retriever";
                            Resources res = getResources();
                            Drawable drawable = res.getDrawable(R.drawable.dog);
                            ci.image = drawable;

                            data.add(ci);

                        }

                        adapter = new DataAdapter(data);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}