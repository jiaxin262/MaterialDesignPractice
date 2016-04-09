package com.jia.jason.materialdesighpractice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.util.ZoomUtil;

public class ZoomImageView extends View implements GestureDetector.OnGestureListener,
		GestureDetector.OnDoubleTapListener{

	/**
	 * 初始化状态常量
	 */
	public static final int STATUS_INIT = 1;

	/**
	 * 图片放大状态常量
	 */
	public static final int STATUS_ZOOM_OUT = 2;

	/**
	 * 图片缩小状态常量
	 */
	public static final int STATUS_ZOOM_IN = 3;

	/**
	 * 图片拖动状态常量
	 */
	public static final int STATUS_MOVE = 4;

	/**
	 * 用于对图片进行移动和缩放变换的矩阵
	 */
	private Matrix matrix = new Matrix();

	/**
	 * 待展示的Bitmap对象
	 */
	private Bitmap sourceBitmap;

	/**
	 * 记录当前操作的状态，可选值为STATUS_INIT、STATUS_ZOOM_OUT、STATUS_ZOOM_IN和STATUS_MOVE
	 */
	private int currentStatus;

	/**
	 * ZoomImageView控件的宽度
	 */
	private int width;

	/**
	 * ZoomImageView控件的高度
	 */
	private int height;

	/**
	 * 记录两指同时放在屏幕上时，中心点的横坐标值
	 */
	private float centerPointX;

	/**
	 * 记录两指同时放在屏幕上时，中心点的纵坐标值
	 */
	private float centerPointY;

	/**
	 * 记录当前图片的宽度，图片被缩放时，这个值会一起变动
	 */
	private float currentBitmapWidth;

	/**
	 * 记录当前图片的高度，图片被缩放时，这个值会一起变动
	 */
	private float currentBitmapHeight;

	/**
	 * 记录手指在横坐标方向上的移动距离
	 */
	private float movedDistanceX;

	/**
	 * 记录手指在纵坐标方向上的移动距离
	 */
	private float movedDistanceY;

	/**
	 * 记录图片在矩阵上的横向偏移值
	 */
	private float totalTranslateX;

	/**
	 * 记录图片在矩阵上的纵向偏移值
	 */
	private float totalTranslateY;

	/**
	 * 记录图片在矩阵上的总缩放比例
	 */
	private float totalRatio;

	/**
	 * 记录手指移动的距离所造成的缩放比例
	 */
	private float scaledRatio;

	/**
	 * 记录图片初始化时的缩放比例
	 */
	private float initRatio;

	/**
	 * 记录上次两指之间的距离
	 */
	private double lastFingerDis;

	GestureDetectorCompat jDetector;
	private static final String DEBUG_TAG = "JGESTURE";
	private ZoomUtil zoomUtil;
	private int posInViewPager;

	/**
	 * ZoomImageView构造函数，将当前操作状态设为STATUS_INIT。
	 * 
	 * @param context
	 * @param attrs
	 */
	public ZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		currentStatus = STATUS_INIT;
		setBackgroundColor(getResources().getColor(R.color.grey));
		jDetector = new GestureDetectorCompat(context, this);
		jDetector.setOnDoubleTapListener(this);
	}

	/**
	 * 将待展示的图片设置进来。
	 * 
	 * @param bitmap
	 *            待展示的Bitmap对象
	 */
	public void setImageBitmap(Bitmap bitmap) {
		sourceBitmap = bitmap;
		invalidate();
	}

	public void setZoomUtil(ZoomUtil zoomUtil, int position) {
		this.zoomUtil = zoomUtil;
		posInViewPager = position;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
			// 分别获取到ZoomImageView的宽度和高度
			width = getWidth();
			height = getHeight();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.jDetector.onTouchEvent(event);
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_POINTER_DOWN:
			if (event.getPointerCount() == 2) {
				// 当有两个手指按在屏幕上时，计算两指之间的距离
				lastFingerDis = distanceBetweenFingers(event);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getPointerCount() == 2) {
				// 有两个手指按在屏幕上移动时，为缩放状态
				centerPointBetweenFingers(event);
				double fingerDis = distanceBetweenFingers(event);
				if (fingerDis > lastFingerDis) {
					currentStatus = STATUS_ZOOM_OUT;
				} else {
					currentStatus = STATUS_ZOOM_IN;
				}
				// 进行缩放倍数检查，最大只允许将图片放大4倍，最小可以缩小到初始化比例
				if ((currentStatus == STATUS_ZOOM_OUT && totalRatio < 4 * initRatio)
						|| (currentStatus == STATUS_ZOOM_IN && totalRatio > initRatio)) {
					scaledRatio = (float) (fingerDis / lastFingerDis);
					totalRatio = totalRatio * scaledRatio;
					if (totalRatio > 4 * initRatio) {
						totalRatio = 4 * initRatio;
					} else if (totalRatio < initRatio) {
						totalRatio = initRatio;
					}
					// 调用onDraw()方法绘制图片
					invalidate();
					lastFingerDis = fingerDis;
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**onDoubleTapListener methods*/
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		//Log.e(DEBUG_TAG, "onSingleTapConfirmed: " + e.toString());
		zoomUtil.closeZoomAnim(posInViewPager);
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		//Log.e(DEBUG_TAG, "onDoubleTap: " + e.toString());
		currentStatus = STATUS_ZOOM_OUT;
		totalRatio = totalRatio == 0 ? 1 : totalRatio;
		scaledRatio = 2f;
		totalRatio = totalRatio * scaledRatio;
		if (totalRatio > 4 * initRatio) {
			totalRatio = 4 * initRatio;
		}
		invalidate();
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		//Log.e(DEBUG_TAG, "onDoubleTapEvent: " + e.toString());
		return true;
	}

	/**onGestureListener methods*/
	@Override
	public boolean onDown(MotionEvent e) {
		//Log.e(DEBUG_TAG, "onDown: " + e.getPointerCount());
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		//Log.e(DEBUG_TAG, "onShowPress: " + e.toString());
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		//Log.e(DEBUG_TAG, "onScroll: " + e1.toString());
		if (e1.getPointerCount() == 1) {    // 只有单指按在屏幕上移动时，为拖动状态
			currentStatus = STATUS_MOVE;
			movedDistanceX = distanceX;
			movedDistanceY = distanceY;
			if (totalTranslateX + movedDistanceX > 0) {
				movedDistanceX = 0;
			} else if (width - (totalTranslateX + movedDistanceX) > currentBitmapWidth) {
				movedDistanceX = 0;
			}
			if (totalTranslateY + movedDistanceY > 0) {
				movedDistanceY = 0;
			} else if (height - (totalTranslateY + movedDistanceY) > currentBitmapHeight) {
				movedDistanceY = 0;
			}
			invalidate();
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		//Log.e(DEBUG_TAG, "onLongPress: " + e.toString());
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		//Log.e(DEBUG_TAG, "onFling: " + e1.toString() + "###" + e2.toString());
		return true;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		//Log.e(DEBUG_TAG, "onSingleTapUp: " + e.toString());
		return true;
	}

	/**
	 * 根据currentStatus的值来决定对图片进行什么样的绘制操作。
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (currentStatus) {
		case STATUS_ZOOM_OUT:
		case STATUS_ZOOM_IN:
			zoom(canvas);
			break;
		case STATUS_MOVE:
			move(canvas);
			break;
		case STATUS_INIT:
			initBitmap(canvas);
		default:
			canvas.drawBitmap(sourceBitmap, matrix, null);
			break;
		}
	}

	/**
	 * 对图片进行缩放处理。
	 * 
	 * @param canvas
	 */
	private void zoom(Canvas canvas) {
		matrix.reset();
		// 将图片按总缩放比例进行缩放
		matrix.postScale(totalRatio, totalRatio);
		float scaledWidth = sourceBitmap.getWidth() * totalRatio;
		float scaledHeight = sourceBitmap.getHeight() * totalRatio;
		float translateX = 0f;
		float translateY = 0f;
		// 如果当前图片宽度小于屏幕宽度，则按屏幕中心的横坐标进行水平缩放。否则按两指的中心点的横坐标进行水平缩放
		if (currentBitmapWidth < width) {
			translateX = (width - scaledWidth) / 2f;
		} else {
			translateX = totalTranslateX * scaledRatio + centerPointX * (1 - scaledRatio);
			// 进行边界检查，保证图片缩放后在水平方向上不会偏移出屏幕
			if (translateX > 0) {
				translateX = 0;
			} else if (width - translateX > scaledWidth) {
				translateX = width - scaledWidth;
			}
		}
		// 如果当前图片高度小于屏幕高度，则按屏幕中心的纵坐标进行垂直缩放。否则按两指的中心点的纵坐标进行垂直缩放
		if (currentBitmapHeight < height) {
			translateY = (height - scaledHeight) / 2f;
		} else {
			translateY = totalTranslateY * scaledRatio + centerPointY * (1 - scaledRatio);
			// 进行边界检查，保证图片缩放后在垂直方向上不会偏移出屏幕
			if (translateY > 0) {
				translateY = 0;
			} else if (height - translateY > scaledHeight) {
				translateY = height - scaledHeight;
			}
		}
		// 缩放后对图片进行偏移，以保证缩放后中心点位置不变
		matrix.postTranslate(translateX, translateY);
		totalTranslateX = translateX;
		totalTranslateY = translateY;
		currentBitmapWidth = scaledWidth;
		currentBitmapHeight = scaledHeight;
		canvas.drawBitmap(sourceBitmap, matrix, null);
	}

	/**
	 * 对图片进行平移处理
	 * 
	 * @param canvas
	 */
	private void move(Canvas canvas) {
		matrix.reset();
		// 根据手指移动的距离计算出总偏移值
		totalTranslateX -= movedDistanceX;
		totalTranslateY -= movedDistanceY;
		// 先按照已有的缩放比例对图片进行缩放
		matrix.postScale(totalRatio, totalRatio);
		// 再根据移动距离进行偏移
		matrix.postTranslate(totalTranslateX, totalTranslateY);
		canvas.drawBitmap(sourceBitmap, matrix, null);
	}

	/**
	 * 对图片进行初始化操作，包括让图片居中，以及当图片大于屏幕宽高时对图片进行压缩。
	 * 
	 * @param canvas
	 */
	private void initBitmap(Canvas canvas) {
		if (sourceBitmap != null) {
			matrix.reset();
			int bitmapWidth = sourceBitmap.getWidth();
			int bitmapHeight = sourceBitmap.getHeight();
			if (bitmapWidth - width > bitmapHeight - height) {
				// 当图片宽度大于屏幕宽度时，将图片等比例压缩，使它可以完全显示出来
				float ratio = width / (bitmapWidth * 1.0f);
				matrix.postScale(ratio, ratio);
				float translateY = (height - (bitmapHeight * ratio)) / 2f;
				// 在纵坐标方向上进行偏移，以保证图片居中显示
				matrix.postTranslate(0, translateY);
				totalTranslateY = translateY;
				totalRatio = initRatio = ratio;
			} else {
				// 当图片高度大于屏幕高度时，将图片等比例压缩，使它可以完全显示出来
				float ratio = height / (bitmapHeight * 1.0f);
				matrix.postScale(ratio, ratio);
				float translateX = (width - (bitmapWidth * ratio)) / 2f;
				// 在横坐标方向上进行偏移，以保证图片居中显示
				matrix.postTranslate(translateX, 0);
				totalTranslateX = translateX;
				totalRatio = initRatio = ratio;
			}
			currentBitmapWidth = bitmapWidth * initRatio;
			currentBitmapHeight = bitmapHeight * initRatio;
			canvas.drawBitmap(sourceBitmap, matrix, null);
		}
	}

	/**
	 * 计算两个手指之间的距离。
	 * 
	 * @param event
	 * @return 两个手指之间的距离
	 */
	private double distanceBetweenFingers(MotionEvent event) {
		float disX = Math.abs(event.getX(0) - event.getX(1));
		float disY = Math.abs(event.getY(0) - event.getY(1));
		return Math.sqrt(disX * disX + disY * disY);
	}

	/**
	 * 计算两个手指之间中心点的坐标。
	 * 
	 * @param event
	 */
	private void centerPointBetweenFingers(MotionEvent event) {
		float xPoint0 = event.getX(0);
		float yPoint0 = event.getY(0);
		float xPoint1 = event.getX(1);
		float yPoint1 = event.getY(1);
		centerPointX = (xPoint0 + xPoint1) / 2;
		centerPointY = (yPoint0 + yPoint1) / 2;
	}

}