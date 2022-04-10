
package com.androidpoet.metaphor

import android.view.View
import androidx.annotation.MainThread
import androidx.transition.ArcMotion
import androidx.transition.PathMotion

@DslMarker
internal annotation class MetaphorViewInlineDsl

/**
 * Creates an instance of the [MetaphorView] by scope of the [MetaphorView.Builder] using kotlin dsl.
 *
 * @param context A context for creating resources of the [MetaphorView].
 * @param block A dsl scope lambda from the [MetaphorView.Builder].
 * */
@MainThread
@JvmSynthetic
@MetaphorViewInlineDsl
public inline fun metaphorView(
  view: View,
  crossinline block: MetaphorView.Builder.() -> Unit
): MetaphorView =
  MetaphorView.Builder(view).apply(block).build()

/**
 * MetaphorFragment implements material motion animations.
 *
 * @see [MetaphorView](https://github.com/AndroidPoet/Metaphor)
 *
 * @param builder A [MetaphorView.Builder] for creating an instance of the [MetaphorView].
 */
public class MetaphorView private constructor(
  builder: Builder
) {

  /** end view to transform into View */
  public val endView: View? = builder.endView

  /** duration of the animations. */
  public val duration: Long = builder.duration

  /** Animation of  View. */
  public val animation: MetaphorAnimation = builder.animation

  /** Motion path of on View animation */
  public val motion: PathMotion = builder.motion

  /** start view to transform into View */
  public val starView: View = builder.startView

  /** Builder class for [MetaphorView]. */
  @MetaphorViewInlineDsl
  public class Builder(public val startView: View) {

    @set:JvmSynthetic
    public var duration: Long = 300

    @set:JvmSynthetic
    public var animation: MetaphorAnimation = MetaphorAnimation.FadeThrough

    @set:JvmSynthetic
    public var endView: View? = null

    @set:JvmSynthetic
    public var motion: PathMotion = ArcMotion()

    /** sets the duration of the View. */
    public fun setDuration(value: Long): Builder = apply { this.duration = value }

    /** sets the [Animation] of the View. */
    public fun setMetaphorAnimation(value: MetaphorAnimation): Builder =
      apply { this.animation = value }

    /** sets the [EndView] of the View. */
    public fun setEndView(value: View): Builder =
      apply { this.endView = value }

    /** sets the [Motion] of the View. */
    public fun setMotion(value: PathMotion): Builder =
      apply { this.motion = value }

    public fun build(): MetaphorView = MetaphorView(this)
  }

  /** starts  animation. */
  @MainThread
  public fun animate() {
    starView.applyMetaphor(this)
  }
}