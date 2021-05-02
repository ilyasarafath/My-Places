package com.example.myplaces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityScreenFragment extends Fragment {



    public CityScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Place Details");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cityFragment=inflater.inflate(R.layout.fragment_city_screen,container,false);
        TextView location = cityFragment.findViewById(R.id.locationName);
        TextView weather = cityFragment.findViewById(R.id.weather);
        TextView temperature= cityFragment.findViewById(R.id.temp);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String response = bundle.getString("weatherData");
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("name") && !jsonObject.isNull("name")) {
                    location.setText(jsonObject.getString("name"));
                }
                if(jsonObject.has("weather") && !jsonObject.isNull("weather")){
                    JSONObject weatherArray = (JSONObject) jsonObject.getJSONArray("weather").get(0);
                    weather.setText(weatherArray.getString("main"));
                }
                if(jsonObject.has("main") && !jsonObject.isNull("main")) {
                    temperature.setText(jsonObject.getJSONObject("main").getString("temp"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



//        try{
//
//            JSONObject json = new JSONObject(response);
//        }
//        catch (Exception e){}

        return  cityFragment;
    }
}