package com.example.aditya.workout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TabMeditate extends Fragment implements View.OnClickListener,
        MediaPlayer.OnPreparedListener {
    SeekBar seekmed;
    Button meditateButton;
    ImageButton muteButton;
    MediaPlayer mp;
    TextView mdistance;
    boolean mediaReady;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_meditate, container, false);

        meditateButton = (Button) root.findViewById(R.id.meditate_button);
        muteButton = (ImageButton) root.findViewById(R.id.mute_button);
        seekmed=(SeekBar) root.findViewById(R.id.seekmediate);
        mdistance = (TextView) root.findViewById(R.id.mdistance);
        meditateButton.setOnClickListener(this);
        muteButton.setOnClickListener(this);
//        bm = new BackgroundMusic(getActivity());
        mp = MediaPlayer.create(getActivity(), R.raw.meditate);
        mp.setOnPreparedListener(this);
//        mp.prepareAsync();

        seekmed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mdistance.setText("" + seekmed.getProgress()/4 + " minutes");
                //Get the thumb bound and get its left value
                int x = seekmed.getThumb().getBounds().left;
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
        return root;


    }


    @Override
    public void onClick(View view) {
        int p;
        AudioManager audio_mngr = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        switch (view.getId()){
            case R.id.mute_button:
                String tag = (String) muteButton.getTag();
                if(tag.equals("muted")) {
                    //if muted and stopped then play
                    if(meditateButton.getText().equals("STOP")) {
                        muteButton.setTag("unmuted");
                        mp.start();
                        muteButton.setImageResource(R.drawable.unmute);
                    }
                }
                else {
                    muteButton.setTag("muted");
                    // LOGIC TO PAUSE MUSIC
                    mp.pause();
                    muteButton.setImageResource(R.drawable.mute);
                }
                break;

            case R.id.meditate_button:
                if(meditateButton.getText().equals("START")){
                    meditateButton.setText("STOP");
               //     mp.start();
                    boolean temp = isChecked("switch_disturb");
                    Log.d("Meditate", "onClick: dnd: " + temp);
                    if(temp){
                        audio_mngr .setRingerMode(AudioManager.RINGER_MODE_SILENT);
                   }

                    muteButton.setImageResource(R.drawable.unmute);


                    p = seekmed.getProgress();
                    p = p/4 + 1;
                    Toast.makeText(getContext(), "Meditation set for: " + String.valueOf(p) + " minutes", Toast.LENGTH_SHORT).show();
                    mp.setLooping(true);
                    mp.start();
//                    temp = isChecked("switch_diturb");
//                    if(!temp){
//                        audio_mngr .setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                    }
          //          mp.start();
                    new CountDownTimer(p*60*1000,300000) {

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub
                            if(mp.isPlaying())
                            {
                                mp.stop();
                            }

                        }
                    }.start();
                }
                else{
                    meditateButton.setText("START");
                    // Logic to stop meditation
                    mp.pause();
                    audio_mngr .setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    muteButton.setImageResource(R.drawable.mute);
                }
                break;

            default:
                break;
        }
    }

    private void startMeditation() {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaReady = true;
    }

    public boolean isChecked(String name){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CUSTOMIZATION",
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, false);
    }


}
