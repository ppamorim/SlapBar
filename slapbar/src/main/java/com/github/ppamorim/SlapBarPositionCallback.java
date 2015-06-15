package com.github.ppamorim;

public interface SlapBarPositionCallback extends SlapBarCallback {
  double endPosition();
  void onProgress(double value);
}
