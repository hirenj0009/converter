package com.hirenj.convertor.adapters;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hirenj.convertor.R;
import com.hirenj.convertor.common.CommonAccess;
import com.hirenj.convertor.fragement.CommonFinFragment;
import com.hirenj.convertor.fragement.CommonFragment;

/**
 * Created by Hiren J on 4/2/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    private String favType;
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, String favType) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.favType = favType;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if("Favourite".equals(_listDataHeader.get(groupPosition))){
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.favourite_list_item, null);
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.favouriteListItem);

            txtListChild.setText(childText);
        }else{

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.converter_list_item, null);
            }

            final ToggleButton togg = (ToggleButton) convertView.findViewById(R.id.favouriteToggle);

            final Fragment fragment;

            if(togg != null){
                togg.setOnClickListener(new ToggleButton.OnClickListener() {

                    public void onClick(View v) {

                        List<String> favourites = null;
                        if("Common".equals(favType)){
                            favourites = CommonFragment.favourites;

                            if(togg.isChecked() && !favourites.contains(childText)){
                                favourites.add(childText);
                                CommonAccess.convertersStatMap.put(childText,true);
                            }else {
                                favourites.remove(childText);
                                CommonAccess.convertersStatMap.put(childText,false);
                            }

                            CommonFragment.favouriteExpandableList.collapseGroup(0);
                            CommonFragment.favouriteExpandableList.expandGroup(0);
                        }else if("Finance".equals(favType)){
                            favourites = CommonFinFragment.favourites;

                            if(togg.isChecked() && !favourites.contains(childText)){
                                favourites.add(childText);
                                CommonAccess.convertersStatMap.put(childText,true);
                            }else {
                                favourites.remove(childText);
                                CommonAccess.convertersStatMap.put(childText,false);
                            }

                            CommonFinFragment.favouriteExpandableList.collapseGroup(0);
                            CommonFinFragment.favouriteExpandableList.expandGroup(0);
                        }
                    }
                });
            }
            TextView txtListChild = (TextView) convertView.findViewById(R.id.ConverterListItem);

            if(CommonAccess.convertersStatMap.get(childText)){
                togg.setChecked(true);
            }else{
                togg.setChecked(false);
            }

            txtListChild.setText(childText);

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {


        super.notifyDataSetChanged();
    }
}