package com.androidpoet.metaphordemo.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidpoet.metaphor.animateView
import com.androidpoet.metaphor.metaphorMaterialFadeInFragment
import com.androidpoet.metaphor.metaphorMaterialFadeThroughBetweenViews
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.FragmentDashboardBinding
import com.androidpoet.metaphordemo.ui.home.ArtistGridListAdapter
import com.androidpoet.metaphordemo.ui.home.ArtistLinearListAdapter
import com.androidpoet.metaphordemo.ui.home.SampleResponse
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DashboardFragment : Fragment() {

  private var _binding: FragmentDashboardBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val viewBinding get() = _binding!!
  private lateinit var artistGridListAdapter: ArtistGridListAdapter
  private lateinit var artistLinearListAdapter: ArtistLinearListAdapter

  private var isGrid: Boolean = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    metaphorMaterialFadeInFragment().duration = 200L
    artistGridListAdapter = ArtistGridListAdapter(requireContext(), Glide.with(requireContext()))
    artistLinearListAdapter =
      ArtistLinearListAdapter(requireContext(), Glide.with(requireContext()))
    artistGridListAdapter.callback = object : ArtistGridListAdapter.Callback {
      override fun onClick(view: View, item: SampleResponse, imageUrl: String) {
        /**this method is used for MaterialContainerTransform it add MaterialElevationScale
         * to components] */

        val extras = FragmentNavigatorExtras(view to item.pos.toString())
        val action = DashboardFragmentDirections.navToCharacterDetailFragment(item)
        findNavController().navigate(action, extras)
      }
    }

    artistLinearListAdapter.callback = object : ArtistLinearListAdapter.Callback {
      override fun onClick(view: View, item: SampleResponse, imageUrl: String) {
        /**this method is used for MaterialContainerTransform it add MaterialElevationScale
         * to components] */

        val extras = FragmentNavigatorExtras(view to item.pos.toString())
        val action = DashboardFragmentDirections.navToCharacterDetailFragment(item)
        findNavController().navigate(action, extras)
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = viewBinding.root


    val snackbar: Snackbar = Snackbar.make(viewBinding.root, "message", Snackbar.LENGTH_LONG)
    val snackbarLayout = snackbar.view
    val lp = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.WRAP_CONTENT,
      LinearLayout.LayoutParams.WRAP_CONTENT
    )
    lp.setMargins(50, 0, 0, 0)
    snackbarLayout.layoutParams = lp
   
    snackbar.show()







    viewBinding.fab.setOnClickListener {
      val action = DashboardFragmentDirections.actionNavigationHomeToAddnoteFragment()
      findNavController().navigate(action)
    }

    return root
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    loadRecyclerView(isGrid)
    viewBinding.reorder.setOnClickListener {

      if (isGrid) {
        isGrid = false
        metaphorMaterialFadeThroughBetweenViews(
          viewBinding.root,
          viewBinding.rcv,
          viewBinding.rcv
        )
        loadRecyclerView(isGrid)
      } else {
        isGrid = true
        metaphorMaterialFadeThroughBetweenViews(
          viewBinding.root,
          viewBinding.rcv,
          viewBinding.rcv
        )
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

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun sampleResponse(): List<SampleResponse> {
    val response = resources.openRawResource(R.raw.list).bufferedReader()
      .use { it.readText() }
    return Gson().fromJson(response, object : TypeToken<List<SampleResponse>>() {}.type)
  }
}
