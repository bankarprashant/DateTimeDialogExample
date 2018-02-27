package com.mydemoapp.datetimedialogexample.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import com.mydemoapp.datetimedialogexample.R;
import com.mydemoapp.datetimedialogexample.dialog.fragment.DateFragment;
import com.mydemoapp.datetimedialogexample.dialog.model.DateModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DateDialog extends DialogFragment implements DateFragment.IDateChanged {
    public static final String TAG = "DateDialog";

    private IDateDialog iDateDialog;
    private DateModel dateModel;
    private DatePicker datePicker;

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    public static DateDialog newInstance() {

        Bundle args = new Bundle();

        DateDialog fragment = new DateDialog();
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

        DateFragment dateFragment = DateFragment.newInstance();
        dateFragment.setListener(this);
        getChildFragmentManager().beginTransaction().add(R.id.frameLayout, dateFragment, TAG).commit();

        return view;
    }

    @OnClick(R.id.cancelTextView)
    public void dismissDialog() {
        this.dismiss();
    }

    @OnClick(R.id.doneTextView)
    public void doneClicked() {
        if (iDateDialog != null && datePicker != null && dateModel != null) {
            iDateDialog.onDateChanged(datePicker, dateModel);
        } else if (iDateDialog != null) {
            datePicker = new DatePicker(getContext());
            dateModel = new DateModel();

            dateModel.year = datePicker.getYear();
            dateModel.month = datePicker.getMonth();
            dateModel.day = datePicker.getDayOfMonth();

            iDateDialog.onDateChanged(datePicker, dateModel);
        } else {
            Log.e(TAG, "doneClicked() called : Callback listener Not Set");
        }
        this.dismiss();
    }

    @Override
    public void onDateChanged(DatePicker datePicker, DateModel dateModel) {
        Log.d(TAG, "onDateChanged() called with: datePicker = [" + datePicker +
                "], dateModel = [" + dateModel + "]");
        this.datePicker = datePicker;
        this.dateModel = dateModel;
    }

    public void setListener(IDateDialog iDateDialog) {
        this.iDateDialog = iDateDialog;
    }

    public interface IDateDialog {
        void onDateChanged(DatePicker datePicker, DateModel dateModel);
    }
}
