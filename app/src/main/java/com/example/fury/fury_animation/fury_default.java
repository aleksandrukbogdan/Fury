package com.example.fury.fury_animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fury.MainActivity;
import com.example.fury.R;

public class fury_default extends Fragment {

    ImageButton btn_fury;

    static ImageView furystandart;
    AnimationDrawable furystandart_animation, furyfight_animation, furypet_animation;
    Handler animation;
    boolean flagPet = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fury_default, container,
                false);

        btn_fury = view.findViewById(R.id.btn_fury);

        btn_fury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = new Handler();
                animation.post(new Runnable() {
                    @Override
                    public void run() {
                        furystandart = view.findViewById(R.id.furystandart);
                        furystandart.setBackgroundResource(R.drawable.furyfight_animation);
                        furyfight_animation = (AnimationDrawable) furystandart.getBackground();
                        furyfight_animation.start();
                        if(MainActivity.loadedsound){
                            int streamId = MainActivity.soundPool.play(MainActivity.soundFight, (float)0.5, (float)0.5, 1, 0, 1f);
                        }
                    }
                });
                animation.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        furyfight_animation.stop();
                        furystandart = view.findViewById(R.id.furystandart);
                        furystandart.setBackgroundResource(R.drawable.furystandart_animation);
                        furystandart_animation = (AnimationDrawable) furystandart.getBackground();
                        furystandart_animation.start();
                    }
                }, 2000);
            }
        });
        btn_fury.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                flagPet = true;
                return true;
            }
        });
        btn_fury.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (animation != null) return true;
                        animation = new Handler();
                        animation.postDelayed(mAction, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (animation == null) return true;
                        animation.removeCallbacks(mAction);
                        if (furypet_animation!=null)
                            furypet_animation.stop();

                        furystandart = view.findViewById(R.id.furystandart);
                        furystandart.setBackgroundResource(R.drawable.furystandart_animation);
                        furystandart_animation = (AnimationDrawable) furystandart.getBackground();
                        furystandart_animation.start();

                        animation = null;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        animation = null;
                        mAction = null;
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {

                    if (furyfight_animation!=null)
                        furyfight_animation.stop();

                    MainActivity.tamagochi1.pet();
                    MainActivity.pB_happy.setProgress((int) MainActivity.tamagochi1.getHappiness());

                    furystandart = view.findViewById(R.id.furystandart);
                    furystandart.setBackgroundResource(R.drawable.furypet_animation);
                    furypet_animation = (AnimationDrawable) furystandart.getBackground();
                    furypet_animation.start();
                    if(MainActivity.loadedsound){
                        int streamId = MainActivity.soundPool.play(MainActivity.soundPet, (float)0.5, (float)0.5, 1, 0, 1f);
                    }
                    if (animation!=null)
                        animation.postDelayed(this, 2000);
                }
            };
        });


        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        furystandart = getView().findViewById(R.id.furystandart);
        furystandart.setBackgroundResource(R.drawable.furystandart_animation);
        furystandart_animation = (AnimationDrawable) furystandart.getBackground();
        furystandart_animation.start();

    }


}



