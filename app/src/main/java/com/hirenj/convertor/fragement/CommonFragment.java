package com.hirenj.convertor.fragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hirenj.convertor.R;
import com.hirenj.convertor.activities.CommonConverterActivity;
import com.hirenj.convertor.adapters.ExpandableListAdapter;
import com.hirenj.convertor.common.CommonAccess;
import com.hirenj.convertor.constants.CommonConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonFragment extends Fragment {

    public static ExpandableListView favouriteExpandableList;
    ExpandableListView converterCategoryExpandableList;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    List<String> favListDataHeader;
    HashMap<String, List<String>> favListDataChild;

    public static List<String> favourites;
    public static List<String> converters;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPref;

    public CommonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static CommonFragment newInstance(String param1, String param2) {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common, container, false);
        sharedPref = this.getContext().getSharedPreferences(CommonConstants.COMMON_CONV_PREF,Context.MODE_PRIVATE);

        favouriteExpandableList = (ExpandableListView) view.findViewById(R.id.favouriteExpandableList);

        prepareListData("Favourite");
        ExpandableListAdapter expFavListAdapter = new ExpandableListAdapter(this.getContext(), favListDataHeader, favListDataChild,mParam1);
        favouriteExpandableList.setAdapter(expFavListAdapter);
        favouriteExpandableList.expandGroup(0);
        favouriteExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long l) {

                String value = favListDataChild.get(
                        favListDataHeader.get(groupPosition)).get(
                        childPosition);

                Intent intent = new Intent(getActivity(), CommonConverterActivity.class);
                intent.putExtra("type",value);
                startActivity(intent);

                return false;
            }
        });


        converterCategoryExpandableList = (ExpandableListView) view.findViewById(R.id.converterCategoryExpandableList);
        prepareListData("Converters");
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this.getContext(), listDataHeader, listDataChild,mParam1);
        converterCategoryExpandableList.setAdapter(expListAdapter);
        if(favourites.size() < 5){
            converterCategoryExpandableList.expandGroup(0);
        }

        converterCategoryExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long l) {

                String value = listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition);

                Intent intent = new Intent(getActivity(), CommonConverterActivity.class);
                intent.putExtra("type",value);
                startActivity(intent);

                return false;
            }
        });



        return view;
    }

    private void prepareListData(String listType) {

        if("Favourite".equals(listType)){
            favListDataHeader = new ArrayList<>();
            favListDataChild = new HashMap<>();

            favListDataHeader.add("Favourite");

            if(sharedPref != null){
                Gson gson = new Gson();
                favourites = gson.fromJson(sharedPref.getString("commonFav",""),new TypeToken<List<String>>(){}.getType());
            }

            if(favourites == null){
                favourites = new ArrayList<>();
            }else{
                for(String fav:favourites){
                    CommonAccess.convertersStatMap.put(fav,true);
                }
            }

            favListDataChild.put(favListDataHeader.get(0), favourites);


        }else{
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<>();

            // Adding child data
            listDataHeader.add("Converters");

            // Adding child data
            converters = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.common_converter_array)));


            int i=0;
            while (converters.size()>i){
                if(!CommonAccess.convertersStatMap.containsKey(converters.get(i))){
                    CommonAccess.convertersStatMap.put(converters.get(i),false);
                }
                i++;
            }
            // Header, Child data
            listDataChild.put(listDataHeader.get(0), converters);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        Gson gson = new Gson();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("commonFav",gson.toJson(favourites));
        editor.apply();

        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
