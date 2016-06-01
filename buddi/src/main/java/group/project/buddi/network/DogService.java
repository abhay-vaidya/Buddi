package group.project.buddi.network;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;

/**
 * Created by Abhay on 25/05/2016.
 */
public interface DogService {
    @GET("dogs/{id}")
    Call<List<Dog>> dogs(
            @Path("id") String id
    );

}
