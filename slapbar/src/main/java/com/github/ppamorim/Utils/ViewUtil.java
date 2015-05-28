package com.github.ppamorim.Utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class ViewUtil {

  public static int dpToPx(Resources resources, float dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
  }

}
