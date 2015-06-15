package com.github.ppamorim.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;
import com.github.ppamorim.SlapBarCallback;
import com.github.ppamorim.Utils.ViewUtil;
import com.github.ppamorim.slapbar.R;

public class ButtonSlapBar extends AbstractSlapBar {

  private TextView textView;
  private Button button;

  private ButtonCallback buttonCallback;

  public ButtonSlapBar(Activity activity) {
    super(activity);
  }

  public ButtonSlapBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ButtonSlapBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override protected int getLayoutId() {
    return R.layout.button_slapbar;
  }

  public ButtonSlapBar config() {
    textView = (TextView) getSlapBar().findViewById(R.id.text);
    button = (Button) getSlapBar().findViewById(R.id.button);
    return this;
  }

  public ButtonSlapBar backgroundColor(int colorId) {
    getSlapBar().setBackgroundColor(colorId);
    return this;
  }

  public ButtonSlapBar buttonColor(int colorId) {
    button.setBackgroundColor(colorId);
    return this;
  }

  public ButtonSlapBar textButtonColor(int colorId) {
    button.setTextColor(getResources().getColor(colorId));
    return this;
  }

  public ButtonSlapBar buttonText(String text) {
    button.setText(text);
    return this;
  }

  public ButtonSlapBar textPadding(int leftDp, int topDp, int rightDp, int bottomDp) {
    textView.setPadding(ViewUtil.dpToPx(getResources(), leftDp),
        ViewUtil.dpToPx(getResources(), topDp), ViewUtil.dpToPx(getResources(), rightDp),
        ViewUtil.dpToPx(getResources(), bottomDp));
    return this;
  }

  public ButtonSlapBar textGravity(int gravity) {
    textView.setGravity(gravity);
    return this;
  }

  public ButtonSlapBar textColor(int colorId) {
    textView.setTextColor(getResources().getColor(colorId));
    return this;
  }

  public ButtonSlapBar text(String text) {
    textView.setText(text);
    return this;
  }

  public ButtonSlapBar callback(ButtonCallback buttonCallback) {
    this.buttonCallback = buttonCallback;
    return this;
  }

  public interface ButtonCallback {
    void onButtonClick();
  }

}
