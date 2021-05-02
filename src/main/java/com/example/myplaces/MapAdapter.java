package com.example.myplaces;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;


public class MapAdapter extends ArrayAdapter<Map> {


    private  Context context;
    public ArrayList<Map> mapArrayList;

    public MapAdapter(@NonNull Context context, ArrayList<Map> mapArrayList) {
        super(context, 0,mapArrayList);
        this.context = context;
        this.mapArrayList = mapArrayList;

    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Map getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Map item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

      Map map = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_map, parent, false);
        }
        TextView location = (TextView) convertView.findViewById(R.id.location);
        TextView country = (TextView) convertView.findViewById(R.id.country);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);

        location.setText(map.locationName);
        country.setText(map.country);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmPopup(map);
//                mapArrayList.remove(map);
//                notifyDataSetChanged();
            }
        });

        return convertView;
    }
    private void confirmPopup(Map map) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete")
                .setPositiveButton("Confirm", null)
                .setNegativeButton("Cancel",null)
                .show();
        Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapArrayList.remove(map);
                notifyDataSetChanged();
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
