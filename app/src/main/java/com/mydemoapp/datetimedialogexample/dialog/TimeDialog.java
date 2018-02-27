package com.mydemoapp.datetimedialogexample.dialog;

import android.os.Build;
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
import com.mydemoapp.datetimedialogexample.dialog.fragment.TimeFragment;
import com.mydemoapp.datetimedialogexample.dialog.model.TimeModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeDialog extends DialogFragment implements TimeFragment.ITimeChanged {
    public static final String TAG = "TimeDialog";

    private ITimeDialog iTimeDialog;
    private TimePicker timePicker;
    private TimeModel timeModel;

    public static TimeDialog newInstance() {

        Bundle args = new Bundle();

        TimeDialog fragment = new TimeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_dialog, container, false);
        ButterKnife.bind(this, view);

        TimeFragment timeFragment = TimeFragment.newInstance();
        timeFragment.setListener(this);
        getChildFragmentManager().beginTransaction().add(R.id.frameLayout, timeFragment, TAG).commit();

        return view;
    }

    @OnClick(R.id.cancelTextView)
    public void dismissDialog() {
        this.dismiss();
    }

    @OnClick(R.id.doneTextView)
    public void doneClicked() {
        if (iTimeDialog != null && timePicker != null && timeModel != null) {
            iTimeDialog.onTimeChanged(timePicker, timeModel);
        } else if (iTimeDialog != null) {
            timePicker = new TimePicker(getContext());
            timeModel = new TimeModel();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timeModel.hour = timePicker.getHour();
                timeModel.minute = timePicker.getMinute();
            } else {
                timeModel.hour = timePicker.getCurrentHour();
                timeModel.minute = timePicker.getCurrentMinute();
            }

            iTimeDialog.onTimeChanged(timePicker, timeModel);
        } else {
            Log.e(TAG, "doneClicked() called : Callback listener Not Set");
        }
        this.dismiss();
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, TimeModel timeModel) {
        Log.d(TAG, "onTimeChanged() called with: timePicker = [" + timePicker +
                "], timeModel = [" + timeModel + "]");
        this.timePicker = timePicker;
        this.timeModel = timeModel;

    }

    public void setListener(ITimeDialog iTimeDialog) {
        this.iTimeDialog = iTimeDialog;
    }

    public interface ITimeDialog {
        void onTimeChanged(TimePicker timePicker, TimeModel timeModel);
    }
}
