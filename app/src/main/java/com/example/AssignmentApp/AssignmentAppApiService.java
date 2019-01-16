package com.example.AssignmentApp;

import com.example.AssignmentApp.model.PhotoApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface AssignmentAppApiService {
    @GET
    Call<PhotoApiResponse> getPhotosDetails(@Url String url);
}