package com.dicoding.picodiploma.submission4.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.viewmodel.TvShowViewModel;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTvAlertDialogFragment extends DialogFragment implements View.OnClickListener {
    private TvShowViewModel tvShowViewModel;
    @BindView(R.id.txt_yes)
    TextView txtYes;
    @BindView(R.id.txt_no)
    TextView txtNo;
    @BindString(R.string.clear_all_tv_show)
    String clearAllTv;


    public MyTvAlertDialogFragment() {
        // Required empty public constructor
    }

    public static MyTvAlertDialogFragment dialogFragment(String title) {
        MyTvAlertDialogFragment fragment = new MyTvAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tv_alert_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);

        txtYes.setOnClickListener(this);
        txtNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_yes:
                tvShowViewModel.deleteAllTv();
                getFragmentManager().beginTransaction().remove(MyTvAlertDialogFragment.this).commit();
                Toast.makeText(getActivity(), clearAllTv, Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_no:
                getFragmentManager().beginTransaction().remove(MyTvAlertDialogFragment.this).commit();
                break;
        }
    }
}
