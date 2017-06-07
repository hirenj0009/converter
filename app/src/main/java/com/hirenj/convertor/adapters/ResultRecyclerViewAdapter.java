package com.hirenj.convertor.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hirenj.convertor.R;
import com.hirenj.convertor.common.dragAndDropHelper.ItemTouchHelperAdapter;
import com.hirenj.convertor.common.dragAndDropHelper.ItemTouchHelperViewHolder;
import com.hirenj.convertor.constants.CommonConstants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hiren J on 5/21/2017.
 */

public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.MyViewHolder> implements ItemTouchHelperAdapter, Filterable {

    public ArrayList<HashMap<String, String>> list;
    private ArrayList<HashMap<String, String>> filteredList;

    public ResultRecyclerViewAdapter(ArrayList<HashMap<String, String>> list) {
        this.list = list;
        this.filteredList  = list;
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
        final HashMap<String, String> result = filteredList.get(position);
        holder.valueText.setText(result.get(CommonConstants.FIRST_COLUMN));
        holder.unitText.setText(result.get(CommonConstants.SECOND_COLUMN));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        HashMap<String, String> prev = filteredList.remove(fromPosition);
        filteredList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filteredList = list;
                } else {

                    ArrayList<HashMap<String, String>> mfilteredList = new ArrayList<>();

                    for (HashMap<String, String> converterResult : list) {

                        if (converterResult.get(CommonConstants.SECOND_COLUMN).toLowerCase().contains(charString.toLowerCase())) {
                            mfilteredList.add(converterResult);
                        }
                    }

                    filteredList = mfilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<HashMap<String, String>>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}