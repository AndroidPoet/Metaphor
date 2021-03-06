
package com.androidpoet.metaphordemo.ui.notifications

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.MetaphorAnimation
import com.androidpoet.metaphor.MetaphorFragment
import com.androidpoet.metaphor.MetaphorView
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.FragmentNotificationsBinding
import com.bumptech.glide.Glide
import java.util.Random

class NotificationsFragment : Fragment() {

  private var _binding: FragmentNotificationsBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private val images = arrayListOf(
    R.drawable.undraw_drag,
    R.drawable.undraw_winners,
    R.drawable.undraw_social_sharing,
    R.drawable.undraw_drag,
    R.drawable.undraw_winners,
    R.drawable.undraw_social_sharing,
    R.drawable.undraw_drag,
    R.drawable.undraw_winners,
    R.drawable.undraw_social_sharing,
    R.drawable.undraw_winners
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // FadeThrough inside fragment
    val meta = MetaphorFragment.Builder(this)
      .setEnterAnimation(MetaphorAnimation.ElevationScale)
      .setExitAnimation(MetaphorAnimation.ElevationScaleGrow)
      .build()
    meta.animate()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

    return binding.root
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img)
    Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img2)
    Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img3)

    binding.SharedX.setOnClickListener {

      val meta = MetaphorView.Builder(binding.img)
        .setDuration(1000)
        .setEndView(binding.img)
        .setMetaphorAnimation(MetaphorAnimation.Fade)
        .build()
      meta.animate()
    }

    binding.SharedY.setOnClickListener {

      val meta = MetaphorView.Builder(binding.img)
        .setDuration(1000)
        .setEndView(binding.img)
        .setMetaphorAnimation(MetaphorAnimation.Fade)
        .build()
      meta.animate()
    }
    binding.SharedZ.setOnClickListener {

      val meta = MetaphorView.Builder(binding.img)
        .setDuration(1000)
        .setEndView(binding.img)
        .setMetaphorAnimation(MetaphorAnimation.FadeThrough)
        .build()
      meta.animate()
    }

    binding.materialFadeThrough.setOnClickListener {

      val meta = MetaphorView.Builder(binding.img2)
        .setDuration(1000)
        .setEndView(binding.img2)
        .setMetaphorAnimation(MetaphorAnimation.FadeThrough)
        .build()
      meta.animate()
    }

    binding.materialFade.setOnClickListener {

      Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img3)
      val meta = MetaphorView.Builder(binding.img3)
        .setDuration(1000)
        .setEndView(binding.img3)
        .setMetaphorAnimation(MetaphorAnimation.FadeThrough)
        .build()
      meta.animate()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()

    _binding = null
  }

  private fun <T> getRandomItem(list: List<T>): T {
    val random = Random()
    val listSize = list.size
    val randomIndex: Int = random.nextInt(listSize)
    return list[randomIndex]
  }
}
