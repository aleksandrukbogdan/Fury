package com.example.fury.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fury.MainActivity;
import com.example.fury.R;

import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

import static com.example.fury.MainActivity.flagS;
import static com.example.fury.MainActivity.mediaPlayerHungry;
import static com.example.fury.MainActivity.mediaPlayerSleep;
import static com.example.fury.MainActivity.mediaPlayerWantToilet;
import static com.example.fury.MainActivity.pB_tired;
import static com.example.fury.MainActivity.tamagochi1;
import static com.example.fury.MainActivity.view1;


public class sleeproom_fragment extends Fragment {
    ImageButton btn_sleep1;
    public static GifImageView sleepzzz;
    public static boolean flagSleep = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sleeproom, container,
                false);

        sleepzzz = view.findViewById(R.id.sleepzzz);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_sleep1 = getView().findViewById(R.id.tosleep);
        btn_sleep1.setOnClickListener(onButtonClickListener1);
    }

    ImageButton.OnClickListener onButtonClickListener1 = new ImageButton.OnClickListener() {


        @Override
        public void onClick(View v) {
            flagSleep = true;
            if(MainActivity.loadedsound){
                int streamId = MainActivity.soundPool.play(MainActivity.soundLight, (float)0.5, (float)0.5, 1, 0, 1f);
            }
            if (MainActivity.pB_hungry.getProgress() <= 20 || MainActivity.pB_clean.getProgress() <= 20) {

                if (mediaPlayerSleep != null)
                    mediaPlayerSleep.stop();
                if(MainActivity.pB_clean.getProgress() <= 20){
                    if (mediaPlayerWantToilet!=null)
                        mediaPlayerWantToilet.stop();
                    mediaPlayerWantToilet = MediaPlayer.create(getContext(),R.raw.wanttoilet);
                    mediaPlayerWantToilet.start();
                }else if(MainActivity.pB_hungry.getProgress() <= 20){
                    if (mediaPlayerHungry!=null)
                        mediaPlayerHungry.stop();
                    mediaPlayerHungry = MediaPlayer.create(getContext(),R.raw.hungry);
                    mediaPlayerHungry.start();
                }
                sleepzzz.setVisibility(View.INVISIBLE);
                if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                    view1.setBackgroundResource(R.drawable.sleeproomdark0dirtphase1);
                } else if (MainActivity.tamagochi1.getDirt() > 60) {
                    view1.setBackgroundResource(R.drawable.sleeproomdark0dirtphase2);
                } else if (MainActivity.tamagochi1.getDirt() < 20) {
                    view1.setBackgroundResource(R.drawable.sleeproomdark0);
                }

            } else {


                if (MainActivity.pB_tired.getProgress() < 80) {
                    flagS = false;
                    tamagochi1.sleep();
                    pB_tired.setProgress((int) tamagochi1.getStrength());
                    if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                        view1.setBackgroundResource(R.drawable.sleeproomdarkdirtphase1);
                    } else if (MainActivity.tamagochi1.getDirt() > 60) {
                        view1.setBackgroundResource(R.drawable.sleeproomdarkdirtphase2);
                    } else if (MainActivity.tamagochi1.getDirt() < 20) {
                        view1.setBackgroundResource(R.drawable.sleeproomdark);
                    }
                    sleepzzz.setVisibility(View.VISIBLE);

                } else {
                    if (mediaPlayerSleep != null)
                        mediaPlayerSleep.stop();
                    if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                        view1.setBackgroundResource(R.drawable.sleeproomdark0dirtphase1);
                    } else if (MainActivity.tamagochi1.getDirt() > 60) {
                        view1.setBackgroundResource(R.drawable.sleeproomdark0dirtphase2);
                    } else if (MainActivity.tamagochi1.getDirt() < 20) {
                        view1.setBackgroundResource(R.drawable.sleeproomdark0);
                    }
                    sleepzzz.setVisibility(View.INVISIBLE);

                }

            }
            btn_sleep1.setOnClickListener(onButtonClickListener2);

        }
    };
    ImageButton.OnClickListener onButtonClickListener2 = new ImageButton.OnClickListener() {


        @Override
        public void onClick(View v) {
            flagSleep = false;
            if(MainActivity.loadedsound){
                int streamId = MainActivity.soundPool.play(MainActivity.soundLight, (float)0.5, (float)0.5, 1, 0, 1f);
            }
            if (mediaPlayerSleep != null)
                mediaPlayerSleep.stop();
            if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                view1.setBackgroundResource(R.drawable.sleeproomdirtphase1);
            } else if (MainActivity.tamagochi1.getDirt() > 60) {
                view1.setBackgroundResource(R.drawable.sleeproomdirtphase2);
            } else if (MainActivity.tamagochi1.getDirt() < 20) {
                view1.setBackgroundResource(R.drawable.sleeproom);
            }

            btn_sleep1.setOnClickListener(onButtonClickListener1);
            sleepzzz.setVisibility(View.INVISIBLE);
        }
    };

}

