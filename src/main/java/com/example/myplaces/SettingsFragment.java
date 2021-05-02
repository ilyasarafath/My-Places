package com.example.myplaces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SettingsFragment extends Fragment {



    public SettingsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Settings");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        //footer home button
        TextView home = v. findViewById(R.id.home);
        //unit change
        RadioGroup unitRadioGrp=  v.findViewById(R.id.tempGroup);
        if(Constant.id != 0){
            RadioButton btn= v.findViewById(Constant.id);
            btn.setChecked(true);
        }


        unitRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn= v.findViewById(checkedId);
                Constant.id = checkedId;
                Constant.unit = btn.getText().toString().toLowerCase();
            }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(),"home")
                        .addToBackStack(null).commit();
            }
        });

        return  v;
    }
}