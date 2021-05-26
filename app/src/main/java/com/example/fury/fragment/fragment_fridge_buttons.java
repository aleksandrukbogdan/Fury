package com.example.fury.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fury.MyProduct;
import com.example.fury.MyProductAdapter;
import com.example.fury.R;

public class fragment_fridge_buttons extends Fragment {

    public static String vegetables;
    public static String soup;
    public static String pizza;
    public static String juice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fridge_fragment_buttons,
                container,
                false);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lv = getView().findViewById(R.id.listview);
        MyProductAdapter adapter = new MyProductAdapter(getContext(), makeProduct());
        lv.setAdapter(adapter);

       /* ImageButton fridge_close = getView().findViewById(R.id.onCloseFridge);
        fridge_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_main_id, fragment_kitchen1);
                fragmentTransaction.commit();
            }
        });*/
    }

    public MyProduct[] makeProduct() {
        MyProduct[] arr = new MyProduct[4];

        String[] productArr = {this.getString(R.string.vegetables), this.getString(R.string.soup), this.getString(R.string.pizza), this.getString(R.string.juice)};
        vegetables = this.getString(R.string.vegetables);
        soup = this.getString(R.string.soup);
        pizza = this.getString(R.string.pizza);
        juice = this.getString(R.string.juice);
        int[] tempArr = {5, 10, 15, 2};
        // Сборка месяцев

        for (int i = 0; i < arr.length; i++) {
            MyProduct product = new MyProduct();
            product.product_name = productArr[i];
            product.cost = tempArr[i];
            arr[i] = product;
        }
        return arr;
    }

}
