package com.example.fury.fury_animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fury.R;

import java.io.IOException;

public class fury_feed extends Fragment {


    ImageView furyfeed;
    static AnimationDrawable furyfeed_animation;
    Handler animationFeed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fury_feed, container,
                false);


        furyfeed = view.findViewById(R.id.furyfeed);
        furyfeed.setBackgroundResource(R.drawable.furyfeed_animation);
        furyfeed_animation = (AnimationDrawable) furyfeed.getBackground();
        furyfeed_animation.start();


        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}



