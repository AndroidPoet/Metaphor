
package com.androidpoet.metaphordemo.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidpoet.metaphor.metaphorStartFragmentWithoutAnimation
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.FragmentListBinding
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArtistListFragment : Fragment() {

  private lateinit var artistGridListAdapter: ArtistGridListAdapter
  private lateinit var artistLinearListAdapter: ArtistLinearListAdapter
  private lateinit var viewBinding: FragmentListBinding

  private var isGrid: Boolean = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // metaphorMaterialSharedAxisInFragment(Metaphor.SharedX, true)
    artistGridListAdapter = ArtistGridListAdapter(requireContext(), Glide.with(requireContext()))
    artistLinearListAdapter =
      ArtistLinearListAdapter(requireContext(), Glide.with(requireContext()))
    artistGridListAdapter.callback = object : ArtistGridListAdapter.Callback {
      override fun onClick(view: View, item: SampleResponse, imageUrl: String) {
        /**this method is used for MaterialContainerTransform it add MaterialElevationScale
         * to components] */

        val extras = FragmentNavigatorExtras(view to item.pos.toString())
        val action = ArtistListFragmentDirections.navToCharacterDetailFragment(item)
        findNavController().navigate(action, extras)
      }
    }

    artistLinearListAdapter.callback = object : ArtistLinearListAdapter.Callback {
      override fun onClick(view: View, item: SampleResponse, imageUrl: String) {
        /**this method is used for MaterialContainerTransform it add MaterialElevationScale
         * to components] */

        val extras = FragmentNavigatorExtras(view to item.pos.toString())
        val action = ArtistListFragmentDirections.navToCharacterDetailFragment(item)
        findNavController().navigate(action, extras)
      }
    }
  }

  private fun sampleResponse(): List<SampleResponse> {
    val response = resources.openRawResource(R.raw.grid).bufferedReader()
      .use { it.readText() }
    return Gson().fromJson(response, object : TypeToken<List<SampleResponse>>() {}.type)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    viewBinding = FragmentListBinding.inflate(inflater, container, false).apply {
      lifecycleOwner = viewLifecycleOwner
    }
    return viewBinding.root
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    /**this method is used for MaterialContainerTransform it add some delay to load animation basically it will wait for recyclerview to be drawn   */
    metaphorStartFragmentWithoutAnimation(viewBinding.rcv)

    loadRecyclerView(isGrid)
    viewBinding.reorder.setOnClickListener {

      if (isGrid) {
        isGrid = false
        loadRecyclerView(isGrid)
      } else {
        isGrid = true

        loadRecyclerView(isGrid)
      }
    }
  }

  fun loadRecyclerView(isGrid: Boolean) {

    if (isGrid) {
      viewBinding.rcv.apply {

        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = artistGridListAdapter.apply {
          viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            submitList(sampleResponse())
          }
        }
      }
    } else {
      viewBinding.rcv.apply {

        layoutManager = LinearLayoutManager(requireContext())
        adapter = artistLinearListAdapter.apply {
          viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            submitList(sampleResponse())
          }
        }
      }
    }
  }
}
