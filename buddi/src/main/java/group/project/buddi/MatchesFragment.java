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

import android.widget.ArrayAdapter;
import android.widget.Toast;

import group.project.buddi.helper.SwipeHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MatchesFragment extends Fragment implements Callback<DogList>{

    String mName;
    final String BASE_URL = "http://animalservices.planet404.com/api/v1/";
    List<Data> result = new ArrayList<Data>();
    String dogName = "Bob";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

        useRetrofit();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.petList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerAdapter ra = new RecyclerAdapter(createList(10));
        recyclerView.setAdapter(ra);
        ItemTouchHelper.Callback callback = new SwipeHelper(ra);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        return rootView;
    }

    public void useRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        DogService dogApi = retrofit.create(DogService.class);

        Call<DogList> call = dogApi.getDog("1");
        //asynchronous call
        call.enqueue(this);

        // synchronous call would be with execute, in this case you
        // would have to perform this outside the main thread
        // call.execute()

        // to cancel a running request
        // call.cancel();
        // calls can only be used once but you can easily clone them
        //Call<StackOverflowQuestions> c = call.clone();
        //c.enqueue(this);

    }

    private List<Data> createList(int size) {

        List<Data> result = new ArrayList<Data>();
        for (int i=1; i <= size; i++) {
            Data ci = new Data();
            ci.name = dogName;
            ci.age = (i+1) + " weeks old";
            ci.breed = "Golden Retriever";
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.dog);
            ci.image = drawable;

            result.add(ci);
        }

        return result;
    }


    @Override
    public void onResponse(Call<DogList> call, Response<DogList> response) {
        Toast.makeText(getActivity(), "GREAT SUCCESS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Call<DogList> call, Throwable t) {
        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}