package com.zukron.stopwatch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zukron.stopwatch.R;
import com.zukron.stopwatch.model.Lap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Project name is Stopwatch
 * Created by Zukron Alviandy R on 7/20/2020
 */
public class LapAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Lap> laps;

    public LapAdapter(Context context, ArrayList<Lap> laps) {
        // sort array jadi latest item to top
        Collections.sort(laps, new Comparator<Lap>() {
            @Override
            public int compare(Lap lap, Lap t1) {
                return t1.getIndex().compareTo(lap.getIndex());
            }
        });

        this.context = context;
        this.laps = laps;
    }

    @Override
    public int getCount() {
        return laps.size();
    }

    @Override
    public Object getItem(int i) {
        return laps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.item_lap, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Lap lap = laps.get(i);
        viewHolder.tvIndexLapItem.setText(String.valueOf(lap.getIndex()));
        viewHolder.tvDurationLapItem.setText(lap.getLap());
        viewHolder.tvDiffLapItem.setText(lap.getDiff());

        return view;
    }

    private static class ViewHolder {
        private TextView tvIndexLapItem;
        private TextView tvDurationLapItem;
        private TextView tvDiffLapItem;

        private ViewHolder(View itemView) {
            this.tvIndexLapItem = itemView.findViewById(R.id.tv_index_lap_item);
            this.tvDurationLapItem = itemView.findViewById(R.id.tv_duration_lap_item);
            this.tvDiffLapItem = itemView.findViewById(R.id.tv_diff_lap_item);
        }
    }
}
