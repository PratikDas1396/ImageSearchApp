package com.imagesearch.app.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.imagesearch.app.Adaptor.TopLabelViewAdaptor;
import com.imagesearch.app.R;
import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Models.LabelImageDataModel;
import com.imagesearch.app.database.Repository.ImageLableMappingRepository;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.imagesearch.app.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Context context;
    public RecyclerView topLabelRecycleView;
    public TopLabelViewAdaptor adaptor;
    List<LabelImageDataModel> items = new ArrayList<LabelImageDataModel>();
    private ImageLableMappingRepository ImageRepo;

    FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        topLabelRecycleView = root.findViewById(R.id.topLableRecycleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        topLabelRecycleView.setLayoutManager(layoutManager);

        DatabaseInitializer db = new DatabaseInitializer(context);
        ImageRepo = new ImageLableMappingRepository(db);
        items = ImageRepo.GetTrendingLabels();

        adaptor = new TopLabelViewAdaptor(context, items);
        topLabelRecycleView.setAdapter(adaptor);

//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //binding =  FragmentHomeBinding.inflate(getLayoutInflater());
        return root;
        //return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}