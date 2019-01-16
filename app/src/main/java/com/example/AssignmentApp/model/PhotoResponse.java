package com.example.AssignmentApp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PhotoResponse implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    @SerializedName("url_t")
    private String urlT;

    @SerializedName("url_l")
    private String urlL;


    public PhotoResponse(Parcel in) {
        title = in.readString();
        id = in.readString();
        urlT = in.readString();
        urlL = in.readString();
    }

    public static final Creator<PhotoResponse> CREATOR = new Creator<PhotoResponse>() {
        @Override
        public PhotoResponse createFromParcel(Parcel in) {
            return new PhotoResponse(in);
        }

        @Override
        public PhotoResponse[] newArray(int size) {
            return new PhotoResponse[size];
        }
    };

    public PhotoResponse(String title, String id, String urlT, String urlL) {
        this.title = title;
        this.id = id;
        this.urlT = urlT;
        this.urlL = urlL;
    }

    public String getId() {
        return id;
    }

    public void setId(String input) {
        this.id = input;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String input) {
        this.title = input;
    }

    public String getUrlT() {
        return urlT;
    }

    public void setUrlT(String input) {
        this.urlT = input;
    }

    public String getUrlL() {
        return urlL;
    }

    public void setUrlL(String input) {
        this.urlL = input;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(id);
        parcel.writeString(urlT);
        parcel.writeString(urlL);
    }
}
