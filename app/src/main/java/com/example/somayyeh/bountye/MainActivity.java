package com.example.somayyeh.bountye;

import android.app.Activity;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    DiscoverCategoriesPagerAdapter categoriesPagerAdapter;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // identify ViewPager used in xml file
        mViewPager = (ViewPager) findViewById(R.id.pager);

        //create an instance of ViewPagerAdapter class
        categoriesPagerAdapter = new DiscoverCategoriesPagerAdapter(getSupportFragmentManager(), this);

        //attach ViewPagerAdapter to ViewPager
        mViewPager.setAdapter(categoriesPagerAdapter);

        //identify xml file TabLayout
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setCurrentItem(1);

    }
}
