package com.example.fury.flappy_bird;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.fury.R;

import java.util.ArrayList;

import static com.example.fury.MainActivity.coins;
import static com.example.fury.MainActivity.mTextCoins;


public class FlappyBirdGameView extends View {

    private FlappyBirdBird bird;
    private Handler handler;
    private Runnable r;
    private ArrayList<FlappyBirdPipe> arrPipes;
    private int sumpipe, distance;

    private int score, bestscore = 0;
    private boolean start;

    private Context context;

    private int soundJump;
    private float volume;
    private boolean loadedsound;
    private SoundPool soundPool;

    public static long fbcoins=0;


    public FlappyBirdGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if(sp!=null){

            bestscore = sp.getInt("bestscore", 0);

        }
        score = 0;
        start = false;
        initBird();
        initPipe();
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        if(Build.VERSION.SDK_INT>=21){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();

        }else{

            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        }

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadedsound = true;
            }
        });

        soundJump = this.soundPool.load(context, R.raw.jump_02, 1);

    }

    private void initPipe() {
        sumpipe = 6;
        distance= 350* FlappyBirdConstants.SCREEN_HEIGHT/1080;
        arrPipes = new ArrayList<>();
        for (int i = 0; i < sumpipe; i++) {

            if(i<sumpipe/2){

                this.arrPipes.add(new FlappyBirdPipe(FlappyBirdConstants.SCREEN_WIDTH
                        +i*((FlappyBirdConstants.SCREEN_WIDTH+200* FlappyBirdConstants.SCREEN_WIDTH/1920)/(sumpipe/2)),
                        0, 200* FlappyBirdConstants.SCREEN_WIDTH/1920, FlappyBirdConstants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.flappybird_pipe2));
                this.arrPipes.get(this.arrPipes.size()-1).randomY();


            }else{

                this.arrPipes.add(new FlappyBirdPipe(this.arrPipes.get(i-sumpipe/2).getX(), this.arrPipes.get(i-sumpipe/2).getY()
                        +this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance, 200* FlappyBirdConstants.SCREEN_WIDTH/1920, FlappyBirdConstants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.flappybird_pipe1));

            }

        }

    }

    private void initBird() {
        bird = new FlappyBirdBird();
        bird.setWidth(100* FlappyBirdConstants.SCREEN_WIDTH/1480);
        bird.setHeight(100* FlappyBirdConstants.SCREEN_HEIGHT/1480);
        bird.setX(100* FlappyBirdConstants.SCREEN_WIDTH/700);
        bird.setY(FlappyBirdConstants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.flappybird_bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.flappybird_bird2));
        bird.setArrBms(arrBms);
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        if(start){

            bird.draw(canvas);
            for (int i = 0; i < sumpipe; i++) {

                if(bird.getRect().intersect(arrPipes.get(i).getRect()) || bird.getY()>FlappyBirdConstants.SCREEN_HEIGHT){

                    FlappyBirdPipe.speed = 0;
                    FlappyBirdActivity.txt_score_over.setText(" "+ FlappyBirdActivity.txt_score.getText());
                    FlappyBirdActivity.txt_coins_over.setText(String.valueOf(fbcoins));
                    FlappyBirdActivity.txt_best_score.setText(" "+ bestscore);
                    FlappyBirdActivity.txt_score.setVisibility(INVISIBLE);
                    FlappyBirdActivity.rl_game_over.setVisibility(VISIBLE);

                }

                if (bird.getY()-bird.getHeight()+ 50<0){

                    bird.setDrop(5);

                }

                if(this.bird.getX()+this.bird.getWidth()>arrPipes.get(i).getX() + arrPipes.get(i).getWidth()/2
                        && this.bird.getX()+this.bird.getWidth()<=arrPipes.get(i).getX() + arrPipes.get(i).getWidth()/2 + FlappyBirdPipe.speed
                        && i<sumpipe/2){

                    score++;
                    fbcoins = score;


                    if(score>bestscore){

                        bestscore = score;
                        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("bestscore", bestscore);
                        editor.apply();

                    }

                    FlappyBirdActivity.txt_score.setText(""+score);
                }

                if(this.arrPipes.get(i).getX() < -arrPipes.get(i).getWidth()){

                    this.arrPipes.get(i).setX(FlappyBirdConstants.SCREEN_WIDTH);

                    if(i < sumpipe/2){

                        arrPipes.get(i).randomY();

                    }else{

                        arrPipes.get(i).setY(this.arrPipes.get(i-sumpipe/2).getY()
                                +this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance);

                    }

                }

                this.arrPipes.get(i).draw(canvas);

            }

        }else{

            if(bird.getY()>FlappyBirdConstants.SCREEN_HEIGHT/2){

                bird.setDrop(-15*FlappyBirdConstants.SCREEN_HEIGHT/1080);

            }

            if (bird.getY()-bird.getHeight()+50<0){

                bird.setDrop(0.3f);

            }

            bird.draw(canvas);

        }

        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){

            bird.setDrop(-15);
            if(loadedsound){

                int streamId = this.soundPool.play(this.soundJump, (float)0.5, (float)0.5, 1, 0, 1f);

            }

        }

        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        FlappyBirdActivity.txt_score.setText("0");
        coins.setCoins(coins.getCoins() + fbcoins);
        mTextCoins.setText("" + coins.getCoins());
        score=0;
        fbcoins=0;
        initPipe();
        initBird();
    }
}
