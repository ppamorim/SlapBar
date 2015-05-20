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

import butterknife.InjectView;
import butterknife.OnClick;
import com.github.ppamorim.SlapBar;

public class BaseActivity extends AbstractActivity {

  @InjectView(R.id.slap_bar) SlapBar slapBar;

  @OnClick(R.id.center_slap) void onCenterClick() {
    slapBar.moveToCenter();
  }

  @Override protected int getLayoutResourceId() {
    return R.layout.activity_base;
  }

}
