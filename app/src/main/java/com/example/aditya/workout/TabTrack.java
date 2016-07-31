package com.example.aditya.workout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by Singhaniya Ankit on 3/28/2016.
 */
public class TabTrack extends Fragment {
    ProgressBar levelBar;
    TextView startLevel, endLevel;
    CalendarPickerView calendar;
    int progress;
    String TRACK = "TRACK";
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_track, container, false);

        levelBar = (ProgressBar) root.findViewById(R.id.level_bar);
        startLevel = (TextView) root.findViewById(R.id.start_level);
        endLevel = (TextView) root.findViewById(R.id.end_level);
        calendar = (CalendarPickerView) root.findViewById(R.id.calander_view);
        // Create shared preference for the level value default is one.
        updateProgressBar();

        //Setting up Calender(EXternal)
        updateCalendar();

        return root;
    }




    public void updateProgressBar(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TRACK",
                Context.MODE_PRIVATE);
        progress = sharedPreferences.getInt("progress", 5);
        Log.d(TRACK, "updateLevel: "+ progress);
        int level = progress/100;
        progress %= 100;
        setLevels(level);

        new Thread(new Runnable() {
            public void run() {
                // Update the progress bar
                mHandler.post(new Runnable() {
                    public void run() {
                        levelBar.setProgress(progress);
                    }
                });
            }
        }).start();
    }



    private void setLevels(int level) {
        /**
         * 0 : Rookie
         * 1 : Advanced
         * 2 : Professional
         * 3 : Master
         * 4 : Ninja
         */
        String[] levels = {"Rookie", "Advanced", "Professional", "Master",
            "Ninja"};
        switch (level){

            case 0:
                startLevel.setText(levels[0]);
                endLevel.setText(levels[1]);
                break;

            case 1:
                startLevel.setText(levels[1]);
                endLevel.setText(levels[2]);
                break;

            case 2:
                startLevel.setText(levels[2]);
                endLevel.setText(levels[3]);
                break;

            case 3:
                startLevel.setText(levels[3]);
                endLevel.setText(levels[4]);
                break;

            default:
                Log.w(TRACK, "setLevels: level value not in range[0-3]" );
        }
    }


    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public void updateWorkoutDates(String day){

    }


    public  void updateCalendar(){
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);

        Date today = parseDate("2016-06-01");
//        calendar.init(today, cal.getTime()).withSelectedDate(today);
        ArrayList<Date> dates = new ArrayList<>();

        dates.add(parseDate("2016-06-02"));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TRACK",
                Context.MODE_PRIVATE);
        ArraySet<String> temp = new ArraySet<>();

        Set<String> datesSet = sharedPreferences.getStringSet("dates", temp);
        for(String d: datesSet){
            Log.d(TRACK, "updateCalendar: "+ d);
            dates.add(parseDate(d));
        }

        Log.d(TRACK, "onCreateView: " + today);
        Log.d(TRACK, "onCreateView: "+ dates.get(0));
        Log.d(TRACK, "onCreateView: " );
        calendar.init(today, cal.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withHighlightedDates(dates);
    }
}
