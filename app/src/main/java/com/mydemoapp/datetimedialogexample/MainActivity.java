package com.mydemoapp.datetimedialogexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mydemoapp.datetimedialogexample.dialog.DateDialog;
import com.mydemoapp.datetimedialogexample.dialog.DateTimeDialog;
import com.mydemoapp.datetimedialogexample.dialog.TimeDialog;
import com.mydemoapp.datetimedialogexample.dialog.model.DateModel;
import com.mydemoapp.datetimedialogexample.dialog.model.TimeModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DateDialog.IDateDialog,
        TimeDialog.ITimeDialog, DateTimeDialog.IDateTimeDialog {

    private static final String TAG = "MainActivity";

    @BindView(R.id.dateTimeTextView)
    TextView dateTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dateTimeDialog)
    public void showDateTimeDialog() {
        DateTimeDialog dateTimeDialog = DateTimeDialog.newInstance();
        dateTimeDialog.setListener(this);
        dateTimeDialog.show(getSupportFragmentManager(), TAG);
    }

    @OnClick(R.id.dateDialog)
    public void showDateDialog() {
        DateDialog dateDialog = DateDialog.newInstance();
        dateDialog.setListener(this);
        dateDialog.show(getSupportFragmentManager(), TAG);
    }

    @OnClick(R.id.timeDialog)
    public void showTimeDialog() {
        TimeDialog timeDialog = TimeDialog.newInstance();
        timeDialog.setListener(this);
        timeDialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, DateModel dateModel) {
        Log.d(TAG, "onDateChanged() called with: datePicker = [" + datePicker +
                "], dateModel = [" + dateModel + "]");
        dateTimeTextView.setText(dateModel.toString());
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, TimeModel timeModel) {
        Log.d(TAG, "onTimeChanged() called with: timePicker = [" + timePicker +
                "], timeModel = [" + timeModel + "]");
        dateTimeTextView.setText(timeModel.toString());
    }

    @Override
    public void onDateTimeSelected(DateModel dateTimeModel) {
        Log.d(TAG, "onDateTimeSelected() called with: dateTimeModel = [" + dateTimeModel + "]");
        dateTimeTextView.setText(dateTimeModel.toString());
    }
}