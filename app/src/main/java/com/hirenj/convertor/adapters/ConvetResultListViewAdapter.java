package com.hirenj.convertor.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hirenj.convertor.R;
import com.hirenj.convertor.constants.CommonConstants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hiren J on 3/21/2017.
 */

public class ConvetResultListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;

    public ConvetResultListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row, null);

            txtFirst = (TextView) convertView.findViewById(R.id.valueText);
            txtSecond = (TextView) convertView.findViewById(R.id.unitText);
        } else {
            txtFirst = (TextView) convertView.findViewById(R.id.valueText);
            txtSecond = (TextView) convertView.findViewById(R.id.unitText);
        }

        HashMap<String, String> map = list.get(position);
        txtFirst.setText(map.get(CommonConstants.FIRST_COLUMN));
        txtSecond.setText(map.get(CommonConstants.SECOND_COLUMN));
        return convertView;
    }
}