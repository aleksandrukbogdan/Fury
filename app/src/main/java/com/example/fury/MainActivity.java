package com.example.fury;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fury.flappy_bird.FlappyBirdActivity;
import com.example.fury.flappy_bird.FlappyBirdGameView;
import com.example.fury.fragment.bath_fragment;
import com.example.fury.fragment.fragment_fury_main;
import com.example.fury.fragment.kitchen_fragment;
import com.example.fury.fragment.room_fragment;
import com.example.fury.fragment.sleeproom_fragment;
import com.example.fury.fury_animation.fury_default;
import com.example.fury.fury_animation.fury_defaultbad;
import com.example.fury.fury_animation.fury_defaultfun;
import com.example.fury.fury_animation.fury_hungry;
import com.example.fury.fury_animation.fury_wantsleep;
import com.example.fury.fury_animation.fury_wanttoilet;

import static com.example.fury.DataBaseHelper.TABLE;
import static com.example.fury.DataBaseHelper.game_over;
import static com.example.fury.DataBaseHelper.user_id;
import static com.example.fury.DataBaseHelper.user_name;
import static com.example.fury.DataBaseHelper.user_score;
import static com.example.fury.Shell.databaseHelper;
import static com.example.fury.Shell.db;
import static com.example.fury.Shell.userCursor;
import static com.example.fury.TimerThread.scores_db;
import static com.example.fury.fragment.sleeproom_fragment.sleepzzz;
import static java.lang.String.valueOf;

public class MainActivity extends FragmentActivity {
    public static tamagochi tamagochi1 = new tamagochi(50, 50, 50, 50, 0);
    final long saved_money = 100;
    final long saved_time = 0;
    final float saved_hungry = 50;
    final float saved_tired = 50;
    final float saved_happy = 50;
    final float saved_clean = 50;
    final float saved_dirt = 1;
    static public Coins coins = new Coins(100);


    static Level level = new Level(1, 0);
    FrameLayout level_layout;
    static ProgressBar level_pB;
    static TextView level_tV;
    static ImageView fury_happy;
    static Dialog leveldialog;

    public static ProgressBar pB_hungry;
    public static ProgressBar pB_happy;
    public static ProgressBar pB_clean;
    public static ProgressBar pB_tired;
    static int pBbgColor;
    public static LinearLayout view1;
    static public TextView mTextCoins;

    public static FrameLayout furycontainer;
    static View fragment_fury_main;

    static ImageView dirt;
    TableLayout tb;

    TimerThread t = new TimerThread();

    static Fragment fury_wanttoilet = new fury_wanttoilet();
    static Fragment fury_hungry = new fury_hungry();
    static Fragment fury_wantsleep = new fury_wantsleep();

    static Fragment fury_default = new fury_default();
    static Fragment fury_defaultfun = new fury_defaultfun();
    static Fragment fury_defaultbad = new fury_defaultbad();

    static ImageView fury_feed;

    Dialog create_pet_dialog;
    Button btn_create;
    Button btn_cont;


    static AnimationDrawable furytoilet_animation, furytoiletdirtphase1_animation, furytoiletdirtphase2_animation;

    public static Fragment newFragment = null;
    static FragmentManager fm;
    static FragmentTransaction ft;

    public static boolean flag = false;
    public static boolean flagT = false;
    static boolean flagTin = false;
    public static boolean flagS = false;
    static boolean flagK = false;

    static boolean isActive = true;


    public long savedtime = 0;
    public long elapsedMillis;

    com.example.fury.fragment.fragment_fury_main fragment_fury_main1 = new fragment_fury_main();

    Chronometer mChronometer;
    ImageButton btn_kitchen, btn_bath, btn_room, btn_sleep;

    SharedPreferences sPref;

    public static int soundFeed;
    public static int soundFight;
    public static int soundHappy;
    public static int soundMoney;
    public static int soundNewLevel;
    public static int soundPet;
    public static int soundToilet;
    public static int soundToiletEnd;
    public static int soundWantSleep;
    public static int soundWantToilet;
    public static int soundLight;

    public static boolean loadedsound;
    public static SoundPool soundPool;
    static SoundPool soundPoolToiletEnd;
    public static MediaPlayer mediaPlayerWash;
    public static MediaPlayer mediaPlayerSleep;
    public static MediaPlayer mediaPlayerWantToilet;
    public static MediaPlayer mediaPlayerHungry;
    public static MediaPlayer mediaPlayerWantSleep;
    public Dialog new_users_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Если Android 4.4 - включить  IMMERSIVE MODE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        setContentView(R.layout.activity_main);

        btn_room = findViewById(R.id.btn_room);
        btn_kitchen = findViewById(R.id.btn_kitchen);
        btn_bath = findViewById(R.id.btn_bath);
        btn_sleep = findViewById(R.id.btn_sleep);

