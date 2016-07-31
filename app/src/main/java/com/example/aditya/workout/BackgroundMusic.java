package com.example.aditya.workout;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;

/**
 * Created by Singhaniya Ankit on 3/29/2016.
 */
public class BackgroundMusic extends AsyncTask<Activity, Void, Void> {
    private int resID;
    Activity mActivity;
    MediaPlayer player;

    //new MyAsyncTask(true).execute(maybe_other_params);
    public BackgroundMusic(Activity activity){
        super();
        this.mActivity = activity;
        //this.resID = resID;
    }
    @Override
    protected Void doInBackground(Activity... ids) {
        player = MediaPlayer.create(mActivity, R.raw.meditate);
//        MediaPlayer.create(MainActivity.class, R.raw.meditate);
        player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        player.pause();
    }
}
