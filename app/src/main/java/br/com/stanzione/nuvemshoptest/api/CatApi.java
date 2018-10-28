package br.com.stanzione.nuvemshoptest.api;

import java.util.List;

import br.com.stanzione.nuvemshoptest.data.Cat;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CatApi {

    static final String API_KEY = "a24ab4d8-2620-4e21-8fcc-a7e50894a7b9";
    static final int LIMIT = 25;

    @Headers({
            "Content-type: application/json",
            "x-api-key: " + API_KEY
    })
    @GET("images/search?mime_types=jpg,png&order=RANDOM&format=json")
    Observable<List<Cat>> fetchCats(@Query("limit") int limit);

}
