package group.project.buddi;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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


public class MatchesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<Data> mData = new ArrayList<Data>();
    private DataAdapter mAdapter;
    private RecyclerView mRecyclerPets;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerPets = (RecyclerView) rootView.findViewById(R.id.petList);
        mRecyclerPets.setLayoutManager(new LinearLayoutManager(getActivity()));


        ItemTouchHelper.Callback callback = new SwipeHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerPets);

        loadJSON();

        mAdapter = new DataAdapter(mData);
        mRecyclerPets.setAdapter(mAdapter);

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

                            mData.add(ci);

                        }

                        mAdapter = new DataAdapter(mData);
                        mRecyclerPets.setAdapter(mAdapter);

                    }
                });
    }

    @Override
    public void onRefresh() {
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}