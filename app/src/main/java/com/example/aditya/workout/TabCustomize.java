package com.example.aditya.workout;

/**
 * Created by aditya on 13/3/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;

public class TabCustomize extends Fragment implements View.OnClickListener {
    //String WORKOUT = "WORKOUT";
    String CUSTOMIZATION = "CUSTOMIZATION";
    SharedPreferences sharedPreferences;
    Switch switch_adaptive;
    CheckBox legs, arms, stomach, fullbody, core;
    CheckBox cardio, strength, fatloss, massgain, flexibility;
    Switch switch_disturb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_customize, container, false);

        /** Initializaing all element
         *  Only works after onCreateView method
         */
        identifyViews(root);
        setOnItemClickListener();
        restorePreferences();

        return root;
    }


    private void setOnItemClickListener() {
        switch_adaptive.setOnClickListener(this);
        legs.setOnClickListener(this);
        arms.setOnClickListener(this);
        stomach.setOnClickListener(this);
        fullbody.setOnClickListener(this);
        core.setOnClickListener(this);
        cardio.setOnClickListener(this);
        strength.setOnClickListener(this);
        fatloss.setOnClickListener(this);
        massgain.setOnClickListener(this);
        flexibility.setOnClickListener(this);
        switch_disturb.setOnClickListener(this);
    }


    public void identifyViews(View root){

        switch_adaptive = (Switch) root.findViewById(R.id.adaptiveswitch);
        switch_disturb = (Switch) root.findViewById(R.id.disturbswitch);
        legs = (CheckBox) root.findViewById(R.id.checklegs);
        arms = (CheckBox) root.findViewById(R.id.checkarms);
        stomach = (CheckBox) root.findViewById(R.id.checkstomach);
        fullbody = (CheckBox) root.findViewById(R.id.checkfullbody);
        core = (CheckBox) root.findViewById(R.id.checkcore);
        cardio = (CheckBox) root.findViewById(R.id.checkcardio);
        strength = (CheckBox) root.findViewById(R.id.checkstrength);
        fatloss = (CheckBox) root.findViewById(R.id.checkfatloss);
        massgain = (CheckBox) root.findViewById(R.id.checkmassgain);
        flexibility = (CheckBox) root.findViewById(R.id.checkflexibility);

    }


    public boolean isChecked(String name){
        boolean temp;
        try {
            switch (name) {
                case "switch_adaptive":
                    temp = switch_adaptive.isChecked();
                    break;

                case "switch_disturb":
                    temp = switch_disturb.isChecked();
                    break;

                case "legs":
                    temp = legs.isChecked();
                    break;

                case "arms":
                    temp = arms.isChecked();
                    break;

                case "stomach":
                    temp = stomach.isChecked();
                    break;

                case "fullbody":
                    temp = fullbody.isChecked();
                    break;

                case "core":
                    temp = core.isChecked();
                    break;

                case "cardio":
                    temp = cardio.isChecked();
                    break;

                case "strength":
                    temp = strength.isChecked();
                    break;

                case "fatloss":
                    temp = fatloss.isChecked();
                    break;

                case "massgain":
                    temp = massgain.isChecked();
                    break;

                case "flexibility":
                    temp = flexibility.isChecked();
                    break;

                default:
                    Log.d(CUSTOMIZATION, "checked: Checkbox was not found! ");
                    temp = false;
            }
        }
        catch (NullPointerException e){
            Log.d(CUSTOMIZATION, "isChecked: Checkbox was not Ticked! (NPE)");
            temp = false;
        }
        return temp;
    }


    public void restorePreferences(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CUSTOMIZATION",
                Context.MODE_PRIVATE);
        String[] checkBoxes = {"switch_adaptive", "switch_disturb", "legs", "arms", "stomach",
                "fullbody", "core", "cardio", "strength", "fatloss",
                "massgain", "flexibility"};

        for (String name : checkBoxes){
            switch (name) {
                case "switch_adaptive":
                    switch_adaptive.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "switch_disturb":
                    switch_disturb.setChecked(sharedPreferences.getBoolean(name, false));

                case "legs":
                    legs.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "arms":
                    arms.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "stomach":
                    stomach.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "fullbody":
                    fullbody.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "core":
                    core.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "cardio":
                    cardio.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "strength":
                    strength.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "fatloss":
                    fatloss.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "massgain":
                    massgain.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                case "flexibility":
                    flexibility.setChecked(sharedPreferences.getBoolean(name, false));
                    break;

                default:
                    Log.d(CUSTOMIZATION, "restorePreferences: Checkbox was not found! ");
            }

        }
    }


    @Override
    public void onClick(View view) {
        sharedPreferences = getActivity().getSharedPreferences("CUSTOMIZATION",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String[] checkBoxes = {"switch_adaptive", "switch_disturb","legs", "arms", "stomach",
                "fullbody", "core", "cardio", "strength", "fatloss",
                "massgain", "flexibility"};

        for (String cb : checkBoxes){
            editor.putBoolean(cb,isChecked(cb));
            Log.d(CUSTOMIZATION, "onClick: " + cb + " is " + isChecked(cb));
        }

        editor.putBoolean("KEY", false);


        editor.commit();
    }
}