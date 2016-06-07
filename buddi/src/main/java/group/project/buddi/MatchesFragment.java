package group.project.buddi;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import group.project.buddi.model.DatabaseAdapter;
import group.project.buddi.model.Dog;
import group.project.buddi.model.DogEntry;


public class MatchesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<Dog> mData = new ArrayList<Dog>();
    private DogRecylerAdapter mAdapter;
    private RecyclerView mRecyclerPets;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseAdapter dbAdapter;
    private Context m_context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        m_context = getContext();


        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerPets = (RecyclerView) rootView.findViewById(R.id.petList);
        mRecyclerPets.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadFromDB();


        // init swipe to dismiss logic
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // callback for drag-n-drop, false to skip this feature
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // callback for swipe to dismiss, removing item from data and adapter

                int id = mData.get(viewHolder.getAdapterPosition()).getID();
//                Toast.makeText(getContext(), "Removed dog with id:" + id, Toast.LENGTH_SHORT).show();

                dbAdapter = new DatabaseAdapter(m_context);
                try {
                    dbAdapter.open();
                } catch (Exception e) {
                    Toast.makeText(m_context, "Error opening database.", Toast.LENGTH_SHORT).show();
                }

                dbAdapter.updateDog(DogEntry.COLUMN_NAME_BLACKLIST, id, true);


                mData.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerPets);


        return rootView;
    }

    private void loadFromDB() {

        // get Instance  of Database Adapter
        dbAdapter = new DatabaseAdapter(m_context);
        try {
            dbAdapter.open();
        } catch (Exception e) {
            Toast.makeText(m_context, "Error opening database.", Toast.LENGTH_SHORT).show();
        }

        // Load all dogs from database
        mData = dbAdapter.getAllDogs();

        if (mData.size() == 0) {
            loadJSON();
        }

        mAdapter = new DogRecylerAdapter(getActivity(), mData);
        mRecyclerPets.setAdapter(mAdapter);

    }

    private void loadJSON() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        String code = sharedPref.getString("code", "A0B0C0D0E0");
        
        Ion.with(getActivity())
                .load("http://ec2-52-91-255-81.compute-1.amazonaws.com/api/v1/feed/" + code + "?access_token=" + sharedPref.getString("auth_token", "broke"))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        
                        if (result == null) {
                            Toast.makeText(m_context, "Error loading results.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mData.clear();

                        for (int i = 0; i < result.size(); i++) {

                            JsonObject jObj = result.get(i).getAsJsonObject();

                            Dog dog = new Dog();
                            dog.setID(jObj.get("id").getAsInt());
                            dog.setName(jObj.get("name").getAsString());
                            dog.setAge(jObj.get("age").getAsInt());
                            dog.setBreed(jObj.get("breed").getAsString());
                            dog.setImageURL(jObj.get("image").getAsString());

                            // get Instance  of Database Adapter
                            dbAdapter = new DatabaseAdapter(m_context);
                            dbAdapter.open();

                            dbAdapter.insertDog(dog);

                        }

                        loadFromDB();

                    }
                });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        loadJSON();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close database
        dbAdapter.close();
    }

    public static Drawable loadImage(String url) {
        try {
            Drawable d = Drawable.createFromPath(url);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}