        pB_hungry = findViewById(R.id.progressBar_hungry);
        pB_happy = findViewById(R.id.progressBar_happy);
        pB_clean = findViewById(R.id.progressBar_clean);
        pB_tired = findViewById(R.id.progressBar_tired);

        pBbgColor = getResources().getColor(R.color.pbFury);

        level_layout = findViewById(R.id.level_layout);
        level_pB = findViewById(R.id.level_pB);
        level_tV = findViewById(R.id.level_tV);
        fury_happy = findViewById(R.id.fury_happy);

        fury_feed = findViewById(R.id.fury_feed);

        dirt = findViewById(R.id.dirt_main);
        fragment_fury_main = findViewById(R.id.fragment_fury_main);
        furycontainer = findViewById(R.id.furycontainer);
        tb = findViewById(R.id.fridge);
        fm = getSupportFragmentManager();

        view1 = findViewById(R.id.view_to_background);
        view1.setBackgroundResource(R.drawable.room);

        mTextCoins = findViewById(R.id.mtextcoins);
        mChronometer = findViewById(R.id.chronos);
//открытие бд для проверки на отсутсвие записей
        db = databaseHelper.getWritableDatabase();
        userCursor = db.rawQuery("select * from " + TABLE, null);
        if (userCursor.getCount() == 0) {
            startActivity(new Intent(MainActivity.this, Activity_FirstStart.class));
        }
        //добавление очков
        Runnable runnable = new Runnable() {
            @Override
            public void run() {


                String[] row = {user_id};
                ContentValues cv1 = new ContentValues();
                cv1.put(user_score, scores_db);
                db.update(TABLE, cv1, "_id=?", row);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread tt = new Thread(runnable);
        tt.start();
//диалоговое окно создания пользователя
        create_pet_dialog = new Dialog(MainActivity.this);
        create_pet_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        create_pet_dialog.closeOptionsMenu();

        Window window1 = create_pet_dialog.getWindow();
        window1.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        create_pet_dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        create_pet_dialog.setContentView(R.layout.create_new_user);

        btn_create = create_pet_dialog.findViewById(R.id.createuser);
        btn_cont = create_pet_dialog.findViewById(R.id.withoutuser);

        btn_create.setOnClickListener(onButtonClickListener1);
        btn_cont.setOnClickListener(onButtonClickListener1);
        create_pet_dialog.show();


        loadText();
        t.start();//запуск потока, реализованного классом TimerThread

        mChronometer.getBase();
        mChronometer.setCountDown(false);
        mChronometer.start();
//запуск секундомера с переключением анимаций

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                red_bg_pB();
                elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();

                if (tamagochi1.getHappiness() <= 20) {
                    pB_happy.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    pB_happy.setProgressBackgroundTintList(ColorStateList.valueOf(pBbgColor));
                }

                if (!flag) {
                    if (flagT) {
                        if (bath_fragment.flagWash) {
                            fury_wash_Animation();
                        } else {
                            if (pB_clean.getProgress() < 100) {
                                fury_toilet_Animation();
                                if (pB_clean.getProgress() < 80) {
                                    dirt.setVisibility(View.INVISIBLE);
                                }
                            } else if (pB_clean.getProgress() >= 100) {
                                flagTin = true;
                                if (furytoilet_animation != null) {
                                    furytoilet_animation.stop();
                                }
                                if (furytoiletdirtphase1_animation != null) {
                                    furytoiletdirtphase1_animation.stop();
                                }
                                if (furytoiletdirtphase2_animation != null) {
                                    furytoiletdirtphase2_animation.stop();
                                }
                                view1.setBackgroundResource(R.drawable.bathroom);
                                furycontainer.setVisibility(View.VISIBLE);
                                fury_animation();
                                if (tamagochi1.getDirt() >= 20 && tamagochi1.getDirt() <= 60) {
                                    dirt.setImageResource(R.drawable.fury_dirtphase1);
                                    dirt.setVisibility(View.VISIBLE);
                                } else if (tamagochi1.getDirt() > 60) {
                                    dirt.setImageResource(R.drawable.fury_dirtphase2);
                                    dirt.setVisibility(View.VISIBLE);
                                } else if (tamagochi1.getDirt() < 20) {
                                    dirt.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    } else {
                        fury_animation();

                        if (tamagochi1.getDirt() >= 20 && tamagochi1.getDirt() <= 60) {
                            dirt.setImageResource(R.drawable.fury_dirtphase1);
                            dirt.setVisibility(View.VISIBLE);
                        } else if (tamagochi1.getDirt() > 60) {
                            dirt.setImageResource(R.drawable.fury_dirtphase2);
                            dirt.setVisibility(View.VISIBLE);
                        } else if (tamagochi1.getDirt() < 20) {
                            dirt.setVisibility(View.INVISIBLE);
                        }
                    }

                } else {
                    if (pB_tired.getProgress() < 100) {
                        fury_sleep_Animation();
                    } else if (pB_tired.getProgress() >= 100) {
                        flagS = true;
                    }
                }
            }
        });


        btn_room.setOnClickListener(onButtonClickListener);
        btn_kitchen.setOnClickListener(onButtonClickListener);
        btn_bath.setOnClickListener(onButtonClickListener);
        btn_sleep.setOnClickListener(onButtonClickListener);

        level_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leveldialog = new Dialog(MainActivity.this);

                leveldialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                leveldialog.closeOptionsMenu();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    leveldialog.getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                }
                Window window = leveldialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                leveldialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                leveldialog.setContentView(R.layout.level_dialog);

                ImageButton dialogExit = leveldialog.findViewById(R.id.dialogexit);
                TextView level_tVDialog = leveldialog.findViewById(R.id.level_tVDialog);
                LinearLayout rewardlevel = leveldialog.findViewById(R.id.rewardlevel);
                TextView level_reward_tV = leveldialog.findViewById(R.id.level_reward_tV);

                dialogExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leveldialog.dismiss();
                    }
                });

                level_tVDialog.setText("" + level.getLevel());

                if (level.getLevel() > 1) {
                    rewardlevel.setVisibility(View.VISIBLE);
                    level_reward_tV.setText(" " + (level.getReward() - 5));
                }

                leveldialog.show();

            }
        });

        ft = fm.beginTransaction();
        if (tamagochi1.getToilet() <= 20) {
            newFragment = fury_wanttoilet;
            ft.replace(R.id.fragment_fury_main, newFragment);
            ft.disallowAddToBackStack();
            ft.commit();
        } else {
            if (tamagochi1.getHungriness() <= 20) {
                newFragment = fury_hungry;
                ft.replace(R.id.fragment_fury_main, newFragment);
                ft.disallowAddToBackStack();
                ft.commit();
            } else {
                if (tamagochi1.getStrength() <= 20) {
                    newFragment = fury_wantsleep;
                    ft.replace(R.id.fragment_fury_main, newFragment);
                    ft.disallowAddToBackStack();
                    ft.commit();
                } else {
                    if (tamagochi1.getHappiness() > 20 && tamagochi1.getHappiness() < 80) {
                        newFragment = fury_default;
                        ft.replace(R.id.fragment_fury_main, newFragment);
                        ft.disallowAddToBackStack();
                        ft.commit();
                    } else if (tamagochi1.getHappiness() >= 80) {
                        newFragment = fury_defaultfun;
                        ft.replace(R.id.fragment_fury_main, newFragment);
                        ft.disallowAddToBackStack();
                        ft.commit();
                    } else if (tamagochi1.getHappiness() <= 20) {
                        newFragment = fury_defaultbad;
                        ft.replace(R.id.fragment_fury_main, newFragment);
                        ft.disallowAddToBackStack();
                        ft.commit();
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            soundPool = builder.build();
            soundPoolToiletEnd = builder.build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            soundPoolToiletEnd = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadedsound = true;
            }
        });

        soundPoolToiletEnd.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadedsound = true;
            }
        });

        soundFeed = soundPool.load(getApplicationContext(), R.raw.feed, 1); //+
        soundFight = soundPool.load(getApplicationContext(), R.raw.fight, 1); //+
        soundHappy = soundPool.load(getApplicationContext(), R.raw.happy, 1); //+
        soundMoney = soundPool.load(getApplicationContext(), R.raw.money, 1); //+
        soundNewLevel = soundPool.load(getApplicationContext(), R.raw.newlevel, 1); //+
        soundPet = soundPool.load(getApplicationContext(), R.raw.pet, 1); //+
        soundToilet = soundPool.load(getApplicationContext(), R.raw.toilet, 1); //+
        soundToiletEnd = soundPoolToiletEnd.load(getApplicationContext(), R.raw.toiletend, 1); //+
        soundWantSleep = soundPool.load(getApplicationContext(), R.raw.wantsleep, 1);
        soundWantToilet = soundPool.load(getApplicationContext(), R.raw.wanttoilet, 1);
        soundLight = soundPool.load(getApplicationContext(), R.raw.light, 1);

        mediaPlayerWash = MediaPlayer.create(this, R.raw.wash);
        mediaPlayerSleep = MediaPlayer.create(this, R.raw.sleep);
        mediaPlayerWantToilet = MediaPlayer.create(this, R.raw.wanttoilet);
        mediaPlayerHungry = MediaPlayer.create(this, R.raw.hungry);
        mediaPlayerWantSleep = MediaPlayer.create(this, R.raw.wantsleep);


    }

    Button.OnClickListener onButtonClickListener1 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btn_create) {
                new_users_dialog = new Dialog(MainActivity.this);

                new_users_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                new_users_dialog.closeOptionsMenu();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    new_users_dialog.getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                }
                Window window = new_users_dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                new_users_dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                new_users_dialog.setContentView(R.layout.new_user_dialog);

                EditText inputname = new_users_dialog.findViewById(R.id.inputname);
                Button btn_confirm = new_users_dialog.findViewById(R.id.confirm_btn);

                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues cv = new ContentValues();

                        // получаем данные из полей ввода
                        String name = inputname.getText().toString();
                        cv.put("name", name);
                        cv.put("score", 0);
                        //заполнение бд
                        db.insert(TABLE, null, cv);
                        new_users_dialog.dismiss();
                        create_pet_dialog.dismiss();
                    }
                });
                new_users_dialog.show();
            } else {
                create_pet_dialog.dismiss();
            }
        }
    };

    final ImageButton.OnClickListener onButtonClickListener = new ImageButton.OnClickListener() { // переключение анимаций в соответствии с комнатами

        @Override
        public void onClick(View v) {


            Fragment newFragment = null;
            Fragment room_fragment = new room_fragment();
            Fragment kitchen_fr = new kitchen_fragment();
            Fragment bath_fr = new bath_fragment();
            Fragment sleep_fr = new sleeproom_fragment();

            bath_fragment.flagWash = false;
            if (mediaPlayerWash != null)
                mediaPlayerWash.stop();

            if (mediaPlayerSleep != null)
                mediaPlayerSleep.stop();


            ft = fm.beginTransaction();
            if (v == btn_room) {
                flag = false;
                flagK = false;
                flagT = false;
                furycontainer.setVisibility(View.VISIBLE);
                if (tamagochi1.getToilet() <= 20) {

                    newFragment = fury_wanttoilet;
                    if (mediaPlayerWantToilet != null) {
                        mediaPlayerWantToilet.stop();
                        mediaPlayerWantToilet = MediaPlayer.create(getApplicationContext(), R.raw.wanttoilet);
                        mediaPlayerWantToilet.setLooping(true);
                        mediaPlayerWantToilet.start();
                    }
                } else {
                    if (tamagochi1.getHungriness() <= 20) {
                        newFragment = fury_hungry;
                        if (mediaPlayerHungry != null) {
                            mediaPlayerHungry.stop();
                            mediaPlayerHungry = MediaPlayer.create(getApplicationContext(), R.raw.hungry);
                            mediaPlayerHungry.setLooping(true);
                            mediaPlayerHungry.start();
                        }
                    } else {
                        if (tamagochi1.getStrength() <= 20) {
                            newFragment = fury_wantsleep;
                            if (mediaPlayerWantSleep != null) {
                                mediaPlayerWantSleep.stop();
                                mediaPlayerWantSleep = MediaPlayer.create(getApplicationContext(), R.raw.wantsleep);
                                mediaPlayerWantSleep.setLooping(true);
                                mediaPlayerWantSleep.start();
                            }
                        } else {
                            if (tamagochi1.getHappiness() > 20 && tamagochi1.getHappiness() < 80) {
                                newFragment = fury_default;
                            } else if (tamagochi1.getHappiness() >= 80) {
                                newFragment = fury_defaultfun;
                            } else if (tamagochi1.getHappiness() <= 20) {
                                newFragment = fury_defaultbad;
                            }
                        }
                    }
                }
                if (tamagochi1.getDirt() >= 20 && tamagochi1.getDirt() <= 60) {
                    dirt.setImageResource(R.drawable.fury_dirtphase1);
                    dirt.setVisibility(View.VISIBLE);
                } else if (tamagochi1.getDirt() > 60) {
                    dirt.setImageResource(R.drawable.fury_dirtphase2);
                    dirt.setVisibility(View.VISIBLE);
                } else if (tamagochi1.getDirt() < 20) {
                    dirt.setVisibility(View.INVISIBLE);
                }
                view1.setBackgroundResource(R.drawable.room);
                ft.replace(R.id.fragment_main_id, room_fragment);
                ft.commit();
            } else if (v == btn_kitchen) {
                flag = false;
                flagK = true;
                MyProductAdapter.flagFeed = false;
                flagT = false;
                furycontainer.setVisibility(View.VISIBLE);
                if (tamagochi1.getToilet() <= 20) {
                    newFragment = fury_wanttoilet;
                    if (mediaPlayerWantToilet != null) {
                        mediaPlayerWantToilet.stop();
                        mediaPlayerWantToilet = MediaPlayer.create(getApplicationContext(), R.raw.wanttoilet);
                        mediaPlayerWantToilet.setLooping(true);
                        mediaPlayerWantToilet.start();
                    }
                } else {
                    if (tamagochi1.getHungriness() <= 20) {
                        newFragment = fury_hungry;
                        if (mediaPlayerHungry != null) {
                            mediaPlayerHungry.stop();
                            mediaPlayerHungry = MediaPlayer.create(getApplicationContext(), R.raw.hungry);
                            mediaPlayerHungry.setLooping(true);
                            mediaPlayerHungry.start();
                        }
                    } else {
                        if (tamagochi1.getStrength() <= 20) {
                            newFragment = fury_wantsleep;
                            if (mediaPlayerWantSleep != null) {
                                mediaPlayerWantSleep.stop();
                                mediaPlayerWantSleep = MediaPlayer.create(getApplicationContext(), R.raw.wantsleep);
                                mediaPlayerWantSleep.setLooping(true);
                                mediaPlayerWantSleep.start();
                            }
                        } else {
                            if (tamagochi1.getHappiness() > 20 && tamagochi1.getHappiness() < 80) {
                                newFragment = fury_default;
                            } else if (tamagochi1.getHappiness() >= 80) {
                                newFragment = fury_defaultfun;
                            } else if (tamagochi1.getHappiness() <= 20) {
                                newFragment = fury_defaultbad;
                            }
                        }
                    }
                }
                if (tamagochi1.getDirt() >= 20 && tamagochi1.getDirt() <= 60) {
                    dirt.setImageResource(R.drawable.fury_dirtphase1);
                    dirt.setVisibility(View.VISIBLE);
                } else if (tamagochi1.getDirt() > 60) {
                    dirt.setImageResource(R.drawable.fury_dirtphase2);
                    dirt.setVisibility(View.VISIBLE);
                } else if (tamagochi1.getDirt() < 20) {
                    dirt.setVisibility(View.INVISIBLE);
                }
                view1.setBackgroundResource(R.drawable.kit1);
                ft.replace(R.id.fragment_main_id, kitchen_fr);
                ft.commit();
            } else if (v == btn_bath) {
                flag = false;
                flagK = false;
                flagT = true;
                if (mediaPlayerWantToilet != null)
                    mediaPlayerWantToilet.stop();

                fury_wash_Animation();
                view1.setBackgroundResource(R.drawable.bathroom);
                if (tamagochi1.getToilet() < 80) {
                    dirt.setVisibility(View.INVISIBLE);
                    furycontainer.setVisibility(View.INVISIBLE);
                    if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                        if (furytoilet_animation != null) {
                            furytoilet_animation.stop();
                        }
                        if (furytoiletdirtphase2_animation != null) {
                            furytoiletdirtphase2_animation.stop();
                        }
                        view1.setBackgroundResource(R.drawable.furytoiletdirtphase1_animation);
                        furytoiletdirtphase1_animation = (AnimationDrawable) view1.getBackground();
                        furytoiletdirtphase1_animation.start();
                    } else if (MainActivity.tamagochi1.getDirt() > 60) {
                        if (furytoilet_animation != null) {
                            furytoilet_animation.stop();
                        }
                        if (furytoiletdirtphase1_animation != null) {
                            furytoiletdirtphase1_animation.stop();
                        }
                        view1.setBackgroundResource(R.drawable.furytoiletdirtphase2_animation);
                        furytoiletdirtphase2_animation = (AnimationDrawable) view1.getBackground();
                        furytoiletdirtphase2_animation.start();
                    } else if (MainActivity.tamagochi1.getDirt() < 20) {
                        if (furytoiletdirtphase1_animation != null) {
                            furytoiletdirtphase1_animation.stop();
                        }
                        if (furytoiletdirtphase2_animation != null) {
                            furytoiletdirtphase2_animation.stop();
                        }
                        view1.setBackgroundResource(R.drawable.furytoilet_animation);
                        furytoilet_animation = (AnimationDrawable) view1.getBackground();
                        furytoilet_animation.start();
                    }
                } else {
                    view1.setBackgroundResource(R.drawable.bathroom);
                    if (tamagochi1.getHungriness() <= 20) {
                        newFragment = fury_hungry;
                        if (mediaPlayerHungry != null) {
                            mediaPlayerHungry.stop();
                            mediaPlayerHungry = MediaPlayer.create(getApplicationContext(), R.raw.hungry);
                            mediaPlayerHungry.setLooping(true);
                            mediaPlayerHungry.start();
                        }
                    } else {
                        if (tamagochi1.getStrength() <= 20) {
                            newFragment = fury_wantsleep;
                            if (mediaPlayerWantSleep != null) {
                                mediaPlayerWantSleep.stop();
                                mediaPlayerWantSleep = MediaPlayer.create(getApplicationContext(), R.raw.wantsleep);
                                mediaPlayerWantSleep.setLooping(true);
                                mediaPlayerWantSleep.start();
                            }
                        } else {
                            if (tamagochi1.getHappiness() > 20 && tamagochi1.getHappiness() < 80) {
                                newFragment = fury_default;
                            } else if (tamagochi1.getHappiness() >= 80) {
                                newFragment = fury_defaultfun;
                            } else if (tamagochi1.getHappiness() <= 20) {
                                newFragment = fury_defaultbad;
                            }
                        }
                    }
                    if (tamagochi1.getDirt() >= 20 && tamagochi1.getDirt() <= 60) {
                        dirt.setImageResource(R.drawable.fury_dirtphase1);
                        dirt.setVisibility(View.VISIBLE);
                    } else if (tamagochi1.getDirt() > 60) {
                        dirt.setImageResource(R.drawable.fury_dirtphase2);
                        dirt.setVisibility(View.VISIBLE);
                    } else if (tamagochi1.getDirt() < 20) {
                        dirt.setVisibility(View.INVISIBLE);
                    }
                }
                ft.replace(R.id.fragment_main_id, bath_fr);
                ft.commit();
            } else if (v == btn_sleep) {
                flag = true;
                flagT = false;
                flagK = false;
                if (mediaPlayerWantToilet != null)
                    mediaPlayerWantToilet.stop();
                if (mediaPlayerHungry != null)
                    mediaPlayerHungry.stop();
                if (mediaPlayerWantSleep != null)
                    mediaPlayerWantSleep.stop();
                sleeproom_fragment.flagSleep = false;
                furycontainer.setVisibility(View.INVISIBLE);
                dirt.setVisibility(View.INVISIBLE);
                if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                    view1.setBackgroundResource(R.drawable.sleeproomdirtphase1);
                } else if (MainActivity.tamagochi1.getDirt() > 60) {
                    view1.setBackgroundResource(R.drawable.sleeproomdirtphase2);
                } else if (MainActivity.tamagochi1.getDirt() < 20) {
                    view1.setBackgroundResource(R.drawable.sleeproom);
                }
                ft.replace(R.id.fragment_main_id, sleep_fr);
                ft.commit();
            }


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (newFragment != null) {
                transaction.replace(R.id.fragment_fury_main, newFragment);
                ft.disallowAddToBackStack();
                transaction.commit();
            }

        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mChronometer.start();
        loadText();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

    }

    @Override
    protected void onResume() {

        super.onResume();
        mChronometer.start();
        loadText();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mChronometer.start();
        loadText();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
        mChronometer.stop();

    }

    @Override
    protected void onStop() {
        saveText();
        mChronometer.stop();
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
        mChronometer.stop();


    }

    public void FlappyBird(View view) {
        FlappyBirdGameView.fbcoins = 0;
        startActivity(new Intent(MainActivity.this, FlappyBirdActivity.class));
    }
    //методы сохранения значений полей и загрузки
    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putLong(valueOf(saved_money), coins.getCoins());
        ed.putLong(valueOf(saved_time), elapsedMillis);
        ed.putFloat(valueOf(saved_hungry), tamagochi1.getHungriness());
        ed.putFloat(valueOf(saved_happy), tamagochi1.getHappiness());
        ed.putFloat(valueOf(saved_tired), tamagochi1.getStrength());
        ed.putFloat(valueOf(saved_clean), tamagochi1.getToilet());
        ed.putFloat(valueOf(saved_dirt), tamagochi1.getDirt());
        ed.apply();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        long savedmoney = sPref.getLong(valueOf(saved_money), saved_money);
        savedtime = sPref.getLong(valueOf(saved_time), saved_time);

        float savedhungry = sPref.getFloat(valueOf(saved_hungry), saved_hungry);
        float savedhappy = sPref.getFloat(valueOf(saved_happy), saved_happy);
        float savedtired = sPref.getFloat(valueOf(saved_tired), saved_tired);
        float savedclean = sPref.getFloat(valueOf(saved_clean), saved_clean);
        float saveddirt = sPref.getFloat(valueOf(saved_dirt), saved_dirt);

        tamagochi1.setHungriness(savedhungry);
        tamagochi1.setHappiness(savedhappy);
        tamagochi1.setToilet(savedtired);
        tamagochi1.setStrength(savedclean);
        tamagochi1.setDirt(saveddirt);


        mTextCoins.setText(Long.toString(savedmoney));
        coins.setCoins(savedmoney);
        mChronometer.setBase(SystemClock.elapsedRealtime() - savedtime);
    }

