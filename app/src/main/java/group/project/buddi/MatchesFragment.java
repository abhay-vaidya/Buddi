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


import group.project.buddi.helper.SwipeHelper;


public class MatchesFragment extends Fragment {

    String mName;
    final String BASE_URL = "http://animalservices.planet404.com/api/v1/dogs/1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.petList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerAdapter ra = new RecyclerAdapter(createList(10));
        recyclerView.setAdapter(ra);
        ItemTouchHelper.Callback callback = new SwipeHelper(ra);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        return rootView;
    }


    private List<Data> createList(int size) {


        List<Data> result = new ArrayList<Data>();
        for (int i=1; i <= size; i++) {
            Data ci = new Data();
            ci.name = "Jerry";
            ci.age = (i+1) + " weeks old";
            ci.breed = "Golden Retriever";
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.dog);
            ci.image = drawable;

            result.add(ci);
        }

        return result;
    }

}