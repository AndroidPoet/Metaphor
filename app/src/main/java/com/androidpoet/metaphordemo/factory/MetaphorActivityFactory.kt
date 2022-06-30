
package com.androidpoet.metaphordemo.factory

import androidx.appcompat.app.AppCompatActivity
import com.androidpoet.metaphor.MetaphorActivity
import com.androidpoet.metaphor.MetaphorAnimation

class MetaphorActivityFactory() : MetaphorActivity.Factory() {
  override fun create(fragment: AppCompatActivity): MetaphorActivity {
    return MetaphorActivity.Builder(fragment)
      .setEnterAnimation(MetaphorAnimation.ElevationScale)
      .setExitAnimation(MetaphorAnimation.SharedAxisXBackward)
      .build()
  }
}
