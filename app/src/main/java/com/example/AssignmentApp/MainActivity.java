package com.example.AssignmentApp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.AssignmentApp.api.PhotoApi;
import com.example.AssignmentApp.api.PhotoService;
import com.example.AssignmentApp.model.PhotoApiResponse;
import com.example.AssignmentApp.model.PhotoResponse;
import com.example.AssignmentApp.views.PhotoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.AssignmentApp.R.id.recyclerView;
import static com.example.AssignmentApp.api.PhotoService.PHOTO_SERVICE_EXTRAS;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private PhotoAdapter mPhotoAdapter;
    private GridLayoutManager mLayoutManager;
   // private SwipeRefreshLayout mSwipeRefreshLayout;
    private String querySearch="null";
    private boolean isLoading = true;
    private int currentPage = 1;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    private PhotoService photoService;
    private List<PhotoResponse> photoSet;
    int spancount = 2;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        size = displayMetrics.widthPixels;
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) findViewById(recyclerView);
        mLayoutManager = new GridLayoutManager(this, spancount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPhotoAdapter = new PhotoAdapter(this, size);
        mPhotoAdapter.setOnSpan(spancount);
        mRecyclerView.setAdapter(mPhotoAdapter);

      /*  mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoading = false;
                            Log.i("...", "last i guess");
                            currentPage++;
//                            loadNextPage(currentPage);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadNextPage(currentPage);
                }
            }
        });

        photoService = PhotoApi.getClient().create(PhotoService.class);

    }

    private void refreshItems() {
        getGridImages(1);
        //mSwipeRefreshLayout.setRefreshing(false);
    }

    private Call<PhotoApiResponse> photoApiResponseCall(int page) {
        return photoService.getPhotosQuery(querySearch,
                page, PHOTO_SERVICE_EXTRAS
        );
    }

    private void getGridImages(int firstPage) {
        photoApiResponseCall(firstPage).enqueue(new Callback<PhotoApiResponse>() {
            @Override
            public void onResponse(Call<PhotoApiResponse> call, Response<PhotoApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                    mPhotoAdapter.setPhotoList(response.body().getPhotos().getPhoto());
                    isLoading = true;
                }
            }

            @Override
            public void onFailure(Call<PhotoApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadNextPage(final int pageNumber) {
        Log.d(TAG, "loadNextPage: " + pageNumber);

        photoApiResponseCall(pageNumber).enqueue(new Callback<PhotoApiResponse>() {
            @Override
            public void onResponse(Call<PhotoApiResponse> call, Response<PhotoApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                    mPhotoAdapter.setPhotoList(response.body().getPhotos().getPhoto());
                    isLoading = true;
                }
            }

            @Override
            public void onFailure(Call<PhotoApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }

    MenuItem searchItem, span2, span3, span4;
    SearchManager searchManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        searchItem = menu.findItem(R.id.action_search);
        span2 = menu.findItem(R.id.action_span2);
        span3 = menu.findItem(R.id.action_span3);
        span4 = menu.findItem(R.id.action_span4);
        searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_span2:
                spancount=2;
                mLayoutManager = new GridLayoutManager(this, spancount);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mPhotoAdapter.setOnSpan(spancount);
                mRecyclerView.invalidate();
                break;
            case R.id.action_span3:
                spancount=3;
                mLayoutManager = new GridLayoutManager(this, spancount);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mPhotoAdapter.setOnSpan(spancount);
                mRecyclerView.invalidate();
                break;

            case R.id.action_span4:
                spancount=4;
                mLayoutManager = new GridLayoutManager(this, spancount);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mPhotoAdapter.setOnSpan(spancount);
                mRecyclerView.invalidate();
                break;
            case R.id.action_search:
                SearchView searchView = null;
                if (searchItem != null) {
                    searchView = (SearchView) searchItem.getActionView();
                }
                if (searchView != null) {
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
                }
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if(query!=null){
                            querySearch=query;
                            getGridImages(1);
                            mProgressBar.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(MainActivity.this, "Search some images by name!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return true;
                    }
                });

                break;

        }

        return super.onOptionsItemSelected(item);

    }
}