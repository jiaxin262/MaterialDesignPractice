package com.jia.jason.materialdesighpractice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.model.ImageModel;

import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/24
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<ImageModel> imageModels;

    public ImageAdapter(Context context, List<ImageModel> imageModels) {
        this.context = context;
        this.imageModels = imageModels;
    }

    @Override
    public int getCount() {
        return imageModels.size();
    }

    @Override
    public Object getItem(int position) {
        return imageModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        ImageModel imageModel = imageModels.get(position);
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(-2, -2));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setBackgroundColor(imageModel.getImageColor());
        imageView.setImageResource(imageModel.getImageId());
        return imageView;
    }

}
