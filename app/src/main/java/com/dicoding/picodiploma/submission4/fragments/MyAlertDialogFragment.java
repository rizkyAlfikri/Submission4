package com.dicoding.picodiploma.submission4.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.viewmodel.MovieViewModel;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MyAlertDialogFragment extends DialogFragment implements View.OnClickListener {
    private MovieViewModel movieViewModel;
    @BindView(R.id.txt_yes)
    TextView txtYes;
    @BindView(R.id.txt_no)
    TextView txtNo;
    @BindString(R.string.clear_all_movie_data)
    String clearAllMovie;


    public MyAlertDialogFragment() {
        // Required empty public constructor
    }

    public static MyAlertDialogFragment dialogFragment(String title) {
        MyAlertDialogFragment fragment = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_alert_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        txtYes.setOnClickListener(this);
        txtNo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_yes:
                movieViewModel.deleteAllMovie();
                getFragmentManager().beginTransaction().remove(MyAlertDialogFragment.this).commit();
                Toast.makeText(getActivity(), clearAllMovie, Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_no:
                getFragmentManager().beginTransaction().remove(MyAlertDialogFragment.this).commit();
                break;
        }
    }
}
