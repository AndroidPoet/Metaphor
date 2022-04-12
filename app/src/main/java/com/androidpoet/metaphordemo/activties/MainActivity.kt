
package com.androidpoet.metaphordemo.activties

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.androidpoet.metaphor.MetaphorActivity
import com.androidpoet.metaphor.MetaphorAnimation
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val decor: View = window.decorView
      decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    Glide.with(this).load(R.drawable.metalogo).into(binding.logo)
    Handler().postDelayed(
      {
        val intent = Intent(this, FragmentHostActivty::class.java)
        startActivity(intent)
        finish()
      },
      2000
    )




  }


}
