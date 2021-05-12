package com.imagesearch.app.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imagesearch.app.Animation.SearchItemAnimation;
import com.imagesearch.app.R;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;

import java.util.ArrayList;
import java.util.List;

public class SearchViewAdaptor extends RecyclerView.Adapter<SearchViewAdaptor.SearchViewHolder> {

    private List<ImageLabelMapping> DataSet;
    private List<ImageLabelMapping> filteredDataList;
    private Context context;

    public SearchViewAdaptor(Context context, List<ImageLabelMapping> DataSet) {
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
        ImageLabelMapping img = this.filteredDataList.get(position);
        try {
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
        return this.filteredDataList.size();
    }

    public Filter getSearchFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Key = charSequence.toString();
                if (Key.isEmpty()) {
                    filteredDataList = DataSet;
                } else {

                    List<ImageLabelMapping> lstFiltered = new ArrayList<ImageLabelMapping>();
                    if (Key.length() >= 3) {

                        DataSet.stream()
                                .filter(file -> file.getImageName().toLowerCase().contains(Key.toLowerCase()))
                                .forEach(filtered -> lstFiltered.add(filtered));

                        filteredDataList = lstFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDataList = (List<ImageLabelMapping>) filterResults.values;
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
