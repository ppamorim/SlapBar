package com.github.ppamorim;

import android.content.Context;
import android.os.Handler;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

public class SlideHelper {

  private static final int DELAY = 100;
  private static final int DEFAULT_TENSION = 40;
  private static final int DEFAULT_FRICTION = 6;

  private boolean canAnimate = true;
  private double tension = DEFAULT_TENSION;
  private double friction = DEFAULT_FRICTION;

  private SlapBar slapBar;

  private static volatile Spring singleton = null;

  private SlapBarPositionCallback callback;

  public SlideHelper(Context context, SlapBar slapBar) {
    this.slapBar = slapBar;
  }

  public SlideHelper setCallback(SlapBarPositionCallback callback) {
    this.callback = callback;
    return this;
  }

  public boolean canAnimate() {
    return canAnimate;
  }

  public void setCanAnimate(boolean canAnimate) {
    this.canAnimate = canAnimate;
  }

  public double getTension() {
    return tension;
  }

  public SlideHelper setTension(double tension) {
    this.tension = tension;
    return this;
  }

  public double getFriction() {
    return friction;
  }

  public SlideHelper setFriction(double friction) {
    this.friction = friction;
    return this;
  }

  public Spring getSpring() {
    if (singleton == null) {
      synchronized (Spring.class) {
        if (singleton == null) {
          singleton = SpringSystem
              .create()
              .createSpring()
              .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
        }
      }
    }
    return singleton;
  }

  public void onAttachedToWindow() {
    getSpring().addListener(springListener);
    slapBar.post(new Runnable() {
      @Override public void run() {
        getSpring().setCurrentValue(1).setAtRest();
      }
    });
  }

  public void onDetachedFromWindow() {
    getSpring().removeListener(springListener);
  }

  public void slideIn() {
    slide(1, 0);
  }

  public void slideOut() {
    slide(0, 1);
  }

  private void slide(int start, final int end) {
    getSpring().setCurrentValue(start).setAtRest();
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        getSpring().setEndValue(end);
      }
    }, DELAY);
  }

  private SpringListener springListener = new SpringListener() {
    @Override public void onSpringUpdate(Spring spring) {
      if (callback != null) {
        callback.updatePosition((float) SpringUtil.mapValueFromRangeToRange(
            spring.getCurrentValue(), 0, 1, 0, callback.endPosition()));
        callback.onProgress(spring.getCurrentValue());
      }
    }
    @Override public void onSpringAtRest(Spring spring) {
      if (callback != null) {
        switch ((int) spring.getEndValue()) {
          case 1:
            canAnimate = true;
            callback.notifyHide();
            break;
          case 0:
            callback.notifyShown();
            break;
          default:
            break;
        }
      }
    }
    @Override public void onSpringActivate(Spring spring) { }
    @Override public void onSpringEndStateChange(Spring spring) { }
  };

}
