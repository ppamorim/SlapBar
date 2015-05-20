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

import android.support.v4.widget.ViewDragHelper;
import android.view.View;

/**
 * This class provides a helper to touch
 * and drag on FlapBar class.
 *
 * @author Pedro Paulo de Amorim
 *
 */
public class SlapBarHelperCallback extends ViewDragHelper.Callback {

  private final View bar;
  private final SlapBar flapBar;

  /**
   * The constructor get the instance of FlapBar and Bar
   *
   * @param flapBar provide the instance of FlapBar
   * @param bar provide the instance bar, this is inflated on FlapBar
   */
  public SlapBarHelperCallback(SlapBar flapBar, View bar) {
    this.flapBar = flapBar;
    this.bar = bar;
  }

  /**
   * Check if view on focus is the bar
   *
   * @param child return the view on focus
   * @param pointerId return the id of view
   * @return if the child on focus is equals the bar
   */
  @Override public boolean tryCaptureView(View child, int pointerId) {
    return child.equals(bar);
  }

  /**
   * Return the value of slide based
   * on left and width of the element
   *
   * @param child return the view on focus
   * @param left return the left size of SlapBar
   * @param dx return the scroll on x-axis
   * @return the offset of slide
   */
  @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
    return Math.min(left, flapBar.getWidth());
  }

  /**
   * This guy return the max value of view that can
   * slide based on #camplViewPositionHorizontal
   *
   * @param child return the view on focus
   * @return max distance that view on focus can slide
   */
  @Override public int getViewHorizontalDragRange(View child) {
    return (int) flapBar.getHorizontalDragRange();
  }

  /**
   * This is called only the touch on bar view is released.
   *
   * @param releasedChild return the view on focus
   * @param xvel return the speed of X animation
   * @param yvel return the speed of Y animation
   */
  @Override public void onViewReleased(View releasedChild, float xvel, float yvel) {
    super.onViewReleased(releasedChild, xvel, yvel);
    flapBar.verifyPosition();
  }

}
