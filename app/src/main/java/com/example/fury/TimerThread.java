package com.example.fury;

import static com.example.fury.DataBaseHelper.TABLE;
import static com.example.fury.MainActivity.isActive;
import static com.example.fury.MainActivity.level_pB;
import static com.example.fury.MainActivity.level_tV;
import static com.example.fury.MainActivity.pB_clean;
import static com.example.fury.MainActivity.pB_happy;
import static com.example.fury.MainActivity.pB_hungry;
import static com.example.fury.MainActivity.pB_tired;
import static com.example.fury.MainActivity.tamagochi1;
import static com.example.fury.Shell.db;

public class TimerThread extends Thread {

static int scores_db;

    @Override
    public void run() {
        super.run();

        while (isActive) {
            tamagochi1.passTime();
            pB_hungry.setProgress((int) tamagochi1.getHungriness());
            pB_happy.setProgress((int) tamagochi1.getHappiness());
            pB_clean.setProgress((int) tamagochi1.getToilet());
            pB_tired.setProgress((int) tamagochi1.getStrength());
            level_pB.setProgress(MainActivity.level.getExperience());
            level_tV.setText("" + MainActivity.level.getLevel());
            scores_db++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }
}