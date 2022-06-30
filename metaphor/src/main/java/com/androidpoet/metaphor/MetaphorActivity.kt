
package com.androidpoet.metaphor

import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity

@DslMarker
internal annotation class MetaphorActivityInlineDsl

/**
 * Creates an instance of the [MetaphorActivity] by scope of the [MetaphorActivity.Builder] using kotlin dsl.
 *
 * @param activity A context for creating resources of the [MetaphorActivity].
 * @param block A dsl scope lambda from the [MetaphorActivity.Builder].
 * */
@MainThread
@JvmSynthetic
@MetaphorActivityInlineDsl
public inline fun metaphorActivity(
  activity: ComponentActivity,
  crossinline block: MetaphorActivity.Builder.() -> Unit
): MetaphorActivity =
  MetaphorActivity.Builder(activity).apply(block).build()

/**
 * MetaphorActivity implements material motion animations.
 *
 * @see [MetaphorActivity](https://github.com/AndroidPoet/Metaphor)
 *
 * @param builder A [MetaphorActivity.Builder] for creating an instance of the [MetaphorActivity].
 */
public class MetaphorActivity private constructor(
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

  /**   Enter AnimationOverlap of  Activity. */
  public val enterTransitionOverlap: Boolean = builder.enterTransitionOverlap

  /**   Return AnimationOverlap of  Activity. */
  public val returnTransitionOverlap: Boolean = builder.returnTransitionOverlap

  /** Enter Animation of  fragment. */
  public val enterAnimation: MetaphorAnimation = builder.enterAnimation

  /** Exit Animation of  fragment. */
  public val exitAnimation: MetaphorAnimation = builder.exitAnimation

  /** Reenter Animation of  fragment. */
  public val reenterAnimation: MetaphorAnimation = builder.reenterAnimation

  /** Return Animation of  fragment. */
  public val returnAnimation: MetaphorAnimation = builder.returnAnimation

  /** Motion path of on fragment animation */
  public val motion: android.transition.PathMotion = builder.motion

  /** Fragment on which animation will apply */
  public val activity: ComponentActivity = builder.activity

  /** start view to transform into fragment */
  public val view: View? = builder.view

  /** transitionName to transform view to another fragment */
  public val transitionName: String = builder.transitionName

  /** Fragment on which animation will apply */
  public val startActivity: Boolean = builder.startActivity

  /** Builder class for [MetaphorFragment]. */
  @MetaphorViewInlineDsl
  public class Builder(public val activity: ComponentActivity) {

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
    public var motion: android.transition.PathMotion = android.transition.ArcMotion()

    @set:JvmSynthetic
    public var view: View? = null

    @set:JvmSynthetic
    public var transitionName: String = ""

    @set:JvmSynthetic
    public var enterTransitionOverlap: Boolean = false

    @set:JvmSynthetic
    public var returnTransitionOverlap: Boolean = false

    @set:JvmSynthetic
    public var startActivity: Boolean = false

    /** sets the duration of the Animation. */
    public fun setEnterDuration(value: Long): Builder = apply { this.enterDuration = value }

    /** sets the duration of the Animation. */
    public fun setExitDuration(value: Long): Builder = apply { this.exitDuration = value }

    /** sets the duration of the Animation. */
    public fun setReenterDuration(value: Long): Builder = apply { this.reenterDuration = value }

    /** sets the duration of the Animation. */
    public fun setReturnDuration(value: Long): Builder = apply { this.returnDuration = value }

    /** sets enter the animation  of the Activity. */
    public fun setEnterAnimation(value: MetaphorAnimation): Builder =
      apply { this.enterAnimation = value }

    /** sets the exit animation of the Activity. */
    public fun setExitAnimation(value: MetaphorAnimation): Builder =
      apply { this.exitAnimation = value }

    /** sets the return animation of the Activity. */
    public fun setReturnAnimation(value: MetaphorAnimation): Builder =
      apply { this.returnAnimation = value }

    /** sets the reenter animation of the Activity. */
    public fun setReenterAnimation(value: MetaphorAnimation): Builder =
      apply { this.reenterAnimation = value }

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

    /** sets start Activity of MetaphorActivity. */
    public fun setStartActivity(value: Boolean): Builder =
      apply { this.startActivity = value }

    public fun build(): MetaphorActivity = MetaphorActivity(this)
  }

  /** starts  animation. */
  public fun animate() {
    activity.applyAnimation(this)
  }

  public abstract class Factory {

    /**
     * Creates a new instance of [MetaphorActivity].
     *
     * @return A new created instance of the [MetaphorActivity].
     */
    public abstract fun create(fragment: AppCompatActivity): MetaphorActivity
  }
}
