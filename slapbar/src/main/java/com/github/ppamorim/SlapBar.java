/*
* Copyright (C) 2015 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.ppamorim;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.github.ppamorim.slapbar.R;
import com.nineoldandroids.view.ViewHelper;

/**
 *
 * This is the correct way to create a SnackBar
 * because it uses DragViewHelper class.
 *
 * @author Pedro Paulo de Amorim
 *
 */
public class SlapBar extends FrameLayout {

  private static final int INVALID_POINTER = -1;
  private static final float DEFAULT_DRAG_LIMIT = 0.5f;
  private static final float SENSITIVITY = 1.0f;

  private boolean isAnimationRunning;
  private int activePointerId = INVALID_POINTER;
  private float horizontalDragRange;
  private float dragLimit;
  private TypedArray attributes;

  private View dragView;

  private SlapBarHelperCallback flapBarHelperCallback;
  private ViewDragHelper dragHelper;
  private SlideHelper slideHelper;

  public SlapBar(Context context) {
    super(context);
  }

  public SlapBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    initializeAttributes(attrs);
  }

  public SlapBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initializeAttributes(attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (!isInEditMode()) {
      setVisibility(GONE);
      mapGUI(attributes);
      attributes.recycle();
      configDragViewHelper();
      configSlideHelper();
    }
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isEnabled()) {
      return false;
    }
    switch (MotionEventCompat.getActionMasked(ev)) {
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        dragHelper.cancel();
        return false;
      case MotionEvent.ACTION_DOWN:
        int index = MotionEventCompat.getActionIndex(ev);
        activePointerId = MotionEventCompat.getPointerId(ev, index);
        if (activePointerId == INVALID_POINTER) {
          return false;
        }
      default:
        return dragHelper.shouldInterceptTouchEvent(ev);
    }
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    int actionMasked = MotionEventCompat.getActionMasked(ev);
    if ((actionMasked & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
      activePointerId = MotionEventCompat.getPointerId(ev, actionMasked);
    }
    if (activePointerId == INVALID_POINTER) {
      return false;
    }
    dragHelper.processTouchEvent(ev);
    boolean isDragViewHit = isViewHit(dragView, (int) ev.getX(), (int) ev.getY());
    return isDragViewHit;
  }

  @Override public void computeScroll() {
    if (!isInEditMode() && dragHelper != null && dragHelper.continueSettling(true)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  @Override protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    setHorizontalDragRange(width);
  }

  public View getDragView() {
    return dragView;
  }

  public void setDragView(View dragView) {
    this.dragView = dragView;
  }

  private void initializeAttributes(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.dragger_layout);
    if(attributes != null) {
      this.dragLimit = attributes.getFloat(R.styleable.dragger_layout_drag_limit, DEFAULT_DRAG_LIMIT);
      this.attributes = attributes;
    }
  }

  private void mapGUI(TypedArray attributes) {
    if (getChildCount() > 1) {
      throw new IllegalStateException("FlapBar must contains only one direct child");
    }
    if (attributes != null && dragView == null) {
      int dragViewId = attributes.getResourceId(
          R.styleable.dragger_layout_drag_view_id, 0);
      dragView = findViewById(dragViewId);
    }
  }

  public void configDragViewHelper() {
    flapBarHelperCallback = new SlapBarHelperCallback(this, dragView);
    dragHelper = ViewDragHelper.create(this, SENSITIVITY, flapBarHelperCallback);
  }

  public void configSlideHelper() {
    if(slideHelper == null) {
      slideHelper = new SlideHelper(getContext(), this);
    }
  }

  private boolean smoothSlideTo(View view, int x, int y) {
    if (dragHelper.smoothSlideViewTo(view, x, y)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }

  public float getHorizontalDragRange() {
    return horizontalDragRange;
  }

  private void setHorizontalDragRange(float horizontalDragRange) {
    this.horizontalDragRange = horizontalDragRange;
  }

  private boolean isViewHit(View view, int x, int y) {
    int[] viewLocation = new int[2];
    view.getLocationOnScreen(viewLocation);
    int[] parentLocation = new int[2];
    this.getLocationOnScreen(parentLocation);
    int screenX = parentLocation[0] + x;
    int screenY = parentLocation[1] + y;
    return screenX >= viewLocation[0]
        && screenX < viewLocation[0] + view.getWidth()
        && screenY >= viewLocation[1]
        && screenY < viewLocation[1] + view.getHeight();
  }

  public void verifyPosition() {
    float xValue = ViewHelper.getX(dragView);
    if(isDragViewAboveTheMiddle(xValue, dragView.getWidth())) {
      if(xValue > 0) {
        closeToRight();
      } else {
        closeToLeft();
      }
    } else {
      moveToCenter();
    }
  }

  private boolean isDragViewAboveTheMiddle(float xValue, int parentSize) {
    if(xValue > 0) {
      return parentSize < (xValue + (parentSize * dragLimit));
    } else {
      return parentSize < (-xValue + (parentSize * dragLimit));
    }
  }

  public void closeToRight() {
    smoothSlideTo(dragView, (int) getHorizontalDragRange(), 0);
  }

  public void closeToLeft() {
    smoothSlideTo(dragView, (int) -getHorizontalDragRange(), 0);
  }

  public void moveToCenter() {
    smoothSlideTo(dragView, 0, 0);
  }

  public void show() {
    slideHelper.slideIn();
  }

  public void showWithDelay(int duration) {
    if(!isAnimationRunning) {
      isAnimationRunning = true;
      show();
      if(duration != SlapDuration.INFINITE) {
        hideWithDelay(duration);
      }
    }
  }

  public void hide() {
    slideHelper.slideOut();
  }

  public void hideWithDelay(int duration) {
    postDelayed(new Runnable() {
      @Override public void run() {
        hide();
      }
    }, duration);
  }

  public void resetAnimation() {
    isAnimationRunning = false;
  }

}
