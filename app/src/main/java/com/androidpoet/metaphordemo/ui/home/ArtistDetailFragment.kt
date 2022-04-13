
package com.androidpoet.metaphordemo.ui.home

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidpoet.metaphor.MetaphorAnimation
import com.androidpoet.metaphor.MetaphorFragment
import com.androidpoet.metaphor.MetaphorView
import com.androidpoet.metaphor.metaphorView
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.FragmentArtistDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialArcMotion

class ArtistDetailFragment : Fragment() {

  val args: ArtistDetailFragmentArgs by navArgs()
  private lateinit var viewBinding: FragmentArtistDetailBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    viewBinding = FragmentArtistDetailBinding.inflate(inflater, container, false).apply {
      lifecycleOwner = viewLifecycleOwner
    }
    return viewBinding.root
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    /** This is the method to perform MaterialContainerTransform which we need to call inside onViewCreated
     * it is important to call this method inside onViewCreated */
    // metaphorDestinationFragmentMaterialContainerTransform(view, args.data.pos.toString())

    val metaphor = MetaphorFragment.Builder(this)
      .setEnterDuration(1000)
      .setView(view)
      .setTransitionName(args.data.pos.toString())
      .setExitAnimation(MetaphorAnimation.ContainerTransform)
      .setMotion(MaterialArcMotion())
      .build()
    metaphor.animate()

    viewBinding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24); // your drawable
    viewBinding.toolBar.setNavigationOnClickListener(
      View.OnClickListener {
        // back button pressed
        findNavController().popBackStack()
      }
    )

    viewBinding.fabDetail.setOnClickListener {

      val meta = metaphorView(viewBinding.fabDetail) {
        setDuration(1000)
        setEndView(viewBinding.controls)
        setMetaphorAnimation(MetaphorAnimation.ContainerTransform)
        setMotion(MaterialArcMotion())
        build()
      }

      meta.animate()
    }

    viewBinding.controls.setOnClickListener {
      // it is reference for the currant view
      // params[endView]you need to pass end view for the transformation
      val meta = MetaphorView.Builder(it)
        .setDuration(1000)
        .setEndView(viewBinding.fabDetail)
        .setMetaphorAnimation(MetaphorAnimation.ContainerTransform)
        .setMotion(MaterialArcMotion())
        .build()
      meta.animate()
    }

    // load image with palette
    Glide.with(requireContext())
      .load(args.data.img)
      .thumbnail(
        Glide.with(requireContext())
          .load(args.data.img)
          .priority(Priority.IMMEDIATE)

      )
      .addListener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
          e: GlideException?,
          model: Any?,
          target: Target<Drawable>?,
          isFirstResource: Boolean
        ): Boolean {
          return false
        }

        override fun onResourceReady(
          resource: Drawable?,
          model: Any?,
          target: Target<Drawable>?,
          dataSource: DataSource?,
          isFirstResource: Boolean
        ): Boolean {

          return false
        }
      })
      .into(viewBinding.artistImageView)
  }

  override fun onDestroyView() {

    super.onDestroyView()
  }
}
