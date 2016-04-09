package com.jia.jason.materialdesighpractice.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.adapter.ViewPagerAdapter;
import com.jia.jason.materialdesighpractice.model.ImageModel;
import com.jia.jason.materialdesighpractice.util.ZoomUtil;

/**
 * Created by xin.jia
 * since 2016/3/25
 */
public class ViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);

        View container = findViewById(R.id.grid_view_pager_container);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_view);
        ZoomUtil zoomUtil = new ZoomUtil(container, viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, ImageModel.mock(), zoomUtil);
        viewPager.setAdapter(adapter);
    }
}