//методы анимаций
    public static void fury_animation() {
        ft = fm.beginTransaction();
        if (tamagochi1.getToilet() <= 20) {
            newFragment = fury_wanttoilet;
            ft.replace(R.id.fragment_fury_main, newFragment);
            ft.disallowAddToBackStack();
            ft.commit();
        } else {
            if (tamagochi1.getHungriness() <= 20) {
                newFragment = fury_hungry;
                ft.replace(R.id.fragment_fury_main, newFragment);
                ft.disallowAddToBackStack();
                ft.commit();
            } else {
                if (tamagochi1.getStrength() <= 20) {
                    newFragment = fury_wantsleep;
                    ft.replace(R.id.fragment_fury_main, newFragment);
                    ft.disallowAddToBackStack();
                    ft.commit();
                } else {
                    if (tamagochi1.getHappiness() > 20 && tamagochi1.getHappiness() < 80) {
                        newFragment = fury_default;
                        ft.replace(R.id.fragment_fury_main, newFragment);
                        ft.disallowAddToBackStack();
                        ft.commit();
                    } else if (tamagochi1.getHappiness() >= 80) {
                        newFragment = fury_defaultfun;
                        ft.replace(R.id.fragment_fury_main, newFragment);
                        ft.disallowAddToBackStack();
                        ft.commit();
                    } else if (tamagochi1.getHappiness() <= 20) {
                        newFragment = fury_defaultbad;
                        ft.replace(R.id.fragment_fury_main, newFragment);
                        ft.disallowAddToBackStack();
                        ft.commit();
                    }
                }
            }
        }
    }

    public static void fury_toilet_Animation() {
        if (pB_clean.getProgress() < 80) {
            flagTin = false;
            tamagochi1.gotoilet();
            if (MainActivity.loadedsound) {
                int streamId = MainActivity.soundPool.play(MainActivity.soundToilet, (float) 0.5, (float) 0.5, 1, 0, 1f);
            }
            pB_clean.setProgress((int) tamagochi1.getToilet());
            dirt.setVisibility(View.INVISIBLE);
            furycontainer.setVisibility(View.INVISIBLE);
            if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                if (furytoilet_animation != null) {
                    furytoilet_animation.stop();
                }
                if (furytoiletdirtphase2_animation != null) {
                    furytoiletdirtphase2_animation.stop();
                }
                view1.setBackgroundResource(R.drawable.furytoiletdirtphase1_animation);
                furytoiletdirtphase1_animation = (AnimationDrawable) view1.getBackground();
                furytoiletdirtphase1_animation.start();
            } else if (MainActivity.tamagochi1.getDirt() > 60) {
                if (furytoilet_animation != null) {
                    furytoilet_animation.stop();
                }
                if (furytoiletdirtphase1_animation != null) {
                    furytoiletdirtphase1_animation.stop();
                }
                view1.setBackgroundResource(R.drawable.furytoiletdirtphase2_animation);
                furytoiletdirtphase2_animation = (AnimationDrawable) view1.getBackground();
                furytoiletdirtphase2_animation.start();
            } else if (MainActivity.tamagochi1.getDirt() < 20) {
                if (furytoiletdirtphase1_animation != null) {
                    furytoiletdirtphase1_animation.stop();
                }
                if (furytoiletdirtphase2_animation != null) {
                    furytoiletdirtphase2_animation.stop();
                }
                view1.setBackgroundResource(R.drawable.furytoilet_animation);
                furytoilet_animation = (AnimationDrawable) view1.getBackground();
                furytoilet_animation.start();
            }
        } else if (pB_clean.getProgress() >= 80 && !flagTin) {
            tamagochi1.gotoilet();
            if (MainActivity.loadedsound) {
                int streamId = MainActivity.soundPool.play(MainActivity.soundToilet, (float) 0.5, (float) 0.5, 1, 0, 1f);
            }
            pB_clean.setProgress((int) tamagochi1.getToilet());
            dirt.setVisibility(View.INVISIBLE);
            furycontainer.setVisibility(View.INVISIBLE);
            if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                if (furytoilet_animation != null) {
                    furytoilet_animation.stop();
                }
                if (furytoiletdirtphase2_animation != null) {
                    furytoiletdirtphase2_animation.stop();
                }
                view1.setBackgroundResource(R.drawable.furytoiletdirtphase1_animation);
                furytoiletdirtphase1_animation = (AnimationDrawable) view1.getBackground();
                furytoiletdirtphase1_animation.start();
            } else if (MainActivity.tamagochi1.getDirt() > 60) {
                if (furytoilet_animation != null) {
                    furytoilet_animation.stop();
                }
                if (furytoiletdirtphase1_animation != null) {
                    furytoiletdirtphase1_animation.stop();
                }
                view1.setBackgroundResource(R.drawable.furytoiletdirtphase2_animation);
                furytoiletdirtphase2_animation = (AnimationDrawable) view1.getBackground();
                furytoiletdirtphase2_animation.start();
            } else if (MainActivity.tamagochi1.getDirt() < 20) {
                if (furytoiletdirtphase1_animation != null) {
                    furytoiletdirtphase1_animation.stop();
                }
                if (furytoiletdirtphase2_animation != null) {
                    furytoiletdirtphase2_animation.stop();
                }
                view1.setBackgroundResource(R.drawable.furytoilet_animation);
                furytoilet_animation = (AnimationDrawable) view1.getBackground();
                furytoilet_animation.start();
            }
        } else {
            if (furytoilet_animation != null) {
                furytoilet_animation.stop();
            }
            if (furytoiletdirtphase1_animation != null) {
                furytoiletdirtphase1_animation.stop();
            }
            if (furytoiletdirtphase2_animation != null) {
                furytoiletdirtphase2_animation.stop();
            }
            if (soundPoolToiletEnd != null) {
                if (MainActivity.loadedsound) {
                    int streamId = soundPoolToiletEnd.play(MainActivity.soundToiletEnd, (float) 0.5, (float) 0.5, 1, 0, 1f);
                }
            }
            soundPoolToiletEnd = null;
            furycontainer.setVisibility(View.VISIBLE);
            view1.setBackgroundResource(R.drawable.bathroom);
            if (tamagochi1.getDirt() >= 20 && tamagochi1.getDirt() <= 60) {
                dirt.setImageResource(R.drawable.fury_dirtphase1);
                dirt.setVisibility(View.VISIBLE);
            } else if (tamagochi1.getDirt() > 60) {
                dirt.setImageResource(R.drawable.fury_dirtphase2);
                dirt.setVisibility(View.VISIBLE);
            } else if (tamagochi1.getDirt() < 20) {
                dirt.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void fury_wash_Animation() {
        if (bath_fragment.flagWash) {
            tamagochi1.wash();
            if (tamagochi1.getDirt() <= 0) {
                if (mediaPlayerWash != null)
                    mediaPlayerWash.stop();
                dirt.setVisibility(View.INVISIBLE);
                view1.setBackgroundResource(R.drawable.bathroom);
                bath_fragment.flagWash = false;
                furycontainer.setVisibility(View.VISIBLE);
            }
            if (!flag && !flagT) {
                if (mediaPlayerWash != null)
                    mediaPlayerWash.stop();
                bath_fragment.flagWash = false;
                furycontainer.setVisibility(View.VISIBLE);
            }
        }
    }

    public void fury_sleep_Animation() {
        if (sleeproom_fragment.flagSleep) {
            if (pB_hungry.getProgress() <= 20 || MainActivity.pB_clean.getProgress() <= 20) {
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
            } else {
                if (pB_tired.getProgress() < 80) {

                    mediaPlayerSleep = MediaPlayer.create(getApplicationContext(), R.raw.sleep);
                    mediaPlayerSleep.start();

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
                } else if (pB_tired.getProgress() >= 80 && !flagS) {

                    mediaPlayerSleep = MediaPlayer.create(getApplicationContext(),
                            R.raw.sleep);
                    mediaPlayerSleep.start();
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
                    if (pB_tired.getProgress() >= 100) {
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
            if (!sleeproom_fragment.flagSleep) {
                if (mediaPlayerSleep != null)
                    mediaPlayerSleep.stop();
                if (MainActivity.tamagochi1.getDirt() >= 20 && MainActivity.tamagochi1.getDirt() <= 60) {
                    view1.setBackgroundResource(R.drawable.sleeproomdirtphase1);
                } else if (MainActivity.tamagochi1.getDirt() > 60) {
                    view1.setBackgroundResource(R.drawable.sleeproomdirtphase2);
                } else if (MainActivity.tamagochi1.getDirt() < 20) {
                    view1.setBackgroundResource(R.drawable.sleeproom);
                }
                sleepzzz.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void red_bg_pB() {
        if (tamagochi1.getHappiness() <= 20) {
            pB_happy.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            pB_happy.setProgressBackgroundTintList(ColorStateList.valueOf(pBbgColor));
        }

        if (tamagochi1.getHungriness() <= 20) {
            pB_hungry.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            pB_hungry.setProgressBackgroundTintList(ColorStateList.valueOf(pBbgColor));
        }

        if (tamagochi1.getToilet() <= 20) {
            pB_clean.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            pB_clean.setProgressBackgroundTintList(ColorStateList.valueOf(pBbgColor));
        }

        if (tamagochi1.getStrength() <= 20) {
            pB_tired.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            pB_tired.setProgressBackgroundTintList(ColorStateList.valueOf(pBbgColor));
        }

    }
}
