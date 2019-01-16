package com.example.AssignmentApp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.AssignmentApp.R;
import com.example.AssignmentApp.model.PhotoResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView titleTextView;
    public static final String EXTRA_PHOTO = "photo";

    private PhotoResponse mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting details screen layout
        setContentView(R.layout.activity_details_view);
        if (getIntent().hasExtra(EXTRA_PHOTO)) {
            mPhoto = getIntent().getParcelableExtra(EXTRA_PHOTO);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a photo parcelable");
        }
        //set image title
        titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(mPhoto.getTitle());
        //Set image url
        imageView = (ImageView) findViewById(R.id.detailed_image);
        progressBar =  findViewById(R.id.progressBar);
        Picasso.with(this)
                .load(mPhoto.getUrlL())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(DetailsActivity.this, "Internet is not working properly!", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
