package com.example.fury;

public class tamagochi {

    private float hungriness = 0;
    private float happiness = 0;
    private float strength = 0;
    private float toilet = 0;
    private float dirt = 0;

    public tamagochi(float hungriness, float happiness, float strength, float toilet, float dirt) {
        this.hungriness = hungriness;
        this.happiness = happiness;
        this.strength = strength;
        this.toilet = toilet;
        this.dirt = dirt;
    }

    public void feed(long feed) {
        hungriness += feed;
        MainActivity.level.setExperience(MainActivity.level.getExperience() + (int) feed);
        MainActivity.level_pB.setProgress(MainActivity.level.getExperience());
        MainActivity.level.newLevel();
        if (hungriness > 100) {
            hungriness = 100;
        }
    }

    public void gotoilet() {

        if (toilet < 100) {

            toilet += 10;
            MainActivity.level.setExperience(MainActivity.level.getExperience() + 10);
            MainActivity.level_pB.setProgress(MainActivity.level.getExperience());
            MainActivity.level.newLevel();

        }
    }

    public void pet() {
        happiness += 15;
        if (happiness > 100) {
            happiness = 100;
        }

        MainActivity.level.setExperience(MainActivity.level.getExperience() + 5);
        MainActivity.level_pB.setProgress(MainActivity.level.getExperience());
        MainActivity.level.newLevel();

    }

    public void sleep() {
        strength += 1;

        MainActivity.level.setExperience(MainActivity.level.getExperience() + 5);
        MainActivity.level_pB.setProgress(MainActivity.level.getExperience());
        MainActivity.level.newLevel();

    }

    public float getHungriness() {
        return hungriness;
    }

    public float getHappiness() {
        return happiness;
    }

    public float getStrength() {
        return strength;
    }

    public float getToilet() {
        return toilet;
    }

    public float getDirt() {
        return dirt;
    }

    public void setHungriness(float hungriness) {
        this.hungriness = hungriness;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public void setToilet(float toilet) {
        this.toilet = toilet;
    }

    public void setDirt(float dirt) {
        this.dirt = dirt;
    }

    public void wash() {
        dirt -= 15;

        MainActivity.level.setExperience(MainActivity.level.getExperience() + 5);
        MainActivity.level_pB.setProgress(MainActivity.level.getExperience());
        MainActivity.level.newLevel();

    }

    public void passTime() {

        hungriness -= 0.1;
        toilet -= 0.1;
        happiness -= 0.1;
        strength -= 0.1;
        dirt += 0.1;

        if (strength < 0)
            strength = 0;

        if (hungriness < 0)
            hungriness = 0;

        if (toilet < 0)
            toilet = 0;

        if (toilet > 100) {
            toilet = 100;
        }

        if (happiness < 0)
            happiness = 0;

        if (dirt >= 100) {
            dirt = 100;
        }
        if (dirt < 0) {
            dirt = 0;
        }


    }


}