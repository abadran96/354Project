package com.example.anthony.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_graph);

        get_json();

       /* GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.graph_choice, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    ArrayList<Integer> date = new ArrayList<>();
    public void get_json() {
        String json;

        try {
            InputStream is = getAssets().open("historical_weather.json");
            Toast.makeText(getApplicationContext(),"now here", Toast.LENGTH_LONG).show();
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();


            json = new String(buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                if(obj.getString("Month").equals("4")) {
                    date.add(obj.getInt("MeanTemp_C"));
//Ookok so basically, I need user input to choose what the x values need to be and what the y values need to be...
                }
            }


            GraphView graph;
            graph = (GraphView) findViewById(R.id.graph);

            Integer[] y = date.toArray(new Integer[date.size()]);

           int[] x = {0,1,2,3,4,5,6,7,8,9,10};
           //  int[] y = {0,1,2,3,4,5,6,7,8,9,10};
            DataPoint[] dp = new DataPoint[x.length];

            for(int i = 0;i < x.length; i++){
                dp[i] = new DataPoint(x[i], y[i]);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);

        graph.addSeries(series);

            Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
