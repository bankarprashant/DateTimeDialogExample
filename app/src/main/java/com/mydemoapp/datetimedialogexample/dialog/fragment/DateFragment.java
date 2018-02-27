package com.mydemoapp.datetimedialogexample.dialog.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.mydemoapp.datetimedialogexample.R;
import com.mydemoapp.datetimedialogexample.dialog.model.DateModel;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateFragment extends DialogFragment implements DatePicker.OnDateChangedListener {

    public static final String TAG = "DateFragment";
    private IDateChanged iDateChanged;
    private DateModel dateModel;

    @BindView(R.id.datePicker)
    DatePicker datePicker;

    public static DateFragment newInstance() {
        Bundle args = new Bundle();

        DateFragment fragment = new DateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        ButterKnife.bind(this, view);

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);

        return view;
    }

    public void setListener(IDateChanged iDateChanged) {
        this.iDateChanged = iDateChanged;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        Log.d(TAG, "onDateChanged() called with: datePicker = [" + datePicker
                + "], year = [" + year + "], month = [" + month + "], day = [" + day + "]");
        if (dateModel == null) {
            dateModel = new DateModel();
        }
        if (iDateChanged != null) {
            dateModel.year = year;
            dateModel.month = month;
            dateModel.day = day;
            dateModel.isDateSet = true;
            iDateChanged.onDateChanged(datePicker, dateModel);
        } else {
            Log.e(TAG, "doneClicked() called : Callback listener Not Set");
        }
    }

    public interface IDateChanged {
        void onDateChanged(DatePicker datePicker, DateModel dateModel);
    }
}