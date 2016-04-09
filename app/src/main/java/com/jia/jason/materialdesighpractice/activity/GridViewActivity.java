package com.jia.jason.materialdesighpractice.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.adapter.ImageAdapter;
import com.jia.jason.materialdesighpractice.adapter.ViewPagerAdapter;
import com.jia.jason.materialdesighpractice.helper.BitmapHelper;
import com.jia.jason.materialdesighpractice.model.ImageModel;
import com.jia.jason.materialdesighpractice.model.JListItem;
import com.jia.jason.materialdesighpractice.util.JScreenUtil;
import com.jia.jason.materialdesighpractice.util.ZoomUtil;

import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/24
 */
public class GridViewActivity extends BaseActivity {

    GridView gridView;
    List<ImageModel> imageModels;
    ZoomUtil zoomUtil;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_layout);

        imageModels = ImageModel.mock();
        gridView = (GridView) findViewById(R.id.grid_view_gv);
        gridView.setColumnWidth(JScreenUtil.getScreenWidth()/2 - BitmapHelper.dip2px(1));
        gridView.setAdapter(new ImageAdapter(this, imageModels));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setViewPagerAndZoom(view, position);
            }
        });
    }

    private void setViewPagerAndZoom(View view, int position) {
        View container = findViewById(R.id.grid_view_pager_container);
        viewPager = (ViewPager) findViewById(R.id.image_detail_view);
        zoomUtil = new ZoomUtil(container, viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, imageModels, zoomUtil);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);

        zoomUtil.setOnZoomListener(new ZoomUtil.OnZoomListener() {
            @Override
            public void onExpanded() {
            }

            @Override
            public void onThumbed() {
                viewPager.setVisibility(View.GONE);
            }
        });
        zoomUtil.zoomImageFromSmall(view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewPager != null && viewPager.getVisibility() == View.VISIBLE) {
                zoomUtil.closeZoomAnim(viewPager.getCurrentItem());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
