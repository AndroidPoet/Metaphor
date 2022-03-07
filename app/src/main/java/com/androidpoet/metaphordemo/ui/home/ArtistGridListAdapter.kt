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

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.androidpoet.metaphordemo.databinding.ItemArtistBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class ArtistGridListAdapter(
  private val context: Context,
  private val requestManager: RequestManager
) : ListAdapter<SampleResponse, ArtistViewHolder>(object : DiffUtil
  .ItemCallback<SampleResponse>() {
    override fun areItemsTheSame(oldItem: SampleResponse, newItem: SampleResponse): Boolean {
      return oldItem.blur == newItem.blur
    }

    override fun areContentsTheSame(oldItem: SampleResponse, newItem: SampleResponse): Boolean {
      return oldItem == newItem
    }
  }) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
    return ArtistViewHolder(
      ItemArtistBinding.inflate(
        LayoutInflater.from(parent.context),
        parent, false
      )
    ).apply {
      binding.root.setOnClickListener {
        if (bindingAdapterPosition >= 0) {
          callback?.onClick(
            binding.imgv,
            getItem(bindingAdapterPosition),
            getItem(bindingAdapterPosition).img
          )
        }
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
    Glide.with(holder.itemView.context).load(getItem(position).img).into(holder.binding.imgv)
    holder.binding.imgv.transitionName = getItem(position).img
    holder.binding.executePendingBindings()
  }

  fun setData(list: List<SampleResponse>) {
    submitList(list)
  }

  var callback: Callback? = null

  interface Callback {
    fun onClick(view: View, item: SampleResponse, imageUrl: String)
  }
}
