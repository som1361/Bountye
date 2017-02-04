package com.example.somayyeh.bountye;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class DiscoverCategoriesPagerAdapter extends FragmentPagerAdapter {
    Context mContext;

    public DiscoverCategoriesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new everyThingFragment();
            case 1:
                return new everyThingFragment();
            case 2:
                return new everyThingFragment();
            case 3:
                return new everyThingFragment();
            case 4:
                return new everyThingFragment();
            case 5:
                return new everyThingFragment();
            case 6:
                return new everyThingFragment();
            case 7:
                return new everyThingFragment();
            case 8:
                return new everyThingFragment();
            case 9:
                return new everyThingFragment();
            case 10:
                return new everyThingFragment();
            case 11:
                return new everyThingFragment();
            case 12:
                return new everyThingFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.home_garden_fragment);
            case 1:
                return mContext.getResources().getString(R.string.everything_fragment);
            case 2:
                return mContext.getResources().getString(R.string.charities_fragment);
            case 3:
                return mContext.getResources().getString(R.string.kids_fragment);
            case 4:
                return mContext.getResources().getString(R.string.books_movies_fragment);
            case 5:
                return mContext.getResources().getString(R.string.collectables_fragment);
            case 6:
                return mContext.getResources().getString(R.string.electronics_fragment);
            case 7:
                return mContext.getResources().getString(R.string.women_fragment);
            case 8:
                return mContext.getResources().getString(R.string.men_fragment);
            case 9:
                return mContext.getResources().getString(R.string.motors_fragment);
            case 10:
                return mContext.getResources().getString(R.string.sporting_goods_fragment);
            case 11:
                return mContext.getResources().getString(R.string.other_fragment);
            case 12:
                return mContext.getResources().getString(R.string.tickets_fragment);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 13;
    }
}
