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

import android.view.View
import android.view.Window
import android.widget.PopupWindow
import androidx.annotation.MainThread

@DslMarker
internal annotation class MetaphorWindowInlineDsl

/**
 * Creates an instance of the [MetaphorWindow] by scope of the [MetaphorWindow.Builder] using kotlin dsl.
 *
 * @param activity A context for creating resources of the [MetaphorWindow].
 * @param block A dsl scope lambda from the [MetaphorWindow.Builder].
 * */
@MainThread
@JvmSynthetic
@MetaphorWindowInlineDsl
public inline fun metaphorWindow(
    popupWindow: PopupWindow,
    crossinline block: MetaphorWindow.Builder.() -> Unit
): MetaphorWindow =
    MetaphorWindow.Builder(popupWindow).apply(block).build()

/**
 * MetaphorActivity implements material motion animations.
 *
 * @see [MetaphorWindow](https://github.com/AndroidPoet/Metaphor)
 *
 * @param builder A [MetaphorWindow.Builder] for creating an instance of the [MetaphorWindow].
 */
public class MetaphorWindow private constructor(
    builder: Builder
) {
    /** duration of enter the animations. */
    public val enterDuration: Long = builder.enterDuration

    /** duration of exit the animations. */
    public val exitDuration: Long = builder.exitDuration



    /**   Enter AnimationOverlap of  Activity. */
    public val enterTransitionOverlap: Boolean = builder.enterTransitionOverlap

    /**   Return AnimationOverlap of  Activity. */
    public val returnTransitionOverlap: Boolean = builder.returnTransitionOverlap

    /** Enter Animation of  fragment. */
    public var enterAnimation: MetaphorAnimation = builder.enterAnimation

    /** Exit Animation of  fragment. */
    public var exitAnimation: MetaphorAnimation = builder.exitAnimation


    /** Motion path of on fragment animation */
    public val motion: android.transition.PathMotion = builder.motion

    /** Fragment on which animation will apply */
    public val window: PopupWindow = builder.popupWindow


    /** Builder class for [MetaphorFragment]. */
    @MetaphorViewInlineDsl
    public class Builder(public val popupWindow: PopupWindow ){

        @set:JvmSynthetic
        public var enterDuration: Long = 300

        @set:JvmSynthetic
        public var reenterDuration: Long = 300

        @set:JvmSynthetic
        public var exitDuration: Long = 300

        @set:JvmSynthetic
        public var returnDuration: Long = 300

        @set:JvmSynthetic
        public var enterAnimation: MetaphorAnimation = MetaphorAnimation.None

        @set:JvmSynthetic
        public var exitAnimation: MetaphorAnimation = MetaphorAnimation.None

        @set:JvmSynthetic
        public var motion: android.transition.PathMotion = android.transition.ArcMotion()

        @set:JvmSynthetic
        public var view: View? = null

        @set:JvmSynthetic
        public var transitionName: String = ""

        @set:JvmSynthetic
        public var enterTransitionOverlap: Boolean = false

        @set:JvmSynthetic
        public var returnTransitionOverlap: Boolean = false


        /** sets the duration of the Animation. */
        public fun setEnterDuration(value: Long): Builder = apply { this.enterDuration = value }

        /** sets the duration of the Animation. */
        public fun setExitDuration(value: Long): Builder = apply { this.exitDuration = value }



        /** sets enter the animation  of the Activity. */
        public fun setEnterAnimation(value: MetaphorAnimation): Builder =
            apply { this.enterAnimation = value }

        /** sets the exit animation of the Activity. */
        public fun setExitAnimation(value: MetaphorAnimation): Builder =
            apply { this.exitAnimation = value }

        /** sets the view of the Activity. */
        public fun setView(value: View): Builder = apply { this.view = value }

        /** sets the motion of the View. */
        public fun setMotion(value: android.transition.PathMotion): Builder =
            apply { this.motion = value }

        /** sets the transition of the View. */
        public fun setTransitionName(value: String): Builder = apply { this.transitionName = value }

        /** sets the enter overlap of the Activity. */
        public fun setEnterOverlap(value: Boolean): Builder =
            apply { this.enterTransitionOverlap = value }

        /** sets the return overlap of the Activity. */
        public fun setReturnOverlap(value: Boolean): Builder =
            apply { this.returnTransitionOverlap = value }


        public fun build(): MetaphorWindow = MetaphorWindow(this)
    }

    /** starts  animation. */
    public fun animate() {
        window.applyAnimation(this)
    }
}