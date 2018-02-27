package com.mydemoapp.datetimedialogexample.dialog.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mydemoapp.datetimedialogexample.R;
import com.mydemoapp.datetimedialogexample.dialog.adapter.DateTimePagerAdapter;
import com.mydemoapp.datetimedialogexample.dialog.model.DateModel;
import com.mydemoapp.datetimedialogexample.dialog.model.TimeModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateTimeFragment extends DialogFragment implements DateFragment.IDateChanged
        , TimeFragment.ITimeChanged {

    private static final String TAG = "DateTimeFragment";
    private DateModel dateTimeModel;
    private IDateTimeChanged iDateTimeChanged;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    public static DateTimeFragment newInstance() {
        Bundle args = new Bundle();

        DateTimeFragment fragment = new DateTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_time, container, false);
        ButterKnife.bind(this, view);

        DateTimePagerAdapter dateTimePagerAdapter = new DateTimePagerAdapter(getChildFragmentManager());
        dateTimePagerAdapter.setFragments(getFragments());
        dateTimePagerAdapter.setTitles(getTitles());
        viewPager.setAdapter(dateTimePagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        DateFragment dateFragment = DateFragment.newInstance();
        dateFragment.setListener(this);
        TimeFragment timeFragment = TimeFragment.newInstance();
        timeFragment.setListener(this);
        fragments.add(dateFragment);
        fragments.add(timeFragment);
        return fragments;
    }

    private ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.date));
        titles.add(getString(R.string.time));
        return titles;
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, TimeModel timeModel) {
        Log.d(TAG, "onTimeChanged() called with: timePicker = [" + timePicker +
                "], timeModel = [" + timeModel + "]");
        if (dateTimeModel == null) {
            dateTimeModel = new DateModel();
        }
        if (iDateTimeChanged != null) {
            dateTimeModel.isTimeSet = true;
            dateTimeModel.hour = timeModel.hour;
            dateTimeModel.minute = timeModel.minute;
            dateTimeModel.second = timeModel.second;
            iDateTimeChanged.onDateTimeChanged(dateTimeModel);
        } else {
            Log.e(TAG, "doneClicked() called : Callback listener Not Set");
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, DateModel dateModel) {
        Log.d(TAG, "onDateChanged() called with: datePicker = [" + datePicker +
                "], dateModel = [" + dateModel + "]");
        viewPager.setCurrentItem(1, true);
        if (dateTimeModel == null) {
            dateTimeModel = new DateModel();
        }
        if (iDateTimeChanged != null) {
            dateTimeModel.isDateSet = true;
            dateTimeModel.year = dateModel.year;
            dateTimeModel.month = dateModel.month;
            dateTimeModel.day = dateModel.day;
            iDateTimeChanged.onDateTimeChanged(dateTimeModel);
        } else {
            Log.e(TAG, "doneClicked() called : Callback listener Not Set");
        }
    }

    public void setListener(IDateTimeChanged iDateTimeChanged) {
        this.iDateTimeChanged = iDateTimeChanged;
    }

    public interface IDateTimeChanged {
        void onDateTimeChanged(DateModel dateTimeModel);
    }
}