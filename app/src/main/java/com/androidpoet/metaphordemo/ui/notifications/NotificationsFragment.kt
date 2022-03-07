
package com.androidpoet.metaphordemo.ui.notifications

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.Metaphor
import com.androidpoet.metaphor.metaphorHideViewWithMaterialFade
import com.androidpoet.metaphor.metaphorMaterialFadeInFragment
import com.androidpoet.metaphor.metaphorMaterialFadeThroughBetweenViews
import com.androidpoet.metaphor.metaphorSharedAxisTransformationBetweenViews
import com.androidpoet.metaphor.metaphorShowViewWithMaterialFade
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
    metaphorMaterialFadeInFragment().duration = 200L
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
    val root: View = binding.root

    return root
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img)
    Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img2)
    Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img3)

    binding.SharedX.setOnClickListener {
      binding.img.visibility = View.GONE
      Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img)
      metaphorSharedAxisTransformationBetweenViews(
        binding.root,
        binding.img,
        binding.img,
        Metaphor.SharedX,
        true
      )
    }

    binding.SharedY.setOnClickListener {
      binding.img.visibility = View.GONE
      Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img)
      metaphorSharedAxisTransformationBetweenViews(
        binding.root,
        binding.img,
        binding.img,
        Metaphor.SharedY,
        true
      )
    }
    binding.SharedZ.setOnClickListener {
      binding.img.visibility = View.GONE
      Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img)
      metaphorSharedAxisTransformationBetweenViews(
        binding.root,
        binding.img,
        binding.img,
        Metaphor.SharedZ,
        true
      )
    }

    binding.materialFadeThrough.setOnClickListener {
      binding.img2.visibility = View.GONE
      Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img2)
      metaphorMaterialFadeThroughBetweenViews(binding.root, binding.img2, binding.img2)
    }

    binding.materialFade.setOnClickListener {
      Glide.with(requireContext()).load(getRandomItem(images)).into(binding.img3)
      if (binding.img3.visibility == View.INVISIBLE) {
        binding.materialFade.text = "Material Fade Through(Hide)"
        metaphorShowViewWithMaterialFade(binding.root, binding.img3)
      } else if (binding.img3.visibility == View.VISIBLE) {
        binding.materialFade.text = "Material Fade Through(Show)"
        metaphorHideViewWithMaterialFade(binding.root, binding.img3)
      }
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
