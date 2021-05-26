package com.example.fury;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fury.fragment.fragment_fridge_buttons;

import static com.example.fury.MainActivity.coins;
import static com.example.fury.MainActivity.fury_happy;
import static com.example.fury.MainActivity.mTextCoins;
import static com.example.fury.MainActivity.pB_happy;
import static com.example.fury.MainActivity.pB_hungry;
import static com.example.fury.MainActivity.tamagochi1;

public class MyProductAdapter extends ArrayAdapter<MyProduct> {

    static boolean flagFeed = false;
    Handler handler;
    AnimationDrawable furyfeed_animation;

    public MyProductAdapter(Context context, MyProduct[] arr) {
        super(context, R.layout.adapter_item, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyProduct product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.textView1)).setText(product.product_name);
        ((TextView) convertView.findViewById(R.id.textView2)).setText(String.valueOf(product.cost));


        if (product.product_name == fragment_fridge_buttons.vegetables)
            ((ImageView) convertView.findViewById(R.id.image_view_list)).setImageResource(R.drawable.carrot);
        if (product.product_name == fragment_fridge_buttons.soup)
            ((ImageView) convertView.findViewById(R.id.image_view_list)).setImageResource(R.drawable.soup);
        if (product.product_name == fragment_fridge_buttons.pizza)
            ((ImageView) convertView.findViewById(R.id.image_view_list)).setImageResource(R.drawable.pizza);
        if (product.product_name == fragment_fridge_buttons.juice)
            ((ImageView) convertView.findViewById(R.id.image_view_list)).setImageResource(R.drawable.tea);


        Button btn_feed = convertView.findViewById(R.id.btn_feed);
        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (coins.getCoins() >= product.cost && tamagochi1.getHungriness() < 100) {

                    coins.setCoins(coins.getCoins() - product.cost);
                    if(MainActivity.loadedsound){
                        int streamId = MainActivity.soundPool.play(MainActivity.soundMoney, (float)0.5, (float)0.5, 1, 0, 1f);
                    }
                    mTextCoins.setText("" + coins.getCoins());
                    tamagochi1.feed(product.cost * 2);
                    pB_hungry.setProgress((int) tamagochi1.getHungriness());
                    pB_happy.setProgress((int) tamagochi1.getHappiness());
                    if (coins.getCoins() < 0) {
                        coins.setCoins(0);
                        mTextCoins.setText("" + coins.getCoins());
                    }

                    handler = new Handler();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (furyfeed_animation != null)
                                furyfeed_animation.stop();
                            MainActivity.fragment_fury_main.setVisibility(View.INVISIBLE);
                            fury_happy.setVisibility(View.INVISIBLE);
                            MainActivity.fury_feed.setVisibility(View.VISIBLE);

                            MainActivity.fury_feed.setBackgroundResource(R.drawable.furyfeed_animation);
                            furyfeed_animation = (AnimationDrawable) MainActivity.fury_feed.getBackground();
                            furyfeed_animation.start();
                        }
                    });

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            furyfeed_animation.stop();
                            MainActivity.fragment_fury_main.setVisibility(View.VISIBLE);
                            MainActivity.fury_feed.setVisibility(View.INVISIBLE);
                        }
                    }, 1800);
                    if(MainActivity.loadedsound){
                        int streamId = MainActivity.soundPool.play(MainActivity.soundFeed, (float)0.5, (float)0.5, 1, 0, 1f);
                    }
                }

            }
        });

        return convertView;
    }

}
