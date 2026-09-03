
package com.androidpoet.metaphordemo.ui.notifications

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.Motion
import com.androidpoet.metaphor.applyMotion
import com.androidpoet.metaphor.toggleVisibility
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
    applyMotion {
      enter = Motion.ElevationScale.shrink()
      exit = Motion.ElevationScale.grow()
    }
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
      binding.img.toggleVisibility(Motion.SharedAxis.x(forward = true, duration = 1000))
    }

    binding.SharedY.setOnClickListener {
      binding.img.toggleVisibility(Motion.SharedAxis.y(forward = true, duration = 1000))
    }

    binding.SharedZ.setOnClickListener {
      binding.img.toggleVisibility(Motion.SharedAxis.z(forward = true, duration = 1000))
    }

    binding.materialFadeThrough.setOnClickListener {
      binding.img2.toggleVisibility(Motion.FadeThrough(duration = 1000))
    }

    binding.materialFade.setOnClickListener {
      Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img3)
      binding.img3.toggleVisibility(Motion.Fade(duration = 1000))
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
