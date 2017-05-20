package com.hirenj.convertor.adapters;

import android.graphics.Color;
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
import com.hirenj.convertor.fragement.FavouriteFragment;

import java.util.List;

/**
 * Created by Hiren J on 5/13/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private List<String> converterList;
    private String favType;
    private TextView converterText;
    private ToggleButton toggle = null;


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String prev = converterList.remove(fromPosition);
        converterList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        converterList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {


        public MyViewHolder(View view) {
            super(view);
            converterText = (TextView) view.findViewById(R.id.ConverterListItem);
            toggle = (ToggleButton) view.findViewById(R.id.favouriteToggle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

    }

    public RecyclerViewAdapter(List<String> converterList, String favType) {
        this.converterList = converterList;
        this.favType = favType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.converter_list_item, parent, false);

        return new RecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        final String converter = converterList.get(position);
        converterText.setText(converter);


        if (toggle != null) {
            toggle.setOnClickListener(new ToggleButton.OnClickListener() {

                public void onClick(View v) {

                    RecyclerView favouritesRecyclerView = FavouriteFragment.favouriteRecyclerList;
                    RecyclerViewAdapter favouriteAdapter = (RecyclerViewAdapter) favouritesRecyclerView.getAdapter();
                    List<String> favourites = favouriteAdapter.getConverterList();
                    if (toggle.isChecked() && !favourites.contains(converter)) {
                        favourites.remove(converter);
                        CommonAccess.convertersStatMap.put(converter, false);
                    } else {
                        favourites.add(converter);
                        CommonAccess.convertersStatMap.put(converter, true);
                    }
                    //favouriteAdapter.getToggle().setVisibility(View.INVISIBLE);
                    favouriteAdapter.setConverterList(favourites);
                    favouriteAdapter.notifyDataSetChanged();
                }
            });

            if ("Favourite".equals(favType)) {
                toggle.setVisibility(View.GONE);
            } else {
                if (CommonAccess.convertersStatMap.get(converter)) {
                    toggle.setChecked(true);
                } else {
                    toggle.setChecked(false);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return converterList.size();
    }

    public List<String> getConverterList() {
        return converterList;
    }

    public void setConverterList(List<String> converterList) {
        this.converterList = converterList;
    }

    public ToggleButton getToggle() {
        return toggle;
    }

    public void setToggle(ToggleButton toggle) {
        this.toggle = toggle;
    }
}