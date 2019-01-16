package com.example.AssignmentApp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.AssignmentApp.R;
import com.example.AssignmentApp.model.PhotoResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<PhotoResponse> mPhotoList;
    private LayoutInflater mInflater;
    private Context mContext;
    private int visibleThreshold = 2;
    int spancount=2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    int size;

    public PhotoAdapter(Context context,int size) {
        this.mContext = context;
        this.size=size;
        this.mInflater = LayoutInflater.from(context);


    }

    public PhotoAdapter(List<PhotoResponse> mPhotoSet, RecyclerView recyclerView) {
        mPhotoList = mPhotoSet;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ProgressBar progress;
        public RelativeLayout container;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            progress = (ProgressBar) itemView.findViewById(R.id.progressBar);
            container =  itemView.findViewById(R.id.container);
            onSizeChanged(itemView,spancount);

        }
        public void onSizeChanged(View view,int spancount){
            view.getLayoutParams().height=size/spancount;
            view.getLayoutParams().width=size/spancount;
        }
    }



    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.photo_row, parent, false);
        final PhotoViewHolder viewHolder = new PhotoViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_PHOTO, mPhotoList.get(position));
                mContext.startActivity(intent);
            }
        });

      /*  view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                removeDialog(position);
                return true;
            }
        });*/

        return viewHolder;
    }
    public void setOnSpan(int spancount){
        this.spancount=spancount;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {
      //  holder.onSizeChanged(spancount);
        PhotoResponse photo = mPhotoList.get(position);
        Picasso.with(mContext)
                .load(photo.getUrlT())

                /*.fit()*/
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progress.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return (mPhotoList == null) ? 0 : mPhotoList.size();
    }

    public void setPhotoList(List<PhotoResponse> photoList) {
        this.mPhotoList = new ArrayList<>();
        this.mPhotoList.addAll(photoList);
        notifyDataSetChanged();
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public void removeDialog(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(R.string.remove_dialog_title);
        alert.setMessage(R.string.remove_dialog_message);
        alert.setPositiveButton(R.string.remove_dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPhotoList.remove(position);
                List<PhotoResponse> responseList = new ArrayList<PhotoResponse>(mPhotoList);
                setPhotoList(responseList);
            }
        });
        alert.setNegativeButton(R.string.remove_dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
