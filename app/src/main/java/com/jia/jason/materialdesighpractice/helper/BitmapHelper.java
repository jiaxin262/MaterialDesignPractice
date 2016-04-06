package com.jia.jason.materialdesighpractice.helper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

import com.jia.jason.materialdesighpractice.JApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xin.jia
 * since 2016/3/29
 */
public class BitmapHelper {

    public BitmapHelper() {
    }

    public static BitmapHelper getBitmapHelper() {
        return new BitmapHelper();
    }
    
    public static Bitmap decodeFile(String pathName, float density) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        float f = 160.0F * density;
        options.inDensity = (int)f;
        Bitmap bm = BitmapFactory.decodeFile(pathName, options);
        if(bm != null) {
            bm.setDensity((int)f);
        }

        return bm;
    }

    public static Bitmap decodeStream(InputStream is) {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inDensity = 160;
        localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(is, (Rect)null, localOptions);
    }

    public static Bitmap decodeStream(InputStream is, float density) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = (int)(160.0F * density);
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(is, (Rect)null, options);
    }

    public static Bitmap decodeURL(String urlString) {
        try {
            HttpURLConnection e = (HttpURLConnection)(new URL(urlString)).openConnection();
            e.setDoInput(true);
            e.connect();
            return decodeStream(e.getInputStream());
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static int dip2px(float dp) {
        return (int)(convertUnitToPixel(1, dp) + 0.5F);
    }

    public static float dip2pxF(float dp) {
        return convertUnitToPixel(1, dp);
    }

    public static float px2dip(float px) {
        float scale = JApplication.getContext().getResources().getDisplayMetrics().density;
        return px / scale;
    }

    public static int px(float dp) {
        return (int)((float)dip2px(dp) + 0.5F);
    }

    public static int iPXToPX(float iPX) {
        float px = (float)JApplication.getContext().getResources().getDisplayMetrics().widthPixels / 640.0F * iPX + 0.5F;
        return (int)px;
    }

    public static float iPXToPXF(float iPX) {
        return (float)JApplication.getContext().getResources().getDisplayMetrics().widthPixels / 640.0F * iPX;
    }

    private static float convertUnitToPixel(int unit, float in) {
        return TypedValue.applyDimension(unit, in, JApplication.getContext().getResources().getDisplayMetrics());
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != -1? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap bitmapAdder(int bottomResId, int topResId) {
        return bitmapAdder(bottomResId, topResId, 0.0F);
    }

    public static Bitmap bitmapAdder(int bottomResId, int topResId, float padding) {
        Bitmap bottomBmp = BitmapFactory.decodeResource(JApplication.getContext().getResources(), bottomResId).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap topBmp = BitmapFactory.decodeResource(JApplication.getContext().getResources(), topResId);
        Canvas canvas = new Canvas(bottomBmp);
        canvas.drawBitmap(topBmp, (float)(bottomBmp.getWidth() - topBmp.getWidth()) - padding, padding, (Paint)null);
        return bottomBmp;
    }

    public static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = null;

        try {
            Bitmap e = BitmapFactory.decodeResource(res, id);
            bitmap = e.copy(Bitmap.Config.ARGB_8888, true);
            e.recycle();
        } catch (OutOfMemoryError var4) {
            ;
        }

        return bitmap;
    }

    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static BitmapDrawable getDrawableFromView(View view) {
        BitmapDrawable drawable = new BitmapDrawable(getBitmapFromView(view));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }
}
