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

    private View containerView;
    private ViewGroup thumbViewParent;
    private View expandedView;

    private Rect startBounds;
    private float startScale;
    private float endScale;

    public ZoomUtil(View containerView, View expandedView) {
        this.containerView = containerView;
        this.expandedView = expandedView;
    }
}
