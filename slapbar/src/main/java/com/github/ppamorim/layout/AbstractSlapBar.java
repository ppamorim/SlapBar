package com.github.ppamorim.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.github.ppamorim.SlapBar;

public abstract class AbstractSlapBar extends SlapBar {

  private int width = -1;
  private int height = -1;

  private View slapBar;

  public AbstractSlapBar(Activity activity) {
    this(activity, null);
    ViewGroup container = (ViewGroup) activity.findViewById(android.R.id.content);
    slapBar = activity.getLayoutInflater().inflate(getLayoutId(), container, false);
    container.addView(this);
    addView(slapBar);
  }

  public AbstractSlapBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AbstractSlapBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initializeView();
  }

  public View getSlapBar() {
    return slapBar;
  }

  public AbstractSlapBar width(int width) {
    this.width = width;
    return this;
  }

  public AbstractSlapBar height(int height) {
    this.height = height;
    return this;
  }

  public AbstractSlapBar initializeView() {
    setVisibility(GONE);
    checkSizes();
    FrameLayout.LayoutParams layoutParams = new LayoutParams(width, height);
    layoutParams.gravity = Gravity.BOTTOM;
    setLayoutParams(layoutParams);
    setDragView(slapBar);
    configDragViewHelper();
    configSlideHelper();
    return this;
  }

  private void checkSizes() {
    if(width <= 0) {
      width = LayoutParams.MATCH_PARENT;
    }
    if(height <= 0) {
      height = LayoutParams.WRAP_CONTENT;
    }
  }

  protected abstract int getLayoutId();

}
