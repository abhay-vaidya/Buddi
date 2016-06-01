package group.project.buddi;


import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Call;

/**
 * Created by Abhay on 25/05/2016.
 */
public interface DogService {
    @GET("dogs/{id}")
    Call<DogList> getDog(@Path("id") String id);

}
