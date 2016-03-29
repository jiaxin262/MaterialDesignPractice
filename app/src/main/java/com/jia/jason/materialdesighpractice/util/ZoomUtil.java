package com.jia.jason.materialdesighpractice.util;

import android.animation.Animator;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xin.jia
 * since 2016/3/25
 */
public class ZoomUtil {

    private final int ANIMATION_DURATION = 300;
    private Animator animator;

    private View containerView; //最外层容器
    private ViewGroup smallViewParent;  //小图片的父容器
    private View expandedView;  //大图片所在视图

    private Rect startBounds;
    private float startScale;
    private float endScale;

    public ZoomUtil(View containerView, View expandedView) {
        this.containerView = containerView;
        this.expandedView = expandedView;
    }

    public void zoomImageFromSmall(final View smallView) {
        smallViewParent = (ViewGroup) smallView.getParent();
        if (animator != null) {
            animator.cancel();
        }

    }

}
