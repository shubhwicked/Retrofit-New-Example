package com.example.AssignmentApp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotosResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int pages;

    @SerializedName("perpage")
    private int perpage;

    @SerializedName("photo")
    private List<PhotoResponse> photo;

    public int getPage(){
        return page;
    }
    public void setPage(int input){
        this.page = input;
    }
    public int getPages(){
        return pages;
    }
    public void setPages(int input){
        this.pages = input;
    }
    public int getPerpage(){
        return perpage;
    }
    public void setPerpage(int input){
        this.perpage = input;
    }
    public List<PhotoResponse> getPhoto(){
        return photo;
    }
    public void setPhoto(List<PhotoResponse> input){
        this.photo = input;
    }
}