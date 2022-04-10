
package com.androidpoet.metaphor

import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.PathMotion

@DslMarker
internal annotation class MetaphorFragmentInlineDsl

/**
 * Creates an instance of the [MetaphorFragment] by scope of the [MetaphorFragment.Builder] using kotlin dsl.
 *
 * @param Context A context for creating resources of the [MetaphorFragment].
 * @param block A dsl scope lambda from the [MetaphorFragment.Builder].
 * */
@MainThread
@JvmSynthetic
@MetaphorFragmentInlineDsl
public inline fun metaphorFragment(
  fragment: Fragment,
  crossinline block: MetaphorFragment.Builder.() -> Unit
): MetaphorFragment =
  MetaphorFragment.Builder(fragment).apply(block).build()

/**
 * MetaphorFragment implements material motion animations.
 *
 * @see [MetaphorFragment](https://github.com/AndroidPoet/Metaphor)
 *
 * @param builder A [MetaphorFragment.Builder] for creating an instance of the [MetaphorFragment].
 */
public class MetaphorFragment private constructor(
  builder: Builder
) {
  /** duration of the animations. */
  public val duration: Long = builder.duration

  /** Animation of  fragment. */
  public val animation: MetaphorAnimation = builder.animation

  /** Motion path of on fragment animation */
  public val motion: PathMotion = builder.motion

  /** Fragment on which animation will apply */
  public val fragment: Fragment = builder.fragment

  /** start view to transform into fragment */
  public val view: View? = builder.view

  /** transitionName to transform view to another fragment */
  public val transitionName: String = builder.transitionName

  /** Builder class for [MetaphorFragment]. */
  @MetaphorViewInlineDsl
  public class Builder(public val fragment: Fragment) {
    @set:JvmSynthetic
    public var duration: Long = 0

    @set:JvmSynthetic
    public var animation: MetaphorAnimation = MetaphorAnimation.FadeThrough

    @set:JvmSynthetic
    public var motion: PathMotion = ArcMotion()

    @set:JvmSynthetic
    public var view: View? = null

    @set:JvmSynthetic
    public var transitionName: String = ""

    /** sets the duration of the Animation. */
    public fun setDuration(value: Long): Builder = apply { this.duration = value }

    /** sets the [Animation] of the Fragment. */
    public fun setMetaphorAnimation(value: MetaphorAnimation): Builder =
      apply { this.animation = value }

    /** sets the [StartView] of the Fragment. */
    public fun setView(value: View): Builder =
      apply { this.view = value }

    /** sets the [Motion] of the View. */
    public fun setMotion(value: PathMotion): Builder =
      apply { this.motion = value }

    /** sets the [TransitionName] of the View. */
    public fun setTransitionName(value: String): Builder =
      apply { this.transitionName = value }

    public fun build(): MetaphorFragment = MetaphorFragment(this)
  }

  /** starts  animation. */
  public fun animate() {
    fragment.applyMetaphor(this)
  }
}
