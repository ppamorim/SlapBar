package com.github.ppamorim;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.github.ppamorim.slapbar.R;

public class SlideHelper {

  private Context context;
  private SlapBar slapBar;

  public SlideHelper(Context context, SlapBar slapBar) {
    this.context = context;
    this.slapBar = slapBar;
  }

  public void slideIn() {
    slapBar.setVisibility(View.VISIBLE);
    Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
    slideIn.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        slapBar.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animation animation) {
        slapBar.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }
    });
    slapBar.startAnimation(slideIn);
  }

  public void slideOut() {
    Animation slideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
    slideOut.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        slapBar.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animation animation) {
        slapBar.setVisibility(View.GONE);
        slapBar.resetAnimation();
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }
    });
    slapBar.startAnimation(slideOut);
  }

}
