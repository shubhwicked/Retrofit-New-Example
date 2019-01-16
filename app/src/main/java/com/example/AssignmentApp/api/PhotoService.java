package com.example.AssignmentApp.api;

import com.example.AssignmentApp.model.PhotoApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService {

    String PHOTO_SERVICE_PATH =
            "rest/?method=flickr.photos.search&api_key=c8c54ebcec7bf9ce3f8a97368a79fe8e&" +
                    "per_page=200&format=json&nojsoncallback=1";

    String PHOTO_SERVICE_EXTRAS= "url_t,url_l";

    @GET(PHOTO_SERVICE_PATH)
    Call<PhotoApiResponse> getPhotosQuery(
            @Query("tags") String search,
            @Query("page") int page,
            @Query("extras") String extras
    );
}
