
package com.androidpoet.metaphor

/**
 * The full set of motions for one screen (a Fragment, an Activity or a PopupWindow).
 *
 * Slots follow the Navigation naming: [enter] and [exit] play when this screen is pushed or
 * pushes another, [popEnter] and [popExit] play when the back stack is popped. A `null` slot
 * leaves that transition untouched.
 *
 * A [MotionSpec] holds no reference to its target, so it can be declared once and reused:
 *
 * ```
 * val listToDetail = motionSpec {
 *   exit = Motion.ElevationScale.shrink()
 *   popEnter = Motion.ElevationScale.grow()
 * }
 * ```
 */
public data class MotionSpec(
  val enter: Motion? = null,
  val exit: Motion? = null,
  val popEnter: Motion? = null,
  val popExit: Motion? = null,
  val sharedElement: Motion.ContainerTransform? = null,
  val allowEnterOverlap: Boolean = false,
  val allowReturnOverlap: Boolean = false,
) {
  public companion object {
    /** A spec with every slot empty. */
    @JvmField public val None: MotionSpec = MotionSpec()
  }
}

@DslMarker
public annotation class MotionDsl

/** Mutable builder behind [motionSpec]. */
@MotionDsl
public class MotionSpecBuilder {
  public var enter: Motion? = null
  public var exit: Motion? = null
  public var popEnter: Motion? = null
  public var popExit: Motion? = null
  public var sharedElement: Motion.ContainerTransform? = null
  public var allowEnterOverlap: Boolean = false
  public var allowReturnOverlap: Boolean = false

  public fun build(): MotionSpec = MotionSpec(
    enter = enter,
    exit = exit,
    popEnter = popEnter,
    popExit = popExit,
    sharedElement = sharedElement,
    allowEnterOverlap = allowEnterOverlap,
    allowReturnOverlap = allowReturnOverlap,
  )
}

/** Builds a [MotionSpec] with a small DSL. */
public inline fun motionSpec(block: MotionSpecBuilder.() -> Unit): MotionSpec =
  MotionSpecBuilder().apply(block).build()

/**
 * Where a screen stands in a shared element transition.
 *
 * The screen that launches the transition passes [Origin]. The screen that receives it passes
 * [Destination] with the view that continues the shared element and its transition name.
 */
public sealed interface SharedElement {
  public object Origin : SharedElement

  public class Destination(
    public val view: android.view.View,
    public val transitionName: String,
  ) : SharedElement
}
