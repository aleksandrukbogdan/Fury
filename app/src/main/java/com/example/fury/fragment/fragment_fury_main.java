package com.example.fury.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fury.R;
import com.example.fury.fury_animation.fury_default;
import com.example.fury.fury_animation.fury_wanttoilet;

public class fragment_fury_main extends Fragment {
    Fragment fury_default = new fury_default();
    Fragment fury_wanttoilet = new fury_wanttoilet();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fury, container,
                false);
    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_fury_main, fury_default);
        fragmentTransaction.commit();
    }*/
    public void sleeproom(){
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fury_default);

        FragmentManager fM = getFragmentManager();
        assert fM != null;
        FragmentTransaction fT = fragmentManager.beginTransaction();
        fT.remove(fury_wanttoilet);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
