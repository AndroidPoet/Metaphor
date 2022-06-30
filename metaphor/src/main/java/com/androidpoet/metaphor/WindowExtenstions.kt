/*
 *
 *  * Copyright 2022 AndroidPoet (Ranbir Singh)
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

package com.androidpoet.metaphor

import android.os.Build
import android.view.Window
import android.widget.PopupWindow
import androidx.annotation.RequiresApi

/** applies Animation form attributes to a View instance. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
internal fun PopupWindow.applyAnimation(
    window: MetaphorWindow
) {

    val enterAnimation =
        getWindowMetaphorAnimation(window.enterAnimation)?.let {
            addAnimationProperties(
                it,
                window,
                window.enterDuration
            )
        }
    val exitAnimation =
        getWindowMetaphorAnimation(window.exitAnimation)?.let {
            addAnimationProperties(
                it,
                window,
                window.exitDuration
            )
        }


    this.enterTransition = enterAnimation
    this.exitTransition = exitAnimation

}

/** applies Properties on Animation form attributes. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
internal fun PopupWindow.addAnimationProperties(
    transition: android.transition.Transition,
    window: MetaphorWindow,
    animationDuration: Long
): android.transition.Transition {


    transition.apply {
        duration = animationDuration

    }
    return transition
}
