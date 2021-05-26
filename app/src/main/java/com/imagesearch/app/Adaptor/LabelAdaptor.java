package com.imagesearch.app.Adaptor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagesearch.app.R;
import com.imagesearch.app.database.Models.Label;

import java.util.List;

public class LabelAdaptor extends RecyclerView.Adapter<LabelAdaptor.LabelViewHolder> {

    private final Context context;
    List<Label> data;

    public LabelAdaptor(Context context, List<Label> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public LabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.label_adaptor_view, parent, false);

        return new LabelAdaptor.LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelViewHolder holder, int position) {
        Label label = this.data.get(position);
        try {
            holder.getTextView().setText(label.getLabelName().toUpperCase());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class LabelViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.labelTextView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
