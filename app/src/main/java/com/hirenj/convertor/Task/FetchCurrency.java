package com.hirenj.convertor.Task;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Hiren J on 3/26/2017.
 */

public class FetchCurrency extends AsyncTask<String,Void,String> {

    static Map<String, Double> valueMap = new HashMap<>();
    Calendar cal = new GregorianCalendar();

    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection conn;

        try {
            url = new URL(urls[0]);
            conn = (HttpURLConnection) url.openConnection();

            InputStream in = conn.getInputStream();
            InputStreamReader reader  = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1){
                char curr = (char) data;
                result += curr;
                data = reader.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public Map<String, Double> doFetch(String url){

        String response;
        Calendar calCheck = new GregorianCalendar();
        if(valueMap.isEmpty() || calCheck.compareTo(cal)>0){
            try {
                response = execute(url).get();

                if(response != ""){
                    JSONObject resJson = new JSONObject(response);

                    String base = (String) resJson.get("base");
                    String date = (String) resJson.get("date");
                    String[] rateDate= date.split("-");
                    cal.set(Integer.parseInt(rateDate[0]),Integer.parseInt(rateDate[1]),Integer.parseInt(rateDate[2]));

                    JSONObject rates = resJson.getJSONObject("rates");

                    Iterator<String> currs = rates.keys();

                    while (currs.hasNext()){
                        String curr = currs.next();
                        valueMap.put(curr,(Double)rates.get(curr));
                    }
                    valueMap.put(base,1.0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valueMap;
    }
}
