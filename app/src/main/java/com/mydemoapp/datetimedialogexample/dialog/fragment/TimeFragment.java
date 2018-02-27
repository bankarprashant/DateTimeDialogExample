package com.mydemoapp.datetimedialogexample.dialog.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.mydemoapp.datetimedialogexample.R;
import com.mydemoapp.datetimedialogexample.dialog.model.TimeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeFragment extends DialogFragment implements TimePicker.OnTimeChangedListener {
    public static final String TAG = "TimeFragment";
    private ITimeChanged iTimeChanged;
    private TimeModel timeModel;

    @BindView(R.id.timePicker)
    TimePicker timePicker;

    public static TimeFragment newInstance() {
        Bundle args = new Bundle();

        TimeFragment fragment = new TimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        ButterKnife.bind(this, view);

        timePicker.setOnTimeChangedListener(this);

        return view;
    }

    public void setListener(ITimeChanged iTimeChanged) {
        this.iTimeChanged = iTimeChanged;
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
        Log.d(TAG, "onTimeChanged() called with: timePicker = [" + timePicker
                + "], hour = [" + hour + "], minute = [" + minute + "]");
        if (timeModel == null) {
            timeModel = new TimeModel();
        }
        if (iTimeChanged != null) {
            timeModel.hour = hour;
            timeModel.minute = minute;
            timeModel.isTimeSet = true;
            iTimeChanged.onTimeChanged(timePicker, timeModel);
        } else {
            Log.e(TAG, "doneClicked() called : Callback listener Not Set");
        }
    }

    public interface ITimeChanged {
        void onTimeChanged(TimePicker timePicker, TimeModel timeModel);
    }
}