package com.mydemoapp.datetimedialogexample.dialog.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mydemoapp.datetimedialogexample.dialog.ViewPagerWrapper;

import java.util.ArrayList;

public class DateTimePagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "DateTimePagerAdapter";

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    private int mCurrentPosition = -1;

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public DateTimePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            ViewPagerWrapper pager = (ViewPagerWrapper) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }
}
