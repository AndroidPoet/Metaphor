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
import com.androidpoet.metaphor.metaphorDestinationFragmentMaterialContainerTransform

import com.androidpoet.metaphor.metaphorMaterialContainerTransformViewIntoAnotherView
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.FragmentArtistDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

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
    metaphorDestinationFragmentMaterialContainerTransform(view, args.data.pos.toString())


    viewBinding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24); // your drawable
    viewBinding.toolBar.setNavigationOnClickListener(
      View.OnClickListener {
        // back button pressed
        findNavController().popBackStack()
      }
    )

    viewBinding.fabDetail.setOnClickListener {
      metaphorMaterialContainerTransformViewIntoAnotherView(
        viewBinding.root,
        viewBinding.fabDetail,
        viewBinding.controlsPanel
      )
    }

    viewBinding.controlsPanel.setOnClickListener {
      // one line of code to transform fab button to CardView
      metaphorMaterialContainerTransformViewIntoAnotherView(
        viewBinding.root,
        viewBinding.controlsPanel,
        viewBinding.fabDetail
      )
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
//                    Timber.e("resource $resource")
//                    (resource as? BitmapDrawable)?.bitmap?.let {
//                        val palette = Palette.from(it).generate()
//                        val paletteTarget = androidx.palette.graphics.Target.DARK_MUTED
//                        val selectedSwatch = palette[paletteTarget]
//                        selectedSwatch?.rgb?.let { color ->
//                            viewBinding.collapsingToolbar.setContentScrimColor(color)
//                        }
//                    }
          return false
        }
      })
      .into(viewBinding.artistImageView)
  }

  override fun onDestroyView() {

    super.onDestroyView()
  }
}
