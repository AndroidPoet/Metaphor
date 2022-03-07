/*
 *
 *  * Copyright (C) 2022 androidpoet
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */


package com.androidpoet.metaphordemo.activties

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.androidpoet.metaphor.metaphorHideViewWithAnimation
import com.androidpoet.metaphor.show
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.ActivityHostActivtyBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentHostActivty : AppCompatActivity() {

  private lateinit var binding: ActivityHostActivtyBinding

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityHostActivtyBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navView: BottomNavigationView = binding.navView

    val navController = findNavController(R.id.nav_host_fragment_activity_host_activty)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    val appBarConfiguration = AppBarConfiguration(
      setOf(
        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
      )
    )
    navView.setupWithNavController(navController)
    // Hide bottom nav on screens which don't require it
    lifecycleScope.launchWhenResumed {
      navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
          R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications -> navView.show()

          else -> navView.metaphorHideViewWithAnimation(binding.container)
        }
      }
    }
    if (Build.VERSION.SDK_INT >= 30) {

      // Root ViewGroup of my activity
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor: View = window.decorView
          decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

      }
      ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->

        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

        // Apply the insets as a margin to the view. Here the system is setting
        // only the bottom, left, and right dimensions, but apply whichever insets are
        // appropriate to your layout. You can also update the view padding
        // if that's more appropriate.

        view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
          leftMargin = insets.left
          bottomMargin = insets.bottom
          rightMargin = insets.right
        }

        // Return CONSUMED if you don't want want the window insets to keep being
        // passed down to descendant views.
        WindowInsetsCompat.CONSUMED
      }

    }
  }

}
