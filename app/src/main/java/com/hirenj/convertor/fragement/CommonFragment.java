package com.hirenj.convertor.fragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hirenj.convertor.R;
import com.hirenj.convertor.activities.CommonConverterActivity;
import com.hirenj.convertor.adapters.RecyclerViewAdapter;
import com.hirenj.convertor.common.CommonAccess;
import com.hirenj.convertor.common.RecyclerTouchListener;
import com.hirenj.convertor.common.dragAndDropHelper.SimpleItemTouchHelperCallback;
import com.hirenj.convertor.constants.CommonConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonFragment extends Fragment {


    RecyclerView commonCategoryRecyclerListView;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common, container, false);
        sharedPref = this.getContext().getSharedPreferences(CommonConstants.COMMON_CONV_PREF, Context.MODE_PRIVATE);

        commonCategoryRecyclerListView = (RecyclerView) view.findViewById(R.id.commonRecyclerList);
        prepareListData();
        RecyclerViewAdapter expListAdapter = new RecyclerViewAdapter(converters, mParam1);
        RecyclerView.LayoutManager commonLayoutManager = new LinearLayoutManager(this.getContext());
        commonCategoryRecyclerListView.setLayoutManager(commonLayoutManager);

        //animator
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        commonCategoryRecyclerListView.setItemAnimator(itemAnimator);


        //commonCategoryRecyclerListView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        commonCategoryRecyclerListView.setAdapter(expListAdapter);

        //Code to make drag and drop items
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(expListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(commonCategoryRecyclerListView);


        commonCategoryRecyclerListView.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(), commonCategoryRecyclerListView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                final TextView converterText = (TextView) view.findViewById(R.id.ConverterListItem);

                converterText.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CommonConverterActivity.class);
                        intent.putExtra("type", converterText.getText().toString());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    private void prepareListData() {

        converters = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.common_converter_array)));

        int i = 0;
        while (converters.size() > i) {
            if (!CommonAccess.convertersStatMap.containsKey(converters.get(i))) {
                CommonAccess.convertersStatMap.put(converters.get(i), false);
            }
            i++;
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
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
