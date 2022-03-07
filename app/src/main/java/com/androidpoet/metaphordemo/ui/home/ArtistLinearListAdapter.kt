
package com.androidpoet.metaphordemo.ui.home

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.androidpoet.metaphordemo.databinding.ArtistLinearListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

/**
 * Created by Phong Huynh on 11/15/2020
 */
class ArtistLinearListAdapter(
  private val context: Context,
  private val requestManager: RequestManager
) : ListAdapter<SampleResponse, ArtistLinearViewHolder>(object : DiffUtil
  .ItemCallback<SampleResponse>() {
    override fun areItemsTheSame(oldItem: SampleResponse, newItem: SampleResponse): Boolean {
      return oldItem.blur == newItem.blur
    }

    override fun areContentsTheSame(oldItem: SampleResponse, newItem: SampleResponse): Boolean {
      return oldItem == newItem
    }
  }) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistLinearViewHolder {
    return ArtistLinearViewHolder(
      ArtistLinearListBinding.inflate(
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
  override fun onBindViewHolder(holder: ArtistLinearViewHolder, position: Int) {
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
