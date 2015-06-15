package com.github.ppamorim;

public interface SlapBarCallback {
  void updatePosition(float yValue);
  void notifyShown();
  void notifyHide();
}
