package com.example.myplaces;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;


public class HomeFragment extends Fragment {
    Context context;

    ListView listView;
     ArrayList<Map> arrayList;
    MapAdapter adapter;
    ProgressDialog progressDialog;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        getActivity().setTitle("Places");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView=inflater.inflate(R.layout.fragment_home,container,false);

        TextView settings = homeView .findViewById(R.id.settings);
        //Calling setting fragmnet
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment(),"settings")
                        .addToBackStack(null).commit();
            }
        });


        FloatingActionButton addPlace = homeView.findViewById(R.id.add_place);
        listView = homeView.findViewById(R.id.place_list_view);
        TextView  noItem = homeView.findViewById(R.id.no_item);
        noItem.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        if (arrayList == null){
            arrayList = new ArrayList<Map>();
            noItem.setVisibility(View.VISIBLE);
        }
        else
            noItem.setVisibility(View.GONE);
        adapter = new MapAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment(),"map")
                            .addToBackStack(null).commit();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map map = adapter.getItem(position);
                progressDialog.show();
                ProviderInterface providerInterface =new ProviderInterface() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Bundle bundle = new Bundle();
                        bundle.putString("weatherData", response);
                        CityScreenFragment city = new CityScreenFragment();
                        city.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, city,"city")
                                .addToBackStack(null).commit();
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), "No network available", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                };
                String url = "lat="+map.getLatitude()+"&lon="+map.getLongitude()+"&units="+Constant.unit+"&appid="+Constant.URL_KEY;
                String requestUrl = Constant.BASE_URL+url;
                APIService apiService=new APIService(providerInterface);
                apiService.getRequest(getContext(), requestUrl);
            }
        });


        return homeView;


    }


    public  void updateListView(Map map){
        arrayList.add(map);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}