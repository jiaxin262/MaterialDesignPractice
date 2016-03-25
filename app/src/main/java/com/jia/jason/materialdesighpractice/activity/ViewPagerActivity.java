package com.jia.jason.materialdesighpractice.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.adapter.ViewPagerAdapter;
import com.jia.jason.materialdesighpractice.model.ImageModel;

/**
 * Created by xin.jia
 * since 2016/3/25
 */
public class ViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_view);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, ImageModel.mock());
        viewPager.setAdapter(adapter);
    }
}
