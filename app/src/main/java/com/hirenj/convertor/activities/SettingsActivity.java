package com.hirenj.convertor.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.hirenj.convertor.R;
import com.hirenj.convertor.constants.CommonConstants;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    Spinner decimalSettingSpinner;
    SharedPreferences sharedPref;
    Switch themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //getting decimal value if saved by user earlier
        sharedPref = this.getSharedPreferences(CommonConstants.SETTING_PREF,Context.MODE_PRIVATE);
        int decimalValue = sharedPref.getInt(getString(R.string.decimal_value),4);

        decimalSettingSpinner = (Spinner) findViewById(R.id.decimalSettingSpinner);

        ArrayList<String> decimalSettingOptions = new ArrayList<>();
        for(int i=0;i<=10;i++){
            decimalSettingOptions.add(i+"");
        }

        ArrayAdapter<String> adapter  =new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, decimalSettingOptions);
        decimalSettingSpinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(decimalValue+"");
        decimalSettingSpinner.setSelection(spinnerPosition); //Setting previous saved decimal value if any

        setTitle("Preferences");

    }


    public void saveSettings(View view){

        //saving user selected value to shared preference
        int decimalValue = Integer.parseInt(decimalSettingSpinner.getSelectedItem().toString());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.decimal_value), decimalValue);
        editor.apply();


        //close activity after save is pressed
        finish();

    }

    public void cancelSettings(View view){

        //close activity
        finish();

    }

    /*public void changeTheme(View view){
        themeSwitch = (Switch) findViewById(R.id.themeSwitch);

        if(themeSwitch.isChecked()){
            setTheme(R.style.AppThemeDark_NoActionBar);
            recreate();
            getApplication().setTheme(R.style.AppThemeLight);
        }
    }*/
}
