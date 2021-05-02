package com.example.myplaces;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment  {

    Geocoder geocoder;

    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        getActivity().setTitle("Google Map");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_map,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //initial marker
                LatLng city = new LatLng(11.0619439, 76.033372);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title("Malappuram Kerala");
                markerOptions.position(city);
                Marker locationMarker = googleMap.addMarker(markerOptions);
                locationMarker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(city));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        //clear all the marker
                        googleMap.clear();
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                       try {
                           //to get city name
                           List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                           String cityName = addresses.get(0).getAddressLine(0);
                           markerOptions.title(cityName);
                       }
                       catch (Exception e){
                            markerOptions.title(latLng.latitude+" "+latLng.longitude);

                       }
                       //camera zoom
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,3));
                        //adding new mark
                        Marker locationMarker = googleMap.addMarker(markerOptions);
                        locationMarker.showInfoWindow();
                    }
                });

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        showConfirmationPop(getActivity(),marker);

                    }
                });

            }
        });

        return  rootView;
    }

    private void showConfirmationPop(Context context,Marker marker) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("Add Place")
                .setMessage("Are you sure want to add this place")
                .setPositiveButton("Confirm", null)
                .setNegativeButton("Cancel",null)
                .show();
        Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment hm = new HomeFragment();
                double latitude = marker.getPosition().latitude;
                double longitude = marker.getPosition().longitude;
                List<Address> addresses = null;
                String cityName = "";

                String countryName = "";
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    cityName = addresses.get(0).getAddressLine(0);
                    countryName = addresses.get(0).getCountryName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(countryName == null)
                    countryName = "";
                Map map= new Map(cityName,latitude,longitude,countryName);
                HomeFragment f = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("home");
                // update list view
                f.updateListView(map);
                dialog.dismiss();

            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}