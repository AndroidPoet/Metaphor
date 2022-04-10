
package com.androidpoet.metaphordemo.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class SampleResponse(var pos: Int, val blur: String, val img: String) : Parcelable
