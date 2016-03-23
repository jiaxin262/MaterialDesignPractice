package com.jia.jason.materialdesighpractice.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/23
 */
public class JListItem {
    public int color;
    public String text;
    public boolean isChecked = false;

    public JListItem() {
        color = Color.BLUE;
        text = "第一个view";
    }

    public JListItem(int color, String text) {
        this.color = color;
        this.text = text;
    }

    //生成假数据
    public static List<JListItem> mockItems(){
        List<JListItem> items = new ArrayList<JListItem>();
        int[] colors = new int[]{
                Color.RED, Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY,
                Color.GRAY, Color.GREEN, Color.LTGRAY, Color.YELLOW, Color.TRANSPARENT,
                Color.RED, Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY,
                Color.GRAY, Color.GREEN, Color.LTGRAY, Color.YELLOW, Color.TRANSPARENT,
                Color.RED, Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY,
                Color.GRAY, Color.GREEN, Color.LTGRAY, Color.YELLOW, Color.TRANSPARENT,
                Color.RED, Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY,
                Color.GRAY, Color.GREEN, Color.LTGRAY, Color.YELLOW, Color.TRANSPARENT
        };
        for (int i=0; i<10; i++){
            JListItem item = new JListItem(colors[i], "第"+i+"个view");
            items.add(item);
        }
        return items;
    }

    //生成假数据
    public static List<JListItem> mockItems2(){
        List<JListItem> items = new ArrayList<JListItem>();
        for (int i=0; i<25; i++){
            JListItem item = new JListItem(Color.GREEN, "第"+i+"个viewLLLLLLLLLLLLLLLLL");
            items.add(item);
        }
        return items;
    }
}
