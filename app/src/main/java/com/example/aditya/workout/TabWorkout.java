package com.example.aditya.workout;

/**
 * Add Multiple Images reamining!
 * Saykhedkar will download
 */

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TabWorkout extends Fragment implements View.OnClickListener {

    int[]  workouts;
    Button btn_workout;
    SeekBar seekIntensity;
    Cursor c,c1;
    TextView mdistance, name_tv;
    DatabaseHelper ahiitDatabaseHelper;
    List<String> listWithoutDuplicates;
    MediaPlayer mp;
    private String WORKOUT = "WORKOUT";
    int hour;
    int minute;

    // Allows us to notify the user that something happened in the background
    NotificationManager notificationManager;

    // Used to track notifications
    int notifID = 33;

    // Used to track if notification is active in the task bar
    boolean isNotificActive = false;
    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.tab_workout, container, false);

        btn_workout = (Button) root.findViewById(R.id.btn_workout);
        seekIntensity = (SeekBar) root.findViewById(R.id.seekintensity);
        mdistance = (TextView) root.findViewById(R.id.mdistance);
        name_tv = (TextView) root.findViewById(R.id.name);
        btn_workout.setOnClickListener(this);
        mp = MediaPlayer.create(getActivity(), R.raw.workout);

        seekIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mdistance.setText("level " +  seekIntensity.getProgress()/20 );
                //Get the thumb bound and get its left value
                int x = seekIntensity.getThumb().getBounds().left;
                //set the left value to textview x value
                mdistance.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        updateProgressValues(5);
        return root;
    }


    public void updateProgressValues(int progress) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TRACK",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        progress += sharedPreferences.getInt("progress", 5);
        editor.putInt("progress", progress);
    }


    public void updateSuccess(Cursor c){
        c.moveToFirst();
        for (int i = 0; i < 13; i++){
            ahiitDatabaseHelper.ahiitDatabase.execSQL("Update Exercise "
                    + " Set eperformcount = eperformcount + 1"
                    + " Where eid = " + c.getInt(0));
            c.moveToNext();
        }
        c.moveToFirst();
        c.close();
        if(c.isClosed()){
            Toast.makeText(getContext(),"Database updated for perform count",Toast.LENGTH_LONG).show();
        }
    }

    ArrayList<String> names = new ArrayList<>();


    public ArrayList<String> getWorkout() {
        ArrayList<String> workoutIds = new ArrayList<>();

        ahiitDatabaseHelper = new DatabaseHelper(getActivity());
        try{
            ahiitDatabaseHelper.createDatabase();
        }catch (IOException ioe){
            throw new Error("Unable to create Database");
        }

        try{
            ahiitDatabaseHelper.openDatabase();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
        boolean temp = isChecked("switch_adaptive");

        if (temp) {
            List<String> list = new ArrayList<String>();
            List<String> final_list = new ArrayList<>();
            if (isChecked("legs")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where elegs = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }

            if (isChecked("arms")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where earms = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }
            if (isChecked("stomach")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where estomach = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }

            if (isChecked("fullbody")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where efullbody = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }

            if (isChecked("core")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where ecore = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }

            if (isChecked("cardio")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where ecardio = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }

            if (isChecked("strength")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where estrength = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }


            if (isChecked("fatloss")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where efatloss = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }


            if (isChecked("massgain")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where emassgain = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }

            }

            if (isChecked("flexibility")) {

                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid from Exercise"
                        + " Where eflexibility = 1", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            list.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                }
            }

            sortByFreq(list);
            Collections.reverse(listWithoutDuplicates);
            ahiitDatabaseHelper.ahiitDatabase.execSQL("DROP TABLE IF EXISTS temp");
            ahiitDatabaseHelper.ahiitDatabase.execSQL("CREATE TABLE temp (id INT PRIMARY KEY , min INT )");



            int user_intensity = seekIntensity.getProgress();
            user_intensity = user_intensity / 20;

            for (int i = 0; i < 20; i++) {
                c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eintensity, eperformcount "
                        + " From Exercise Where eid = " + listWithoutDuplicates.get(i), null);
                int min_wt = 0;
                if (c != null) {
                    c.moveToFirst();
                    int intensity = c.getInt(0);
                    int perform_count = c.getInt(1);
                    min_wt = perform_count + ((Math.abs(intensity - user_intensity) * perform_count) / 5);
                }

                ahiitDatabaseHelper.ahiitDatabase.execSQL("INSERT INTO temp VALUES ( " +
                        listWithoutDuplicates.get(i) + " ,  " + min_wt + " )");
            }

            c1 = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select id from temp " +
                    " order by min ASC ", null);

            c1.moveToFirst();
            for (int i = 0; i < 13; i++){
                final_list.add(c1.getString(0));
                c1.moveToNext();
            }
            String final_string = final_list.toString().replace("[", "")  //remove the right bracket
                    .replace("]", "")  //remove the left bracket
                    .trim();
            c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid , ename from Exercise " +
                    " where eid IN ( " + final_string + " ) order by eintensity" ,null);

            if (c.moveToFirst()) {
                for (int i = 0; i < 9; i++) {
//                  Toast.makeText(getContext(), c.getString(0) + " " + c.getString(1), Toast.LENGTH_SHORT).show();
                    workoutIds.add(c.getString(0));
                    names.add(c.getString(1));
                    c.moveToNext();
                }
            }
            if (c.moveToLast()) {
                for (int i = 0; i < 4; i++) {
                    workoutIds.add(c.getString(0));
                    //Toast.makeText(getContext(), c.getString(0) + " " + c.getString(1), Toast.LENGTH_SHORT).show();
                    names.add(c.getString(1));
                    c.moveToPrevious();
                }
            }

            //Code to update after workout count!
            updateSuccess(c);

        }

        //code for NonAdaptive Workout
        else {

            c = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select eid , ename from Exercise" +
                    " order by eperformcount ASC, eintensity ASC", null);
            if (c.moveToFirst()) {
                for (int i = 0; i < 9; i++) {
                    //Toast.makeText(getContext(), c.getString(0) + " " + c.getString(1), Toast.LENGTH_LONG).show();
                    workoutIds.add(c.getString(0));
                    names.add(c.getString(1));
                    c.moveToNext();
                }
            }
            if (c.moveToLast()) {
                for (int i = 0; i < 4; i++) {
                    //Toast.makeText(getContext(), c.getString(0) + " " + c.getString(1), Toast.LENGTH_LONG).show();
                    workoutIds.add(c.getString(0));
                    names.add(c.getString(1));
                    c.moveToPrevious();
                }
            }
            updateSuccess(c);
        }
        return workoutIds;
    }


    void sortByFreq(List<String> a) {
        Map<String, Integer> map = new TreeMap<>();

       /* Logic to place the elements to Map */
        for (int i = 0; i < a.size(); i++) {
            if (map.get(a.get(i)) == null) {
                map.put(a.get(i), 1);
            } else {
                int frequency = map.get(a.get(i));
                map.put(a.get(i), frequency + 1);
            }
        }

        //System.out.println(map);

        List list = new LinkedList(map.entrySet());

       /* Sort the list elements based on frequency */
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object obj1, Object obj2) {
                return ((Comparable) ((Map.Entry) (obj1)).getValue())
                        .compareTo(((Map.Entry) (obj2)).getValue());
            }
        });

        int count = 0;

       /* Place the elements in to the array based on frequency */
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();

            String key = (String) entry.getKey();
            int val = (int) entry.getValue();

            for (int i = 0; i < val; i++) {
                a.add(count, String.valueOf(key));
                count++;
            }
        }
        LinkedHashSet<String> listToSet = new LinkedHashSet<String>(a);
        listWithoutDuplicates = new ArrayList<String>(listToSet);
    }


    public boolean isChecked(String name){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CUSTOMIZATION",
                Context.MODE_PRIVATE);
        //Code to debug begins
        String[] checkBoxes = {"switch_adaptive", "legs", "arms", "stomach",
                "fullbody", "core", "cardio", "strength", "fatloss",
                "massgain", "flexibility"};

        for (String cb : checkBoxes){
            Log.d(WORKOUT, "isChecked: " + cb + " is " + sharedPreferences.getBoolean(name, false));
        }
        //Code to debug ends
        return sharedPreferences.getBoolean(name, false);
    }


    public void  startWorkout() {
        ImageView image = (ImageView) getView().findViewById(R.id.image);
        ArrayList<String> workoutIds = getWorkout();
        //Log.d(WORKOUT, "startWorkout: " + workoutIds);
        workouts = new int[13];
        int i=0;

        for (String id: workoutIds){
            Log.d(WORKOUT, "startWorkout: " + id);
            String mDrawableName = "ahiit" + id;
            int resID = getResources().getIdentifier(mDrawableName, "drawable",
                    getContext().getPackageName());
            workouts[i++] = resID;
            Log.d(WORKOUT, "startWorkout: res: " + resID);
        }
//        Uncomment Below line for testing
//        int[] workouts1 = {R.drawable.hiit1,R.drawable.hiit2,R.drawable.hiit3,R.drawable.hiit4, };
//        workouts = workouts1;
        //ArrayList<String> names = getWorkoutName(workoutIds);
        Log.d(WORKOUT, "startWorkout:names: "+ names);
        updateWorkoutDay();
        updateLevel();
        animate(image, workouts, 0, false );
    }



    /**
     * Code to animate Image transition (USAGE)
     * ImageView demoImage = (ImageView) findViewById(R.id.DemoImage);
     * int imagesToShow[] = { R.drawable.image1, R.drawable.image2,R.drawable.image3 };
     * animate(demoImage, imagesToShow, 0,false);
     */
    private void animate(final ImageView imageView, final int images[], final int imageIndex,
                         final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        name_tv.setText(names.get(imageIndex));
        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 30000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever == true){
                        animate(imageView, images, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View view) {
        ImageView image = (ImageView) getView().findViewById(R.id.image);
        if (btn_workout.getText().equals("START")) {
            btn_workout.setText("STOP");
            mp.start();
            startWorkout();
           /*
            ahiitDatabaseHelper.ahiitDatabase.execSQL("CREATE TABLE IF NOT EXISTS workout (id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , hour INT, minute INT)");

            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            ahiitDatabaseHelper.ahiitDatabase.execSQL("Insert INTO workout ( hour , minute) values (" + hour + " , " + minute + " )");

            c1 = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select hour , count (hour) As hour_occurence FROM workout GROUP BY hour ORDER BY hour_occurence DESC LIMIT 1", null);
            c1.moveToFirst();
            hour = c1.getInt(0);
            c1 = ahiitDatabaseHelper.ahiitDatabase.rawQuery("Select minute , count (minute) As minute_occurence FROM workout GROUP BY minute ORDER BY minute_occurence DESC LIMIT 1", null);
            c1.moveToFirst();
            minute = c1.getInt(0);

            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);

            // Define our intention of executing AlertReceiver
            Intent alertIntent = new Intent(this.getActivity(),AlertReceiver.class);

            // Allows you to schedule for your application to do something at a later date
            // even if it is in he background or isn't active
            mContext = getContext();
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

            // set() schedules an alarm to trigger
            // Trigger for alertIntent to fire in 5 seconds
            // FLAG_UPDATE_CURRENT : Update the Intent if active
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    24*60*60*1000 ,PendingIntent.getBroadcast(getContext(), 1, alertIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT));
           */

            Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;

            // Define our intention of executing AlertReceiver
            Intent alertIntent = new Intent(this.getActivity(), AlertReceiver.class);

            // Allows you to schedule for your application to do something at a later date
            // even if it is in he background or isn't active
            mContext = getContext();
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

            // set() schedules an alarm to trigger
            // Trigger for alertIntent to fire in 5 seconds
            // FLAG_UPDATE_CURRENT : Update the Intent if active
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
                    PendingIntent.getBroadcast(getContext(), 1, alertIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT));
//            btn_workout.setText("START");

        }
        else{
            btn_workout.setText("START");
            name_tv.setText("");
            image.setImageResource(android.R.color.transparent);
            workouts = new int[1];
            mp.pause();
        }

    }


    public void updateLevel(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TRACK",
                Context.MODE_PRIVATE);

        int level = sharedPreferences.getInt("progress", 5);
        level += 5;
        Log.d(WORKOUT, "updateLevel: "+ level);
        // change below 50 value to 5.. 50 is just for testin..
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("progress");
        editor.putInt("progress", level);
        editor.commit();


        level = sharedPreferences.getInt("progress", 15);
        Log.d(WORKOUT, "updateLevel: "+ level);
    }


    public void updateWorkoutDay(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TRACK",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        ArraySet<String> temp = new ArraySet<>();

        Set<String> datesSet = sharedPreferences.getStringSet("dates", temp);
        Date d =  new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Log.d(WORKOUT, "updateWorkoutDay: "+ dateFormat.format(d));
        String date = dateFormat.format(d).toString();
        datesSet.add(date);
        editor.putStringSet("dates", datesSet);

        editor.commit();
    }


}