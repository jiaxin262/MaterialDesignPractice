package com.jia.jason.materialdesighpractice.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.jia.jason.materialdesighpractice.model.ImageModel;

import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/25
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ImageModel> imageModels;

    public ViewPagerAdapter(Context context, List<ImageModel> imageModels) {
        this.context = context;
        this.imageModels = imageModels;
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
    public Object instantiateItem(ViewGroup container, int position) {
        ImageModel imageModel = imageModels.get(position);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new GridView.LayoutParams(-2, -2));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(imageModel.getImageColor());
        imageView.setImageResource(imageModel.getImageId());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
