package com.example.alena.tututest.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.alena.tututest.DirectionActivity;
import com.example.alena.tututest.MainActivity;
import com.example.alena.tututest.R;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by alena on 23.10.2016.
 */

public class MainFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    static final private int CHOOSE_FROM_STATION = 0;
    static final private int CHOOSE_TO_STATION = 1;

    private TextView mTextViewTime;
    private TextView mTextViewFrom;
    private TextView mTextViewTo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Расписание");

        mTextViewTime = (TextView) view.findViewById(R.id.tv_time);
        mTextViewFrom = (TextView) view.findViewById(R.id.from_et);
        mTextViewTo = (TextView) view.findViewById(R.id.to_et);

        mTextViewFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DirectionActivity.class);
                intent.putExtra(DirectionActivity.DIRECTION, DirectionActivity.DIRECTION_FROM);
                startActivityForResult(intent, CHOOSE_FROM_STATION);
            }
        });

        mTextViewTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DirectionActivity.class);
                intent.putExtra(DirectionActivity.DIRECTION, DirectionActivity.DIRECTION_TO);
                startActivityForResult(intent, CHOOSE_TO_STATION);
            }
        });

        mTextViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        mTextViewTime.setText("" + day + "-" + (month+1) + "-" + year);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String stationString = data.getStringExtra(DirectionActivity.CHOOSE);

            if (requestCode == CHOOSE_FROM_STATION) {
                mTextViewFrom.setTextSize(20);
                mTextViewFrom.setText(stationString);
                mTextViewFrom.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

            } else if (requestCode == CHOOSE_TO_STATION) {
                mTextViewTo.setTextSize(20);
                mTextViewTo.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                mTextViewTo.setText(stationString);
            }
        }
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getChildFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        final DatePickerDialog.OnDateSetListener callback;

        public DatePickerFragment(DatePickerDialog.OnDateSetListener callback) {
            this.callback = callback;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), callback, year, month, day);

        }
    }
}
