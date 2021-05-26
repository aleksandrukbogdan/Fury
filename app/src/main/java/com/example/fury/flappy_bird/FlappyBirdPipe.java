package com.example.fury.flappy_bird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FlappyBirdPipe extends FlappyBirdBaseObject {

    public static  int speed;

    public FlappyBirdPipe(float x, float y, int width, int height){

        super(x, y, width, height);

        Timer timer = new Timer();
        timer.schedule(new UpdateTimerTask(),0,20000);


    }

    public void draw(Canvas canvas){
        this.x-=speed;
        canvas.drawBitmap(this.bm, this.x, this.y, null);
    }

    public void randomY(){
        Random r = new Random();
        this.y = r.nextInt(225)-225;
    }

    @Override
    public void setBm(Bitmap bm) {
        this.bm = Bitmap.createScaledBitmap(bm, width, height, true);
    }
}
class UpdateTimerTask extends TimerTask{
    @Override
    public void run() {

        FlappyBirdPipe.speed = 10* FlappyBirdConstants.SCREEN_WIDTH/1920;

    }
}
