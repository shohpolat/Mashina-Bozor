package com.example.mashinabozor.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Page_Adapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private String [] titles = new String[]{"BOSH SAHIFA","QIDIRUV SAHIFASI","YOZISHMALAR"};

    public Page_Adapter(@NonNull FragmentManager fm,int behaviour, ArrayList<Fragment> fragments) {
        super(fm,behaviour);

        this.fragments = fragments;

    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
