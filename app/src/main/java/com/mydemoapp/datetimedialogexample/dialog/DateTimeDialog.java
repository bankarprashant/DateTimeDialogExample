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
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mydemoapp.datetimedialogexample.R;
import com.mydemoapp.datetimedialogexample.dialog.fragment.DateTimeFragment;
import com.mydemoapp.datetimedialogexample.dialog.model.DateModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DateTimeDialog extends DialogFragment implements DateTimeFragment.IDateTimeChanged {
    private static final String TAG = "DateTimeDialog";
    private DateModel dateTimeModel;
    private IDateTimeDialog iDateTimeDialog;

    public static DateTimeDialog newInstance() {

        Bundle args = new Bundle();

        DateTimeDialog fragment = new DateTimeDialog();
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

        DateTimeFragment dateTimeFragment = DateTimeFragment.newInstance();
        dateTimeFragment.setListener(this);
        getChildFragmentManager().beginTransaction().add(R.id.frameLayout, dateTimeFragment, TAG).commit();

        return view;
    }

    @OnClick(R.id.cancelTextView)
    public void dismissDialog() {
        this.dismiss();
    }

    @OnClick(R.id.doneTextView)
    public void doneClicked() {
        if (iDateTimeDialog == null) {
            Log.e(TAG, "doneClicked() called : Callback listener Not Set");
            return;
        }
        if (dateTimeModel != null) {
            if (dateTimeModel.isDateSet && dateTimeModel.isTimeSet) {
                iDateTimeDialog.onDateTimeSelected(dateTimeModel);
            } else {
                if (!dateTimeModel.isDateSet) {
                    DatePicker datePicker = new DatePicker(getContext());

                    dateTimeModel.year = datePicker.getYear();
                    dateTimeModel.month = datePicker.getMonth();
                    dateTimeModel.day = datePicker.getDayOfMonth();
                } else {
                    TimePicker timePicker = new TimePicker(getContext());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dateTimeModel.hour = timePicker.getHour();
                        dateTimeModel.minute = timePicker.getMinute();
                    } else {
                        dateTimeModel.hour = timePicker.getCurrentHour();
                        dateTimeModel.minute = timePicker.getCurrentMinute();
                    }
                }

                iDateTimeDialog.onDateTimeSelected(dateTimeModel);
            }
        } else {
            DatePicker datePicker = new DatePicker(getContext());
            DateModel dateModel = new DateModel();

            dateModel.year = datePicker.getYear();
            dateModel.month = datePicker.getMonth();
            dateModel.day = datePicker.getDayOfMonth();

            TimePicker timePicker = new TimePicker(getContext());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dateModel.hour = timePicker.getHour();
                dateModel.minute = timePicker.getMinute();
            } else {
                dateModel.hour = timePicker.getCurrentHour();
                dateModel.minute = timePicker.getCurrentMinute();
            }

            iDateTimeDialog.onDateTimeSelected(dateModel);
        }
        this.dismiss();
    }

    @Override
    public void onDateTimeChanged(DateModel dateTimeModel) {
        Log.d(TAG, "onDateTimeChanged() called with: dateTimeModel = [" + dateTimeModel + "]");
        this.dateTimeModel = dateTimeModel;
    }

    public void setListener(IDateTimeDialog iDateTimeDialog) {
        this.iDateTimeDialog = iDateTimeDialog;
    }

    public interface IDateTimeDialog {
        void onDateTimeSelected(DateModel dateTimeModel);
    }
}