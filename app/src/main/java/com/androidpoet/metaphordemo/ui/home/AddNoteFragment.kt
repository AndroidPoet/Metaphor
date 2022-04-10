
package com.androidpoet.metaphordemo.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.androidpoet.metaphordemo.R
import com.androidpoet.metaphordemo.databinding.FragmentAddNoteBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddNoteFragment : Fragment() {

  private lateinit var viewBinding: FragmentAddNoteBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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
    viewBinding = FragmentAddNoteBinding.inflate(inflater, container, false).apply {
      lifecycleOwner = viewLifecycleOwner
    }
    return viewBinding.root
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }
}
