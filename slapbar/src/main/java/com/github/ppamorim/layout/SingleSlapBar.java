package com.github.ppamorim.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.github.ppamorim.Utils.ViewUtil;
import com.github.ppamorim.slapbar.R;

public class SingleSlapBar extends AbstractSlapBar {

  private TextView textView;

  public SingleSlapBar(Activity activity) {
    super(activity);
  }

  public SingleSlapBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SingleSlapBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override protected int getLayoutId() {
    return R.layout.single_slapbar;
  }

  public SingleSlapBar config() {
    textView = (TextView)getSlapBar().findViewById(R.id.text);
    return this;
  }

  public SingleSlapBar backgroundColor(int colorId) {
    getSlapBar().setBackgroundColor(colorId);
    return this;
  }

  public SingleSlapBar textPadding(int leftDp, int topDp, int rightDp, int bottomDp) {
    textView.setPadding(
        ViewUtil.dpToPx(getResources(), leftDp),
        ViewUtil.dpToPx(getResources(), topDp),
        ViewUtil.dpToPx(getResources(), rightDp),
        ViewUtil.dpToPx(getResources(), bottomDp));
    return this;
  }

  public SingleSlapBar textColor(int colorId) {
    textView.setTextColor(getResources().getColor(colorId));
    return this;
  }

  public SingleSlapBar text(String text) {
    textView.setText(text);
    return this;
  }

}
