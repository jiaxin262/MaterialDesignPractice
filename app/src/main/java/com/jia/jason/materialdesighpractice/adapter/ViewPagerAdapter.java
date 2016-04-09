package com.jia.jason.materialdesighpractice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.model.ImageModel;
import com.jia.jason.materialdesighpractice.util.ZoomUtil;
import com.jia.jason.materialdesighpractice.view.ZoomImageView;

import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/25
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ImageModel> imageModels;
    private ZoomUtil zoomUtil;

    public ViewPagerAdapter(Context context, List<ImageModel> imageModels, ZoomUtil zoomUtil) {
        this.context = context;
        this.imageModels = imageModels;
        this.zoomUtil = zoomUtil;
    }

    @Override
    public int getCount() {
        return imageModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageModel imageModel = imageModels.get(position);

        ZoomImageView zoomImageView = new ZoomImageView(context, null);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageModel.getImageId());
        zoomImageView.setImageBitmap(bitmap);
        zoomImageView.setZoomUtil(zoomUtil, position);
        container.addView(zoomImageView);
        return zoomImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
