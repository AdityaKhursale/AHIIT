package com.example.aditya.workout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Workout"));
        tabLayout.addTab(tabLayout.newTab().setText("Meditate"));
        tabLayout.addTab(tabLayout.newTab().setText("Track"));
        tabLayout.addTab(tabLayout.newTab().setText("Customize"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_share);

        // Fetch and store ShareActionProvider
        //mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String myMsg = getMyMsg();
        Log.d("MAIN", "onCreateOptions: " + myMsg );
        shareIntent.putExtra(Intent.EXTRA_TEXT, myMsg);

        mShareActionProvider.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MAIN", "onOptionsItemSelected: " + item);
        return super.onOptionsItemSelected(item);
    }

    public String getMyMsg(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("TRACK",
                Context.MODE_PRIVATE);
        int level = sharedPreferences.getInt("progress", 5);
        Log.d("MAIN", "getMyMsg: " + level);
        level = level / 100;
        String msg = "I am a ";

        if(level == 0){
            msg += "ROOKIE";
        }
        else if(level == 1){
            msg += "ADVANCED";
        }
        else if(level == 2){
            msg += "PROFESSIONAL";
        }
        else if(level == 3){
            msg += "MASTER";
        }
        else{
            msg += "NINJA";
        }

        msg += "! What level are you? Use AHIIT to get fit..";
        return msg;
    }


    public void watchVideo(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
             //   Uri.parse("http://www.youtube.com/playlist?list="));
                 Uri.parse("https://goo.gl/dHztIZ"));
        startActivity(intent);
    }
}
