package com.jia.jason.materialdesighpractice.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.adapter.ImageAdapter;
import com.jia.jason.materialdesighpractice.adapter.ViewPagerAdapter;
import com.jia.jason.materialdesighpractice.model.ImageModel;
import com.jia.jason.materialdesighpractice.util.ZoomUtil;

import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/24
 */
public class GridViewActivity extends BaseActivity {

    GridView gridView;
    List<ImageModel> imageModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_layout);

        imageModels = ImageModel.mock();
        gridView = (GridView) findViewById(R.id.grid_view_gv);
        gridView.setAdapter(new ImageAdapter(this, imageModels));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setViewPagerAndZoom(view, position);
            }
        });
    }

    private void setViewPagerAndZoom(View view, int position) {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.image_detail_view);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, imageModels, viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);

        viewPager.setVisibility(View.VISIBLE);
        View container = findViewById(R.id.grid_view_pager_container);

        ZoomUtil zoomUtil = new ZoomUtil(container, viewPager);
        zoomUtil.zoomImageFromSmall(view);
    }
}
