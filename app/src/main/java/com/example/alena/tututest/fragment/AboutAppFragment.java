package com.example.alena.tututest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alena.tututest.MainActivity;
import com.example.alena.tututest.R;

/**
 * Created by alena on 23.10.2016.
 */

public class AboutAppFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app,container,false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("О приложении");

        return view;
    }
}
