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

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private Context context;
    public RecyclerView searchRecycleView;
    public SearchViewAdaptor searchViewAdaptor;
    ImagesRepository ImageRepo;
    List<Images> items = new ArrayList<Images>();
    EditText txtSearch;
    CharSequence searchedText = "";
    private final int numberOfColums = 3;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE }, 0x30);
        }

        DatabaseInitializer db = new DatabaseInitializer(context);
        ImageRepo = new ImagesRepository(db);
        items = ImageRepo.GetImages();

        searchRecycleView = root.findViewById(R.id.recyclerView);
        txtSearch = root.findViewById(R.id.txtSearch);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchViewAdaptor.getSearchFilter().filter(s);
                searchedText = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(this.numberOfColums, StaggeredGridLayoutManager.VERTICAL);

        searchRecycleView.setLayoutManager(layoutManager);
        searchViewAdaptor = new SearchViewAdaptor(context, items);
        searchRecycleView.setAdapter(searchViewAdaptor);

//        private SearchViewModel searchViewModel;
//        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
//        final TextView textView = root.findViewById(R.id.text_search);
//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

}
