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
package com.github.ppamorim.app;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import com.github.ppamorim.SlapBar;
import com.github.ppamorim.SlapBarCallback;
import com.github.ppamorim.SlapDuration;
import com.github.ppamorim.layout.AbstractSlapBar;
import com.github.ppamorim.layout.ButtonSlapBar;
import com.github.ppamorim.layout.SingleSlapBar;

public class BaseActivity extends AbstractActivity {

  private SingleSlapBar singleSlapBar;

  @InjectView(R.id.center_slap) TextView centerSlap;

  @OnClick(R.id.center_slap) void onCenterClick() {
    singleSlapBar.show(SlapDuration.SHORT);
  }

  @OnLongClick(R.id.center_slap) boolean onCenterLongClick() {
    singleSlapBar.moveToCenter();
    return true;
  }

  @Override protected int getLayoutResourceId() {
    return R.layout.activity_base;
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    singleSlapBar = new SingleSlapBar(this);
    singleSlapBar.config()
        .backgroundColor(getResources().getColor(R.color.black_gray))
        .text("test")
        .textGravity(Gravity.LEFT)
        .textPadding(16, 16, 16, 16)
        .textColor(R.color.blue)
        .setTension(30)
        .setFriction(2)
        .setSlapBarCallback(slapBarCallback)
        .initializeView();

  }

  private SlapBarCallback slapBarCallback = new SlapBarCallback() {
    @Override public void updatePosition(float yValue) {
      ViewCompat.setTranslationY(centerSlap, yValue);
    }

    @Override public void notifyShown() {

    }

    @Override public void notifyHide() {

    }
  };

}
