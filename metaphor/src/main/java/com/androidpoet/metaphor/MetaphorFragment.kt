
package com.androidpoet.metaphor

import android.graphics.Color
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
 * @param fragment A context for creating resources of the [MetaphorFragment].
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
  /** duration of enter the animations. */
  public val enterDuration: Long = builder.enterDuration
  /** duration of reenter the animations. */
  public val reenterDuration: Long = builder.reenterDuration
  /** duration of exit the animations. */
  public val exitDuration: Long = builder.exitDuration
  /** duration of return the animations. */
  public val returnDuration: Long = builder.returnDuration

  /** Enter Animation of  fragment. */
  public val enterAnimation: MetaphorAnimation = builder.enterAnimation

  /** Exit Animation of  fragment. */
  public val exitAnimation: MetaphorAnimation = builder.exitAnimation

  /** Reenter Animation of  fragment. */
  public val reenterAnimation: MetaphorAnimation = builder.reenterAnimation

  /** Return Animation of  fragment. */
  public val returnAnimation: MetaphorAnimation = builder.returnAnimation

  /**   Enter AnimationOverlap of  fragment. */
  public val enterTransitionOverlap: Boolean = builder.enterTransitionOverlap

  /**   Return AnimationOverlap of  fragment. */
  public val returnTransitionOverlap: Boolean = builder.returnTransitionOverlap

  /** Motion path of on fragment animation */
  public val motion: PathMotion = builder.motion

  /** Fragment on which animation will apply */
  public val fragment: Fragment = builder.fragment

  /** start view to transform into fragment */
  public val view: View? = builder.view

  /** transitionName to transform view to another fragment */
  public val transitionName: String = builder.transitionName

  /** scrimColor while performing animation */
  public val scrimColor: Int = builder.scrimColor

  /** containerColors while performing animation */
  public val containerColors: Int = builder.containerColors

  /** Builder class for [MetaphorFragment]. */
  @MetaphorViewInlineDsl
  public class Builder(public val fragment: Fragment) {

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
    public var reenterAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic
    public var returnAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic
    public var enterTransitionOverlap: Boolean = false

    @set:JvmSynthetic
    public var returnTransitionOverlap: Boolean = false

    @set:JvmSynthetic
    public var motion: PathMotion = ArcMotion()

    @set:JvmSynthetic
    public var view: View? = null

    @set:JvmSynthetic
    public var transitionName: String = ""

    @set:JvmSynthetic
    public var scrimColor: Int = Color.TRANSPARENT

    @set:JvmSynthetic
    public var containerColors: Int = Color.TRANSPARENT

    /** sets the duration of the animation. */
    public fun setEnterDuration(value: Long): Builder = apply { this.enterDuration = value }

    /** sets the duration of the animation. */
    public fun setExitDuration(value: Long): Builder = apply { this.exitDuration = value }

    /** sets the duration of the animation. */
    public fun setReenterDuration(value: Long): Builder = apply { this.reenterDuration = value }

    /** sets the duration of the animation. */
    public fun setReturnDuration(value: Long): Builder = apply { this.returnDuration = value }

    /** sets enter the animation of the Fragment. */
    public fun setEnterAnimation(value: MetaphorAnimation): Builder =
      apply { this.enterAnimation = value }

    /** sets the exit animation of the Fragment. */
    public fun setExitAnimation(value: MetaphorAnimation): Builder =
      apply { this.exitAnimation = value }

    /** sets the return animation of the Fragment. */
    public fun setReturnAnimation(value: MetaphorAnimation): Builder =
      apply { this.returnAnimation = value }

    /** sets the reenter animation of the Fragment. */
    public fun setReenterAnimation(value: MetaphorAnimation): Builder =
      apply { this.reenterAnimation = value }

    /** sets the SetView of the Fragment. */
    public fun setView(value: View): Builder =
      apply { this.view = value }

    /** sets the Motion of the animation. */
    public fun setMotion(value: PathMotion): Builder =
      apply { this.motion = value }

    /** sets the TransitionName of the View. */
    public fun setTransitionName(value: String): Builder =
      apply { this.transitionName = value }

    /** sets the enter Overlap of the Fragment. */
    public fun setEnterOverlap(value: Boolean): Builder =
      apply { this.enterTransitionOverlap = value }

    /** sets the return Overlap of the Fragment. */
    public fun setReturnOverlap(value: Boolean): Builder =
      apply { this.returnTransitionOverlap = value }

    /** sets the ScrimColor of the Fragment. */
    public fun setScrimColor(value: Int): Builder =
      apply { this.scrimColor = value }

    /** sets the ScrimColor of the Fragment. */
    public fun setContainerColor(value: Int): Builder =
      apply { this.containerColors = value }

    public fun build(): MetaphorFragment = MetaphorFragment(this)
  }

  /** starts  animation. */
  public fun animate() {
    fragment.applyAnimation(this)
  }
}
