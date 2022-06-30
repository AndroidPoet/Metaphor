package com.androidpoet.metaphordemo

import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.*
import com.google.android.material.transition.MaterialArcMotion

object MetaphorUtils {

  fun getFragmentMetaphor(
    fragment: Fragment,
    enterAnimation: MetaphorAnimation,
    exitAnimation: MetaphorAnimation
  ): MetaphorFragment {

    return MetaphorFragment.Builder(fragment)
      .setEnterDuration(500)
      .setEnterAnimation(enterAnimation)
      .setExitAnimation(exitAnimation)
      .build()
  }


  fun getActivityMetaphor(
    appCompatActivity: AppCompatActivity,
    enterAnimation: MetaphorAnimation,
    exitAnimation: MetaphorAnimation
  ): MetaphorActivity {

    return MetaphorActivity.Builder(appCompatActivity)
      .setEnterAnimation(enterAnimation)
      .setExitAnimation(exitAnimation)
      .build()
  }


  fun View.getViewMetaphor(
    metaphorAnimation: MetaphorAnimation,
    end: View
  ): MetaphorView {

    return MetaphorView.Builder(this)
      .setDuration(1000)
      .setEndView(end)
      .setMetaphorAnimation(metaphorAnimation)
      .setMotion(MaterialArcMotion())
      .build()
  }

  fun PopupWindow.getPopupWindowMetaphor(
    metaphorAnimation: MetaphorAnimation,
  ): MetaphorWindow {

    return MetaphorWindow.Builder(this)
      .setEnterDuration(500)
      .setExitDuration(500)
      .setEnterAnimation(metaphorAnimation)
      .setExitAnimation(metaphorAnimation)
      .build()
  }


}