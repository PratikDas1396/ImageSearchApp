package com.imagesearch.app.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.imagesearch.app.R;
import com.imagesearch.app.database.Models.*;

import java.util.List;

public class TopLabelViewAdaptor extends RecyclerView.Adapter<TopLabelViewAdaptor.TopLabelViewHolder> {

    private final Context context;
    List<LabelImageDataModel> data;

    public TopLabelViewAdaptor(Context context, List<LabelImageDataModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TopLabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_top_label_adapter_view, parent, false);

        return new TopLabelViewAdaptor.TopLabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopLabelViewHolder holder, int position) {
        LabelImageDataModel image_data = this.data.get(position);
        try {
            holder.getLabelView().setText(image_data.getLabelName());
            setChildRecycleView(holder.getRecyclerView(), image_data.getImages());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class TopLabelViewHolder extends RecyclerView.ViewHolder {
        private TextView label_text;
        private RecyclerView recyclerView;
        private Button label_button;

        public TopLabelViewHolder(@NonNull View itemView) {
            super(itemView);
            label_text = (TextView) itemView.findViewById(R.id.label_text);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.row_item_recycler_view);
            label_button = (Button) itemView.findViewById(R.id.label_button);
        }

        public TextView getLabelView() {
            return label_text;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public Button getButtonView() {
            return label_button;
        }
    }

    private void setChildRecycleView(RecyclerView recycleView, List<ImageLabelMapping> data) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(new TopLabelRowItemAdaptor(context, data));
    }
}
