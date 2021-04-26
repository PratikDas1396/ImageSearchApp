package com.imagesearch.app.ui.search;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.imagesearch.app.Adaptor.SearchViewAdaptor;
import com.imagesearch.app.CommonClass.ImageFileFilter;
import com.imagesearch.app.MainActivity;
import com.imagesearch.app.R;
import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.tuyenmonkey.mkloader.MKLoader;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import needle.Needle;

public class SearchFragment extends Fragment {
    private Context context;
    public RecyclerView searchRecycleView;
    public SearchViewAdaptor searchViewAdaptor;
    ImagesRepository ImageRepo;
    List<Images> items = new ArrayList<Images>();
    EditText txtSearch;
    CharSequence searchedText = "";
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

        DatabaseInitializer db = new DatabaseInitializer(context);
        ImageRepo = new ImagesRepository(db);
        items = ImageRepo.GetImages();

        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                items = ImageRepo.GetImages();
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
