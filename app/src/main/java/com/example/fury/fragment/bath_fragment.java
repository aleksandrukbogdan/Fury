package com.example.fury.fragment;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fury.R;

import static com.example.fury.MainActivity.flag;
import static com.example.fury.MainActivity.flagT;
import static com.example.fury.MainActivity.furycontainer;
import static com.example.fury.MainActivity.mediaPlayerWash;
import static com.example.fury.MainActivity.tamagochi1;
import static com.example.fury.MainActivity.view1;

public class bath_fragment extends Fragment {

    public static boolean flagWash = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bath, container,
                false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton btn_clean = getView().findViewById(R.id.btn_clean);
        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagWash = true;
                furycontainer.setVisibility(View.INVISIBLE);
                view1.setBackgroundResource(R.drawable.bathroomcertain);
                mediaPlayerWash = MediaPlayer.create(getContext(),R.raw.wash);
                mediaPlayerWash.start();
                if (tamagochi1.getDirt() <= 0) {
                    if (mediaPlayerWash!=null)
                        mediaPlayerWash.stop();
                }
                if (!flag && !flagT) {
                    if (mediaPlayerWash!=null)
                        mediaPlayerWash.stop();
                }
            }
        });
    }
}
