package com.androidpoet.metaphordemo.factory

import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.MetaphorAnimation
import com.androidpoet.metaphor.MetaphorFragment


class MetaphorFragmentFactory : MetaphorFragment.Factory() {
  override fun create(fragment: Fragment): MetaphorFragment {
    return MetaphorFragment.Builder(fragment)
      .setEnterAnimation(MetaphorAnimation.ElevationScale)
      .setExitAnimation(MetaphorAnimation.SharedAxisXBackward)
      .build()
  }
}