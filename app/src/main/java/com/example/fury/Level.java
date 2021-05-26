package com.example.fury;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;



import static com.example.fury.MainActivity.coins;
import static com.example.fury.MainActivity.mTextCoins;

public class Level {

    Handler handler;
    AnimationDrawable furyhappy_animation;
    private int level = 1;
    private int experience = 0;
    private int reward = 20;

    public Level(int level, int experience) {
        this.level = level;
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void newLevel(){

        if (experience>=500){
            level++;
            MainActivity.level_tV.setText("" + level);
            this.experience-=500;
            coins.setCoins(coins.getCoins() + reward);
            mTextCoins.setText("" + coins.getCoins());
            this.reward+=5;

            if(MainActivity.loadedsound){
                int streamId = MainActivity.soundPool.play(MainActivity.soundNewLevel, (float)0.4, (float)0.4, 1, 0, 1f);
            }

           handler = new Handler();

           handler.post(new Runnable() {
               @Override
               public void run(){
                   MainActivity.fragment_fury_main.setVisibility(View.INVISIBLE);
                   MainActivity.fury_feed.setVisibility(View.INVISIBLE);
                   MainActivity.fury_happy.setVisibility(View.VISIBLE);
                   MainActivity.fury_happy.setBackgroundResource(R.drawable.furyhappy_animation);
                   furyhappy_animation = (AnimationDrawable) MainActivity.fury_happy.getBackground();
                   furyhappy_animation.start();
                   if(MainActivity.loadedsound){
                       int streamId = MainActivity.soundPool.play(MainActivity.soundHappy, (float)0.5, (float)0.5, 1, 0, 1f);
                   }
               }
           });

           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   furyhappy_animation.stop();
                   MainActivity.fragment_fury_main.setVisibility(View.VISIBLE);

                   MainActivity.fury_happy.setVisibility(View.INVISIBLE);
               }
           }, 1600);



        }

    }

}
