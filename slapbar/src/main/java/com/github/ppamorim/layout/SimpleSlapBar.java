package com.github.ppamorim.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.github.ppamorim.SlapBar;
import com.github.ppamorim.slapbar.R;

public class SimpleSlapBar extends SlapBar {

  private int height;

  private View slapBar;

  public SimpleSlapBar(Activity activity) {
    this(activity, null);
    ViewGroup container = (ViewGroup) activity.findViewById(android.R.id.content);
    slapBar = activity.getLayoutInflater().inflate(R.layout.simple_slapbar, container, false);
    container.addView(this);
    addView(slapBar);
  }

  public SimpleSlapBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SimpleSlapBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initializeView();
  }

  public SimpleSlapBar height(int height) {
    this.height = height;
    return this;
  }

  public SimpleSlapBar initializeView() {
    setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    setDragView(slapBar);
    configDragViewHelper();
    configSlideHelper();
    return this;
  }

}
