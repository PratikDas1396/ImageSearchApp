package com.imagesearch.app.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.imagesearch.app.R;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchViewAdaptor extends RecyclerView.Adapter<SearchViewAdaptor.SearchViewHolder> {

    private List<Images> DataSet;
    private List<Images> filteredDataList;
    private Context context;

    public SearchViewAdaptor(Context context, List<Images> DataSet) {
        this.context = context;
        this.DataSet = DataSet;
        this.filteredDataList = DataSet;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_search_adaptor_view, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Images img = this.filteredDataList.get(position);
        try {
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
        return this.filteredDataList.size();
    }

    public Filter getSearchFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                filteredDataList = new ArrayList<Images>();
                String Key = charSequence.toString();
                if (Key.isEmpty()) {
                    filteredDataList = DataSet;
                } else {
                    ImageLabelMappingRepository imagesLabelMappingRepository = new ImageLabelMappingRepository();
                    filteredDataList = imagesLabelMappingRepository.GetImagesByLabel(Key.toLowerCase());
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDataList = (List<Images>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public SearchViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.searchImageView);
        }

        public ImageView getSearchView() {
            return imageView;
        }
    }

}
