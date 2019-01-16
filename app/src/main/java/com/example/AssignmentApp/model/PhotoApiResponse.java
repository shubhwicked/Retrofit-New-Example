package com.example.AssignmentApp.model;

import com.google.gson.annotations.SerializedName;

public class PhotoApiResponse {

    @SerializedName("photos")
    private PhotosResponse photos;

    public PhotosResponse getPhotos(){
        return photos;
    }
    public void setPhotos(PhotosResponse input){
        this.photos = input;
    }

}
