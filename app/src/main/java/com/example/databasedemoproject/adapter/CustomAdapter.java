package com.example.databasedemoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.databasedemoproject.R;
import com.example.databasedemoproject.model.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarabjjeet on 9/11/17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> {
    private List<DataModel> eventList;
    ArrayList<DataModel> filteredList;
    private Context context;

    public CustomAdapter(List<DataModel> eventList, Context context) {
        super(context, R.layout.custom_list_view, eventList);

        this.eventList = eventList;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        DataModel dataModel = getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);

            holder.title = (TextView) convertView.findViewById(R.id.txt_title);
            holder.description = (TextView) convertView.findViewById(R.id.txt_description);
            holder.time = (TextView) convertView.findViewById(R.id.txt_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(dataModel.getTitle());
        holder.description.setText(dataModel.getDescription());
        holder.time.setText(dataModel.getTime());

        return convertView;

    }

    class ViewHolder {
        TextView title;
        TextView description;
        TextView time;


    }
}

