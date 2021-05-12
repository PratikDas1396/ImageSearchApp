package com.imagesearch.app.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.imagesearch.app.Adaptor.SearchViewAdaptor;
import com.imagesearch.app.R;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.List;

import needle.Needle;

public class SearchFragment extends Fragment {
    private Context context;
    private RecyclerView searchRecycleView;
    private SearchViewAdaptor searchViewAdaptor;
    private ImageLabelMappingRepository imagesLableMappingRepository;
    private List<ImageLabelMapping> items = new ArrayList<ImageLabelMapping>();
    private EditText txtSearch;
    private CharSequence searchedText = "";
    private final int numberOfColums = 3;
    MKLoader loader;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        View view = root.findViewById(R.id.no_search_found);
        searchRecycleView = root.findViewById(R.id.recyclerView);
        loader = root.findViewById(R.id.search_loader);

        loader.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        searchRecycleView.setVisibility(view.INVISIBLE);

        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                imagesLableMappingRepository = new ImageLabelMappingRepository();
                items = imagesLableMappingRepository.Get();
                Needle.onMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(numberOfColums, StaggeredGridLayoutManager.VERTICAL);
                        searchRecycleView.setLayoutManager(layoutManager);
                        searchViewAdaptor = new SearchViewAdaptor(context, items);
                        searchRecycleView.setAdapter(searchViewAdaptor);
                        loader.setVisibility(View.INVISIBLE);
                        searchRecycleView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        txtSearch = root.findViewById(R.id.txtSearch);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchViewAdaptor.getSearchFilter().filter(s);
                searchedText = s;

                if (searchViewAdaptor.getItemCount() == 0) {
                    view.setVisibility(View.VISIBLE);
                    searchRecycleView.setVisibility(view.INVISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                    searchRecycleView.setVisibility(view.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return root;
    }
}
