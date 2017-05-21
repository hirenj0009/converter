package com.hirenj.convertor.fragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hirenj.convertor.R;
import com.hirenj.convertor.activities.CommonConverterActivity;
import com.hirenj.convertor.adapters.RecyclerViewAdapter;
import com.hirenj.convertor.common.CommonAccess;
import com.hirenj.convertor.common.RecyclerTouchListener;
import com.hirenj.convertor.common.dragAndDropHelper.SimpleItemTouchHelperCallback;
import com.hirenj.convertor.constants.CommonConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {


    public static RecyclerView favouriteRecyclerList;
    public static List<String> favourites = new ArrayList<>();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SharedPreferences sharedPref;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        favouriteRecyclerList = (RecyclerView) view.findViewById(R.id.favRecyclerList);

        sharedPref = this.getContext().getSharedPreferences(CommonConstants.COMMON_CONV_PREF, Context.MODE_PRIVATE);
        prepareListData();
        RecyclerViewAdapter expFavListAdapter = new RecyclerViewAdapter(favourites, mParam1);
        RecyclerView.LayoutManager favLayoutManager = new LinearLayoutManager(this.getContext());
        favouriteRecyclerList.setLayoutManager(favLayoutManager);
        favouriteRecyclerList.setItemAnimator(new DefaultItemAnimator());
        //favouriteRecyclerList.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        favouriteRecyclerList.setAdapter(expFavListAdapter);

        //Code to make drag and drop items
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(expFavListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(favouriteRecyclerList);


        favouriteRecyclerList.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(), favouriteRecyclerList, new RecyclerTouchListener.ClickListener() {
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

        if (favourites.size() == 0){
            if (sharedPref != null) {
                Gson gson = new Gson();
                favourites = gson.fromJson(sharedPref.getString("commonFav", ""), new TypeToken<List<String>>() {
                }.getType());
            }
        }

        for (String fav : favourites) {
            CommonAccess.convertersStatMap.put(fav, true);
        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        editor.putString("commonFav", gson.toJson(favourites));
        editor.apply();

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
