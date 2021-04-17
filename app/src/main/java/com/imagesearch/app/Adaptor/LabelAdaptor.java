package com.imagesearch.app.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imagesearch.app.R;
import com.imagesearch.app.database.Models.Label;

import java.util.List;

public class LabelAdaptor extends BaseAdapter {

    private Context context;
    private List<Label> list;

    public LabelAdaptor(Context context, List<Label> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final Label labels = (Label) this.getItem(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.label_adaptor_view, null);
                TextView txt =  convertView.findViewById(R.id.LabelID);
                txt.setText(labels.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertView;
    }
}
