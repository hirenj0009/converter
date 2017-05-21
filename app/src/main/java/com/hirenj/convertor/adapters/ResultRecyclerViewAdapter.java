package com.hirenj.convertor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hirenj.convertor.R;
import com.hirenj.convertor.common.CommonAccess;
import com.hirenj.convertor.common.dragAndDropHelper.ItemTouchHelperAdapter;
import com.hirenj.convertor.common.dragAndDropHelper.ItemTouchHelperViewHolder;
import com.hirenj.convertor.constants.CommonConstants;
import com.hirenj.convertor.fragement.FavouriteFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hiren J on 5/21/2017.
 */

public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    public ArrayList<HashMap<String, String>> list;

    public ResultRecyclerViewAdapter(ArrayList<HashMap<String, String>> list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        private TextView valueText;
        private TextView unitText;

        public MyViewHolder(View view) {
            super(view);
            valueText = (TextView) view.findViewById(R.id.valueListItem);
            unitText = (TextView) view.findViewById(R.id.unitListItem);
        }

        @Override
        public void onItemSelected() {
            //itemView.setBackgroundColor(Color.LTGRAY); //removing color as  Elevation works with only non-transparent backgrounds on views
        }

        @Override
        public void onItemClear() {
            //itemView.setBackgroundColor(0); //removing color as  Elevation works with only non-transparent backgrounds on views
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_list_item, parent, false);

        return new ResultRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultRecyclerViewAdapter.MyViewHolder holder, int position) {
        final HashMap<String, String> result = list.get(position);
        holder.valueText.setText(result.get(CommonConstants.FIRST_COLUMN));
        holder.unitText.setText(result.get(CommonConstants.SECOND_COLUMN));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        HashMap<String, String> prev = list.remove(fromPosition);
        list.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}