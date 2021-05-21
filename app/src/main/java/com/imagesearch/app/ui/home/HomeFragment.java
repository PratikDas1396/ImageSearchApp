package com.imagesearch.app.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imagesearch.app.Adaptor.TopLabelViewAdaptor;
import com.imagesearch.app.CommonClass.AppPermission;
import com.imagesearch.app.CommonClass.CustomDialog;
import com.imagesearch.app.R;
import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.LabelImageDataModel;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;
import com.imagesearch.app.database.Repository.LabelRepository;
import com.imagesearch.app.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import needle.Needle;

public class HomeFragment extends Fragment {

    public static final int READ_FILE_PERMISSION_CODE = 0x30;
    private Context context;
    private Activity activity;
    public RecyclerView topLabelRecycleView;
    public TopLabelViewAdaptor adaptor;
    List<LabelImageDataModel> items = new ArrayList<LabelImageDataModel>();
    private ImageLabelMappingRepository mappingRepository;
    private LabelRepository labelRepository;
    FragmentHomeBinding binding;
    DatabaseInitializer db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        context = activity.getApplicationContext();
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        topLabelRecycleView = root.findViewById(R.id.topLableRecycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        topLabelRecycleView.setLayoutManager(layoutManager);

        if (AppPermission.checkAppPermissionsGranted(this.context)) {
            appStart();
        } else {
            if (AppPermission.shouldShowRequestPermissionRationale(HomeFragment.this)) {
                showDialog();
            } else {
                AppPermission.requestAppPermission(HomeFragment.this, this.context, this.READ_FILE_PERMISSION_CODE);
            }
        }
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_FILE_PERMISSION_CODE: {
                if (grantResults.length > 0) {
                    appStart();
                } else {
                    showDialog();
                }
                break;
            }
        }
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

    private void appStart() {
        labelRepository = new LabelRepository();
        mappingRepository = new ImageLabelMappingRepository();
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                items = mappingRepository.GetImagesByLabel(labelRepository.GetTrendingLabels());
                Needle.onMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        adaptor = new TopLabelViewAdaptor(context, items);
                        topLabelRecycleView.setAdapter(adaptor);
                    }
                });
            }
        });
    }

    private void showDialog() {
        Dialog dialog = CustomDialog.getAllPermissionDialog(activity);
        Button btnOk = (Button) dialog.findViewById(R.id.btnGrantPermission);
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            AppPermission.requestAppPermission(activity, READ_FILE_PERMISSION_CODE);
        });
        dialog.show();
    }
}