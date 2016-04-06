package com.jia.jason.materialdesighpractice.model;

import android.graphics.Color;
import android.util.Log;

import com.jia.jason.materialdesighpractice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by xin.jia
 * since 2016/3/24
 */
public class ImageModel {

    private int imageId;
    private int imageColor;

    public ImageModel() {
    }

    public ImageModel(int imageId, int imageColor) {
        this.imageId = imageId;
        this.imageColor = imageColor;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageColor() {
        return imageColor;
    }

    public void setImageColor(int imageColor) {
        this.imageColor = imageColor;
    }

    public static int[] imageIds = {
            R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6, R.drawable.sample_7
    };
    public static int[] imageColors = {
            Color.RED, Color.BLACK, Color.BLUE, Color.CYAN,
            Color.GRAY, Color.GREEN, Color.LTGRAY, Color.YELLOW
    };

    public static List<ImageModel> mock() {
        List<ImageModel> imageModels = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            int random = new Random().nextInt(8);
            ImageModel imageModel = new ImageModel(imageIds[random], imageColors[random]);
            imageModels.add(imageModel);
        }
        return imageModels;
    }
}
