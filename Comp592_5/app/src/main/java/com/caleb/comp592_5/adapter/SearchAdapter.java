package com.caleb.comp592_5.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caleb.comp592_5.data.LocationData;
import com.caleb.comp592_5.activity.R;
import com.caleb.comp592_5.activity.SearchActivity;
import com.caleb.comp592_5.common.logger.Log;


import java.util.ArrayList;

/**
 * Created by Caleb on 9/1/2016.
 */
public class SearchAdapter extends
        RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    private static final String TAG = "SearchAdapter";
    private ArrayList<LocationData> searchList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView icon;

        public MyViewHolder(View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    ((SearchActivity)context).getSelectedLocation(searchList.get(getAdapterPosition()));
                }
            });
            name = (TextView) view.findViewById(R.id.history_name);
            icon = (ImageView) view.findViewById(R.id.search_icon);
        }
    }

    public SearchAdapter(Context context,ArrayList<LocationData> searchHistoryList) {
        this.context = context;
        searchList = searchHistoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_list_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        LocationData searchData = searchList.get(position);
        if("Current Location".equals(searchData.getAddress())) {
            holder.icon.setImageResource(R.drawable.ic_my_location_black_24dp);
        }
        else{
            holder.icon.setImageResource(R.drawable.ic_history_black_24dp);
        }
        holder.name.setText(
                searchData.getAddress().substring(0,Math.min(
                        searchData.getAddress().length(),19
                )));
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
}