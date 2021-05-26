package com.imagesearch.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imagesearch.app.Adaptor.LabelAdaptor;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Repository.LabelRepository;

import java.util.List;

public class LabelActivity extends AppCompatActivity {
    private RecyclerView labelRecycleView;
    private LabelAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        labelRecycleView = findViewById(R.id.labelRecycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        labelRecycleView.setLayoutManager(layoutManager);
        List<Label> labels = new LabelRepository().Get();
        adaptor = new LabelAdaptor(this, labels);
        labelRecycleView.setAdapter(adaptor);
    }
}
