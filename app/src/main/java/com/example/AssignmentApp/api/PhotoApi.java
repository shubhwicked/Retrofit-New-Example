package com.example.AssignmentApp.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoApi {

    private static Retrofit retrofit = null;

    private static final String ROOT_URL = "https://api.flickr.com/services/";

    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
            "api_key=c8c54ebcec7bf9ce3f8a97368a79fe8e&tags=tile&" +
            "format=json&nojsoncallback=1&page=1&per_page=500&extras=url_t,url_l";

    private static OkHttpClient buildClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ROOT_URL)
                    .build();
        }
        return retrofit;
    }
}
