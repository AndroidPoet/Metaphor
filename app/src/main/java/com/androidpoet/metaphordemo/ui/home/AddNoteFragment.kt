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

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.metaphorMaterialContainerTransformViewIntoFragment
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

    metaphorMaterialContainerTransformViewIntoFragment(requireActivity().findViewById(R.id.fab),viewBinding.root)
  }
}

