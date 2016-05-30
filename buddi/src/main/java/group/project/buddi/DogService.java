package group.project.buddi;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Abhay on 25/05/2016.
 */
public interface DogService {
    @GET("dogs/{id}")
    Call<Dog> getDog(@Path("id") int id);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://animalservices.planet404.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
