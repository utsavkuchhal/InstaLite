package com.utsav.vgapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.utsav.vgapp.Fragment.Categories;
import com.utsav.vgapp.Fragment.MainFragment;
import com.utsav.vgapp.Fragment.Trending;

public class PagerAdapter extends FragmentPagerAdapter {


    int count;
    public PagerAdapter(FragmentManager fm, int oount) {
        super(fm);
        this.count = oount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MainFragment mainFragment = new MainFragment();
                return mainFragment;
            case 1:
                Trending trending = new Trending();
                return trending;
            case 2:
                Categories categories = new Categories();
                return categories;
                default:
                    return null;

        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
