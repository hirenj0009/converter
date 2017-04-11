package com.hirenj.convertor.fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hirenj.convertor.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DigitalConverter.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DigitalConverter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DigitalConverter extends Fragment {

    EditText topDigiValue;
    TextView bottomDigiValue;
    Spinner topDigiSpinner;
    Spinner bottomDigiSpinner;
    String Selected_top_digi_value;
    String Selected_bottom_digi_value;

    private OnFragmentInteractionListener mListener;

    public DigitalConverter() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DigitalConverter newInstance(String param1, String param2) {
        DigitalConverter fragment = new DigitalConverter();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_digital_converter, container, false);
        topDigiSpinner = (Spinner) view.findViewById(R.id.topOptionsSpinner);
        bottomDigiSpinner = (Spinner) view.findViewById(R.id.bottomTimeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.digi_Storage_Array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        topDigiSpinner.setAdapter(adapter);
        bottomDigiSpinner.setAdapter(adapter);



        topDigiValue = (EditText) view.findViewById(R.id.topValueText);
        topDigiValue.addTextChangedListener(topWatch);

        bottomDigiValue = (EditText) view.findViewById(R.id.bottomTimeText);
        bottomDigiValue.addTextChangedListener(bottomWatch);

        topDigiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                topTextChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bottomDigiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bottomTextChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }


    TextWatcher topWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(topDigiValue.hasFocus()){
                topTextChange();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    TextWatcher bottomWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(bottomDigiValue.hasFocus()){
                bottomTextChange();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

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


    private void setParams(){

        Selected_top_digi_value = topDigiSpinner.getSelectedItem().toString();
        Selected_bottom_digi_value = bottomDigiSpinner.getSelectedItem().toString();
        Selected_top_digi_value = Selected_top_digi_value.substring(Selected_top_digi_value.indexOf("(")+1, Selected_top_digi_value.lastIndexOf(")"));
        Selected_bottom_digi_value = Selected_bottom_digi_value.substring(Selected_bottom_digi_value.indexOf("(")+1, Selected_bottom_digi_value.lastIndexOf(")"));

    }

    private void topTextChange(){
        /*setParams();
        Convert conv = new Convert();
        double convertedValue = 0;
        if(!topDigiValue.getText().toString().equals("")){
            convertedValue = conv.convertValue(Double.parseDouble(topDigiValue.getText().toString()), Selected_top_digi_value, Selected_bottom_digi_value,"digital");
        }
        bottomDigiValue.setText(convertedValue+"");*/
    }

    private void bottomTextChange(){
        {
            /*setParams();
            Convert conv = new Convert();
            double convertedValue = 0;
            if(!bottomDigiValue.getText().toString().equals("")){
                convertedValue = conv.convertValue(Double.parseDouble(bottomDigiValue.getText().toString()), Selected_bottom_digi_value, Selected_top_digi_value,"digital");
            }
            topDigiValue.setText(convertedValue+"");*/
        }
    }
}
