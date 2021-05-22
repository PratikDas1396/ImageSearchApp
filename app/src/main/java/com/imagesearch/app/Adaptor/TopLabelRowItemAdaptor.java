package com.imagesearch.app.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.imagesearch.app.R;
import com.imagesearch.app.database.Models.*;

import java.util.List;

public class TopLabelRowItemAdaptor extends RecyclerView.Adapter<TopLabelRowItemAdaptor.TopLabelRowItemViewHolder> {

    private final Context context;
    List<ImageLabelMapping> data;

    public TopLabelRowItemAdaptor(Context context, List<ImageLabelMapping> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TopLabelRowItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_top_label_row_item_adaptor_view, parent, false);

        return new TopLabelRowItemAdaptor.TopLabelRowItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopLabelRowItemViewHolder holder, int position) {
        ImageLabelMapping img = this.data.get(position);
        try {
            //SearchItemAnimation.animateFadeIn(holder.imageView, position);

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.loading_icon_shape);
            Glide.with(context)
                    .load(img.getFullPath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(requestOptions)
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(img.getFullPath()), "image/*");
                this.context.startActivity(intent);
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class TopLabelRowItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        public TopLabelRowItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.row_item_image_view);

        }
        public ImageView getSearchView() {
            return imageView;
        }
    }
}
