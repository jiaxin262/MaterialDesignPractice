package com.jia.jason.materialdesighpractice.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;

import com.jia.jason.materialdesighpractice.R;

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
    private Rect finalBounds;
    private Point globalOffSet;
    private float startScale;
    private float endScale;
    private OnZoomListener listener;

    public ZoomUtil(View containerView, View expandedView) {
        this.containerView = containerView;
        this.expandedView = expandedView;
    }

    public void zoomImageFromSmall(final View smallView) {
        smallViewParent = (ViewGroup) smallView.getParent();
        if (animator != null) {
            animator.cancel();
        }

        startBounds = new Rect();
        finalBounds = new Rect();
        globalOffSet = new Point();
        smallView.getGlobalVisibleRect(startBounds);
        containerView.getGlobalVisibleRect(finalBounds, globalOffSet);
        startBounds.offset(-globalOffSet.x, -globalOffSet.y);
        finalBounds.offset(-globalOffSet.x, -globalOffSet.y);

        Log.e("startBounds:", ""+startBounds);
        Log.e("finalBounds:", ""+finalBounds);
        Log.e("globalOffSet:", ""+globalOffSet);
        //设置缩放比例和位置
        setScaleAndPos();

        expandedView.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1);
        animatorSet.play(ObjectAnimator.ofFloat(expandedView, "pivotX", 0f))
                .with(ObjectAnimator.ofFloat(expandedView, "pivotY", 0f))
                .with(ObjectAnimator.ofFloat(expandedView, "alpha", 1.0f));
        animatorSet.start();

        startZoomAnim(expandedView, startBounds, finalBounds, startScale);
        endScale = startScale;
    }

    private void setScaleAndPos() {
        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
//            float startWidth = startScale * finalBounds.width();
//            float deltaWidth = (startWidth - startBounds.width()) / 2;
//            startBounds.left -= deltaWidth;
//            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
//            float startHeight = startScale * finalBounds.height();
//            float deltaHeight = (startHeight - startBounds.height()) / 2;
//            startBounds.top -= deltaHeight;
//            startBounds.bottom += deltaHeight;
        }
    }

    private void startZoomAnim(View v, Rect startBounds, Rect finalBounds, float startScale) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(v, "x", startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(v, "y", startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(v, "scaleX", startScale, 1f))
                .with(ObjectAnimator.ofFloat(v, "scaleY", startScale, 1f));
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator = null;
                if (listener != null) {
                    listener.onExpanded();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animator = null;
                if (listener != null) {
                    listener.onExpanded();
                }
            }
        });
        animatorSet.start();
        animator = animatorSet;
    }

    public void closeZoomAnim(int position) {
        if (animator != null) {
            animator.cancel();
        }
        AnimatorSet set = new AnimatorSet();
        boolean isInBound = getScaleFinalBounds(position);
        if (isInBound) {
            set.play(ObjectAnimator.ofFloat(expandedView, "x", startBounds.left))
                    .with(ObjectAnimator.ofFloat(expandedView, "y", startBounds.top))
                    .with(ObjectAnimator.ofFloat(expandedView, "scaleX", endScale))
                    .with(ObjectAnimator.ofFloat(expandedView, "scaleY", endScale));
        } else {
            // 如果当前显示的图片不在gridview当前显示的图片中，等于越界了。这时我们就不执行缩放操作，直接渐变消失即可。
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(expandedView, "alpha", 0.1f);
            set.play(alphaAnimator);
        }
        set.setDuration(ANIMATION_DURATION);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator = null;
                if (listener != null) {
                    listener.onThumbed();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animator = null;
                if (listener != null) {
                    listener.onThumbed();
                }
            }
        });
        set.start();
        animator = set;
    }

    public boolean getScaleFinalBounds(int position) {
        //得到显示区域中第一个子视图的序号
        int firstPosition = ((AdapterView<?>)smallViewParent).getFirstVisiblePosition();
        View childView = smallViewParent.getChildAt(position - firstPosition);

        startBounds = new Rect();
        final Point globalOffset = new Point();

        try {
            //通过这个计算startBounds，得到当前view的位置，从而设定偏移值
            childView.getGlobalVisibleRect(startBounds);
        } catch (Exception e) {
            return false;
        }
        containerView.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        //设置比率
        setScaleAndPos();

        endScale = startScale;
        return true;
    }

    public void setOnZoomListener(OnZoomListener l) {
        listener = l;
    }

    public interface OnZoomListener {
        public void onExpanded();//点击后展示大图成功后调用
        public void onThumbed();//点击后缩小回小图时调用
    }

}
