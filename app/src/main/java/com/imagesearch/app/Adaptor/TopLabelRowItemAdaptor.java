package com.imagesearch.app.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imagesearch.app.Animation.SearchItemAnimation;
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

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(context)
                    .load(img.getFullPath())
                    .apply(requestOptions)
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(v -> {
                Toast.makeText(context, img.getImageName(), Toast.LENGTH_LONG).show();
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
