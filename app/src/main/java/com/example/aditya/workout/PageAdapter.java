package com.example.aditya.workout;

/**
 * Created by aditya on 13/3/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabWorkout tabWorkout = new TabWorkout();
                return tabWorkout;

            case 1:
                TabMeditate tabMeditate = new TabMeditate();
                return tabMeditate;

            case 2:
                TabTrack tabTrack = new TabTrack();
                return  tabTrack;

            case 3:
                TabCustomize tabCustomize = new TabCustomize();
                return tabCustomize;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
