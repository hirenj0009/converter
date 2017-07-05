package com.hirenj.convertor.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hirenj.convertor.R;
import com.hirenj.convertor.adapters.ResultRecyclerViewAdapter;
import com.hirenj.convertor.common.RecyclerTouchListener;
import com.hirenj.convertor.constants.CommonConstants;
import com.hirenj.convertor.convert.Convert;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonConverterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    EditText topLengthValue;
    Spinner topLengthSpinner;

    RecyclerView resultList;
    ResultRecyclerViewAdapter adapter;

    String Selected_top_length_value;
    int decimalValue;

    ArrayAdapter<CharSequence> spinnerAdapter;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_converter);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            type = "Length";
        } else {
            type = extras.getString("type");

            if (extras.getString("lastVal") != null) {
                String lastVal = extras.getString("lastVal");
                topLengthValue.setText(lastVal);
            }
        }

        topLengthSpinner = (Spinner) findViewById(R.id.topOptionsSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout11
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.length_array, android.R.layout.simple_spinner_item);

        if (type.equals("Temperature")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Area")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.area_array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Volume")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.volume_array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Weight")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.weight_array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Time")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.time_array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Length")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.length_array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Currency")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.currency_Array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Speed")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.speed_array, android.R.layout.simple_spinner_item);
        } else if (type.equals("Pressure")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.pressure_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Velocity - Angular")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.velocity_angular_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Acceleration")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.acceleration_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Acceleration - Angular")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.acceleration_angular_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Density")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.density_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Specific Volume")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.specific_volume_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Moment of Inertia")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.moment_of_inertia_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Moment of Force")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.moment_of_force_array, android.R.layout.simple_spinner_item);
        }else if (type.equals("Torque")) {
            spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.torque_array, android.R.layout.simple_spinner_item);
        }


        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        topLengthSpinner.setAdapter(spinnerAdapter);

        resultList = (RecyclerView) findViewById(R.id.ResultList);

        topLengthValue = (EditText) findViewById(R.id.topValueText);
        topLengthValue.addTextChangedListener(topWatch);

        topLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                topTextChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //getting decimal value from shared preference
        SharedPreferences sharedPref = this.getSharedPreferences(CommonConstants.SETTING_PREF, Context.MODE_PRIVATE);
        decimalValue = sharedPref.getInt(getString(R.string.decimal_value), 4);

        setTitle(type + " Converter");
    }


    TextWatcher topWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (topLengthValue.hasFocus()) {
                topTextChange();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void setParams() {
        Selected_top_length_value = topLengthSpinner.getSelectedItem().toString();
        if ("Currency".equals(type)) {
            Selected_top_length_value = topLengthSpinner.getSelectedItem().toString().split("-")[0].trim();
        }
    }

    private void topTextChange() {
        try {
            setParams();
            Convert conv = new Convert();
            conv.setNumberFormat(decimalValue);

            ArrayList<String> resultArray;
            ArrayList<String> resultUnitArray;

            if (!topLengthValue.getText().toString().equals("") && !topLengthValue.getText().toString().equals("-")) {
                conv.setResultArray(Double.parseDouble(topLengthValue.getText().toString()), Selected_top_length_value, type);

                resultArray = conv.getResultArray();
                resultUnitArray = conv.getResultUnitArray();

                ArrayList<HashMap<String, String>> list = new ArrayList<>();
                ;

                int i = 0;
                for (String value : resultArray) {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put(CommonConstants.FIRST_COLUMN, value);
                    temp.put(CommonConstants.SECOND_COLUMN, resultUnitArray.get(i));
                    list.add(temp);
                    i++;
                }

                adapter = new ResultRecyclerViewAdapter(list);

                RecyclerView.LayoutManager commonLayoutManager = new LinearLayoutManager(this);
                resultList.setLayoutManager(commonLayoutManager);

                //animator
                RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                resultList.setItemAnimator(itemAnimator);

                resultList.setAdapter(adapter);


                resultList.addOnItemTouchListener(new RecyclerTouchListener(this, resultList, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        final TextView converterText = (TextView) view.findViewById(R.id.valueListItem);

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("value", converterText.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        final TextView converterText = (TextView) view.findViewById(R.id.unitListItem);

                        int spinnerPosition = spinnerAdapter.getPosition(converterText.getText().toString());
                        topLengthSpinner.setSelection(spinnerPosition);
                    }
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //to send data to fragment who started this activity
    @Override
    protected void onDestroy() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(type, topLengthValue.getText().toString());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.searchMain);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
