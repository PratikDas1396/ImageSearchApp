package com.imagesearch.app.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.imagesearch.app.Adaptor.SearchViewAdaptor;
import com.imagesearch.app.R;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.List;

import needle.Needle;

public class SearchFragment extends Fragment {
    private Context context;
    private RecyclerView searchRecycleView;
    private SearchViewAdaptor searchViewAdaptor;
    private ImageLabelMappingRepository imagesLabelMappingRepository;
    private ImagesRepository imagesRepository;
    private List<Images> items = new ArrayList<Images>();
    private Button btnSearch;
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

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(numberOfColums, StaggeredGridLayoutManager.VERTICAL);
        searchRecycleView.setLayoutManager(layoutManager);
        searchViewAdaptor = new SearchViewAdaptor(context, items);
        searchRecycleView.setAdapter(searchViewAdaptor);

        loader.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        searchRecycleView.setVisibility(view.INVISIBLE);

        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
//                imagesLabelMappingRepository = new ImageLabelMappingRepository();
                imagesRepository = new ImagesRepository();
                items = imagesRepository.Get();
                //items = imagesLabelMappingRepository.GetImagesByLabel();
                Needle.onMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        //RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(numberOfColums, StaggeredGridLayoutManager.VERTICAL);
                        //searchRecycleView.setLayoutManager(layoutManager);
                        searchViewAdaptor = new SearchViewAdaptor(context, items);
                        searchRecycleView.setAdapter(searchViewAdaptor);
                        loader.setVisibility(View.INVISIBLE);
                        searchRecycleView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        txtSearch = root.findViewById(R.id.txtSearch);
        btnSearch = root.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);
                searchRecycleView.setVisibility(View.INVISIBLE);

                searchViewAdaptor.getSearchFilter().filter(txtSearch.getText());

                loader.setVisibility(View.INVISIBLE);
                searchRecycleView.setVisibility(View.VISIBLE);
            }
        });

//        txtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
////                searchedText = s;
////
////                loader.setVisibility(View.INVISIBLE);
////                if (searchViewAdaptor.getItemCount() == 0) {
////                    view.setVisibility(View.VISIBLE);
////                    searchRecycleView.setVisibility(view.INVISIBLE);
////                } else {
////                    view.setVisibility(View.INVISIBLE);
////                    searchRecycleView.setVisibility(view.VISIBLE);
////                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        return root;
    }
}
