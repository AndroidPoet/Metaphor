package com.androidpoet.metaphordemo.activties

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.androidpoet.metaphor.metaphorMaterialContainerTransformViewIntoAnotherView
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.ActivityMainBinding
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val decor: View = window.decorView
      decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }



    Glide.with(this).load(R.drawable.metalogo).into(binding.logo)
    Handler().postDelayed(
      Runnable {
        val intent = Intent(this, FragmentHostActivty::class.java)
        startActivity(intent)
        finish()
      },
      2000
    )
  }
}
