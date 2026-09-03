
@file:Suppress("DEPRECATION")

package com.androidpoet.metaphor

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.PopupWindow
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

/*
 * The 1.x builder API, kept for one major version so existing call sites keep compiling.
 * Every entry point below delegates to the Motion / MotionSpec API and will be removed in 3.0.
 */

private const val LEGACY_MESSAGE = "Replaced by the Motion and MotionSpec API in Metaphor 2.0"

@Deprecated("Use a Motion subtype instead. $LEGACY_MESSAGE")
public enum class MetaphorAnimation {
  None,
  ContainerTransform,
  FadeThrough,
  Fade,
  SharedAxisXForward,
  SharedAxisYForward,
  SharedAxisZForward,
  SharedAxisXBackward,
  SharedAxisYBackward,
  SharedAxisZBackward,
  ElevationScaleGrow,
  ElevationScale,
}

/** Converts a 1.x animation name to its [Motion], or `null` for [MetaphorAnimation.None]. */
@Deprecated(LEGACY_MESSAGE)
public fun MetaphorAnimation.toMotion(
  duration: Long = 300L,
  path: MotionPath = MotionPath.Arc,
  scrimColor: Int = Color.TRANSPARENT,
  containerColor: Int = Color.TRANSPARENT,
): Motion? = when (this) {
  MetaphorAnimation.None -> null
  MetaphorAnimation.ContainerTransform -> Motion.ContainerTransform(scrimColor, containerColor, path, duration = duration)
  MetaphorAnimation.FadeThrough -> Motion.FadeThrough(duration)
  MetaphorAnimation.Fade -> Motion.Fade(duration)
  MetaphorAnimation.SharedAxisXForward -> Motion.SharedAxis.x(forward = true, duration = duration)
  MetaphorAnimation.SharedAxisYForward -> Motion.SharedAxis.y(forward = true, duration = duration)
  MetaphorAnimation.SharedAxisZForward -> Motion.SharedAxis.z(forward = true, duration = duration)
  MetaphorAnimation.SharedAxisXBackward -> Motion.SharedAxis.x(forward = false, duration = duration)
  MetaphorAnimation.SharedAxisYBackward -> Motion.SharedAxis.y(forward = false, duration = duration)
  MetaphorAnimation.SharedAxisZBackward -> Motion.SharedAxis.z(forward = false, duration = duration)
  MetaphorAnimation.ElevationScaleGrow -> Motion.ElevationScale.grow(duration)
  MetaphorAnimation.ElevationScale -> Motion.ElevationScale.shrink(duration)
}

private fun Any?.toMotionPath(): MotionPath = when (this) {
  null -> MotionPath.Linear
  is androidx.transition.ArcMotion,
  is android.transition.ArcMotion,
  -> MotionPath.Arc
  else -> MotionPath.Linear
}

private fun legacySpec(
  enter: MetaphorAnimation,
  enterDuration: Long,
  exit: MetaphorAnimation,
  exitDuration: Long,
  reenter: MetaphorAnimation,
  reenterDuration: Long,
  `return`: MetaphorAnimation,
  returnDuration: Long,
  path: MotionPath,
  scrimColor: Int,
  containerColor: Int,
  enterOverlap: Boolean,
  returnOverlap: Boolean,
): MotionSpec {
  val slots = listOf(
    enter.toMotion(enterDuration, path, scrimColor, containerColor),
    exit.toMotion(exitDuration, path, scrimColor, containerColor),
    reenter.toMotion(reenterDuration, path, scrimColor, containerColor),
    `return`.toMotion(returnDuration, path, scrimColor, containerColor),
  )
  return MotionSpec(
    enter = slots[0],
    exit = slots[1],
    popEnter = slots[2],
    popExit = slots[3],
    sharedElement = slots.filterIsInstance<Motion.ContainerTransform>().firstOrNull(),
    allowEnterOverlap = enterOverlap,
    allowReturnOverlap = returnOverlap,
  )
}

@DslMarker
public annotation class MetaphorFragmentInlineDsl

@Deprecated("Use Fragment.applyMotion { }. $LEGACY_MESSAGE")
@MainThread
@JvmSynthetic
@MetaphorFragmentInlineDsl
public inline fun metaphorFragment(
  fragment: Fragment,
  crossinline block: MetaphorFragment.Builder.() -> Unit,
): MetaphorFragment = MetaphorFragment.Builder(fragment).apply(block).build()

@Deprecated("Use Fragment.applyMotion(MotionSpec). $LEGACY_MESSAGE")
public class MetaphorFragment private constructor(builder: Builder) {
  public val enterDuration: Long = builder.enterDuration
  public val reenterDuration: Long = builder.reenterDuration
  public val exitDuration: Long = builder.exitDuration
  public val returnDuration: Long = builder.returnDuration
  public val enterAnimation: MetaphorAnimation = builder.enterAnimation
  public val exitAnimation: MetaphorAnimation = builder.exitAnimation
  public val reenterAnimation: MetaphorAnimation = builder.reenterAnimation
  public val returnAnimation: MetaphorAnimation = builder.returnAnimation
  public val enterTransitionOverlap: Boolean = builder.enterTransitionOverlap
  public val returnTransitionOverlap: Boolean = builder.returnTransitionOverlap
  public val motion: androidx.transition.PathMotion = builder.motion
  public val fragment: Fragment = builder.fragment
  public val view: View? = builder.view
  public val transitionName: String = builder.transitionName
  public val scrimColor: Int = builder.scrimColor
  public val containerColors: Int = builder.containerColors

  @MetaphorFragmentInlineDsl
  public class Builder(public val fragment: Fragment) {
    @set:JvmSynthetic public var enterDuration: Long = 300

    @set:JvmSynthetic public var reenterDuration: Long = 300

    @set:JvmSynthetic public var exitDuration: Long = 300

    @set:JvmSynthetic public var returnDuration: Long = 300

    @set:JvmSynthetic public var enterAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var exitAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var reenterAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var returnAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var enterTransitionOverlap: Boolean = false

    @set:JvmSynthetic public var returnTransitionOverlap: Boolean = false

    @set:JvmSynthetic public var motion: androidx.transition.PathMotion = androidx.transition.ArcMotion()

    @set:JvmSynthetic public var view: View? = null

    @set:JvmSynthetic public var transitionName: String = ""

    @set:JvmSynthetic public var scrimColor: Int = Color.TRANSPARENT

    @set:JvmSynthetic public var containerColors: Int = Color.TRANSPARENT

    public fun setEnterDuration(value: Long): Builder = apply { enterDuration = value }
    public fun setExitDuration(value: Long): Builder = apply { exitDuration = value }
    public fun setReenterDuration(value: Long): Builder = apply { reenterDuration = value }
    public fun setReturnDuration(value: Long): Builder = apply { returnDuration = value }
    public fun setEnterAnimation(value: MetaphorAnimation): Builder = apply { enterAnimation = value }
    public fun setExitAnimation(value: MetaphorAnimation): Builder = apply { exitAnimation = value }
    public fun setReturnAnimation(value: MetaphorAnimation): Builder = apply { returnAnimation = value }
    public fun setReenterAnimation(value: MetaphorAnimation): Builder = apply { reenterAnimation = value }
    public fun setView(value: View): Builder = apply { view = value }
    public fun setMotion(value: androidx.transition.PathMotion): Builder = apply { motion = value }
    public fun setTransitionName(value: String): Builder = apply { transitionName = value }
    public fun setEnterOverlap(value: Boolean): Builder = apply { enterTransitionOverlap = value }
    public fun setReturnOverlap(value: Boolean): Builder = apply { returnTransitionOverlap = value }
    public fun setScrimColor(value: Int): Builder = apply { scrimColor = value }
    public fun setContainerColor(value: Int): Builder = apply { containerColors = value }
    public fun build(): MetaphorFragment = MetaphorFragment(this)
  }

  public fun animate() {
    val spec = legacySpec(
      enterAnimation, enterDuration, exitAnimation, exitDuration,
      reenterAnimation, reenterDuration, returnAnimation, returnDuration,
      motion.toMotionPath(), scrimColor, containerColors,
      enterTransitionOverlap, returnTransitionOverlap,
    )
    val destination = view?.let { SharedElement.Destination(it, transitionName) }
    fragment.applyMotion(spec, destination)
  }
}

@Deprecated("Use Fragment.postponeEnterUntilDrawn(). $LEGACY_MESSAGE", ReplaceWith("postponeEnterUntilDrawn()"))
@JvmSynthetic
public fun Fragment.hold() {
  postponeEnterUntilDrawn()
}

@DslMarker
internal annotation class MetaphorActivityInlineDsl

@Deprecated("Use Activity.applyMotion { }. $LEGACY_MESSAGE")
@MainThread
@JvmSynthetic
@MetaphorActivityInlineDsl
public inline fun metaphorActivity(
  activity: ComponentActivity,
  crossinline block: MetaphorActivity.Builder.() -> Unit,
): MetaphorActivity = MetaphorActivity.Builder(activity).apply(block).build()

@Deprecated("Use Activity.applyMotion(MotionSpec). $LEGACY_MESSAGE")
public class MetaphorActivity private constructor(builder: Builder) {
  public val enterDuration: Long = builder.enterDuration
  public val reenterDuration: Long = builder.reenterDuration
  public val exitDuration: Long = builder.exitDuration
  public val returnDuration: Long = builder.returnDuration
  public val enterTransitionOverlap: Boolean = builder.enterTransitionOverlap
  public val returnTransitionOverlap: Boolean = builder.returnTransitionOverlap
  public val enterAnimation: MetaphorAnimation = builder.enterAnimation
  public val exitAnimation: MetaphorAnimation = builder.exitAnimation
  public val reenterAnimation: MetaphorAnimation = builder.reenterAnimation
  public val returnAnimation: MetaphorAnimation = builder.returnAnimation
  public val motion: android.transition.PathMotion = builder.motion
  public val activity: ComponentActivity = builder.activity
  public val view: View? = builder.view
  public val transitionName: String = builder.transitionName
  public val startActivity: Boolean = builder.startActivity

  @MetaphorActivityInlineDsl
  public class Builder(public val activity: ComponentActivity) {
    @set:JvmSynthetic public var enterDuration: Long = 300

    @set:JvmSynthetic public var reenterDuration: Long = 300

    @set:JvmSynthetic public var exitDuration: Long = 300

    @set:JvmSynthetic public var returnDuration: Long = 300

    @set:JvmSynthetic public var enterAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var exitAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var reenterAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var returnAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var motion: android.transition.PathMotion = android.transition.ArcMotion()

    @set:JvmSynthetic public var view: View? = null

    @set:JvmSynthetic public var transitionName: String = ""

    @set:JvmSynthetic public var enterTransitionOverlap: Boolean = false

    @set:JvmSynthetic public var returnTransitionOverlap: Boolean = false

    @set:JvmSynthetic public var startActivity: Boolean = false

    public fun setEnterDuration(value: Long): Builder = apply { enterDuration = value }
    public fun setExitDuration(value: Long): Builder = apply { exitDuration = value }
    public fun setReenterDuration(value: Long): Builder = apply { reenterDuration = value }
    public fun setReturnDuration(value: Long): Builder = apply { returnDuration = value }
    public fun setEnterAnimation(value: MetaphorAnimation): Builder = apply { enterAnimation = value }
    public fun setExitAnimation(value: MetaphorAnimation): Builder = apply { exitAnimation = value }
    public fun setReturnAnimation(value: MetaphorAnimation): Builder = apply { returnAnimation = value }
    public fun setReenterAnimation(value: MetaphorAnimation): Builder = apply { reenterAnimation = value }
    public fun setView(value: View): Builder = apply { view = value }
    public fun setMotion(value: android.transition.PathMotion): Builder = apply { motion = value }
    public fun setTransitionName(value: String): Builder = apply { transitionName = value }
    public fun setEnterOverlap(value: Boolean): Builder = apply { enterTransitionOverlap = value }
    public fun setReturnOverlap(value: Boolean): Builder = apply { returnTransitionOverlap = value }
    public fun setStartActivity(value: Boolean): Builder = apply { startActivity = value }
    public fun build(): MetaphorActivity = MetaphorActivity(this)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  public fun animate() {
    val spec = legacySpec(
      enterAnimation, enterDuration, exitAnimation, exitDuration,
      reenterAnimation, reenterDuration, returnAnimation, returnDuration,
      motion.toMotionPath(), Color.TRANSPARENT, Color.TRANSPARENT,
      enterTransitionOverlap, returnTransitionOverlap,
    )
    val role = when {
      startActivity -> SharedElement.Origin
      view != null -> SharedElement.Destination(view, transitionName)
      else -> null
    }
    (activity as Activity).applyMotion(spec, role)
  }
}

@DslMarker
internal annotation class MetaphorWindowInlineDsl

@Deprecated("Use PopupWindow.applyMotion { }. $LEGACY_MESSAGE")
@MainThread
@JvmSynthetic
@MetaphorWindowInlineDsl
public inline fun metaphorWindow(
  popupWindow: PopupWindow,
  crossinline block: MetaphorWindow.Builder.() -> Unit,
): MetaphorWindow = MetaphorWindow.Builder(popupWindow).apply(block).build()

@Deprecated("Use PopupWindow.applyMotion(MotionSpec). $LEGACY_MESSAGE")
public class MetaphorWindow private constructor(builder: Builder) {
  public val enterDuration: Long = builder.enterDuration
  public val exitDuration: Long = builder.exitDuration
  public val enterAnimation: MetaphorAnimation = builder.enterAnimation
  public val exitAnimation: MetaphorAnimation = builder.exitAnimation
  public val window: PopupWindow = builder.popupWindow

  @MetaphorWindowInlineDsl
  public class Builder(public val popupWindow: PopupWindow) {
    @set:JvmSynthetic public var enterDuration: Long = 300

    @set:JvmSynthetic public var exitDuration: Long = 300

    @set:JvmSynthetic public var enterAnimation: MetaphorAnimation = MetaphorAnimation.None

    @set:JvmSynthetic public var exitAnimation: MetaphorAnimation = MetaphorAnimation.None

    public fun setEnterDuration(value: Long): Builder = apply { enterDuration = value }
    public fun setExitDuration(value: Long): Builder = apply { exitDuration = value }
    public fun setEnterAnimation(value: MetaphorAnimation): Builder = apply { enterAnimation = value }
    public fun setExitAnimation(value: MetaphorAnimation): Builder = apply { exitAnimation = value }
    public fun build(): MetaphorWindow = MetaphorWindow(this)
  }

  @RequiresApi(Build.VERSION_CODES.M)
  public fun animate() {
    window.applyMotion(
      MotionSpec(
        enter = enterAnimation.toMotion(enterDuration),
        exit = exitAnimation.toMotion(exitDuration),
      ),
    )
  }
}

@DslMarker
internal annotation class MetaphorViewInlineDsl

@Deprecated("Use View.morphInto, View.animateVisibility or ViewGroup.animateChanges. $LEGACY_MESSAGE")
@MainThread
@JvmSynthetic
@MetaphorViewInlineDsl
public inline fun metaphorView(
  view: View,
  crossinline block: MetaphorView.Builder.() -> Unit,
): MetaphorView = MetaphorView.Builder(view).apply(block).build()

@Deprecated("Use View.morphInto, View.animateVisibility or ViewGroup.animateChanges. $LEGACY_MESSAGE")
public class MetaphorView private constructor(builder: Builder) {
  public val endView: View? = builder.endView
  public val duration: Long = builder.duration
  public val animation: MetaphorAnimation = builder.animation
  public val motion: androidx.transition.PathMotion = builder.motion
  private val startView: View = builder.startView

  @MetaphorViewInlineDsl
  public class Builder(public val startView: View) {
    @set:JvmSynthetic public var duration: Long = 300

    @set:JvmSynthetic public var animation: MetaphorAnimation = MetaphorAnimation.FadeThrough

    @set:JvmSynthetic public var endView: View? = null

    @set:JvmSynthetic public var motion: androidx.transition.PathMotion = androidx.transition.ArcMotion()

    public fun setDuration(value: Long): Builder = apply { duration = value }
    public fun setMetaphorAnimation(value: MetaphorAnimation): Builder = apply { animation = value }
    public fun setEndView(value: View): Builder = apply { endView = value }
    public fun setMotion(value: androidx.transition.PathMotion): Builder = apply { motion = value }
    public fun build(): MetaphorView = MetaphorView(this)
  }

  @MainThread
  public fun animate() {
    val motion = animation.toMotion(duration, this.motion.toMotionPath()) ?: return
    val end = endView
    when {
      motion is Motion.ContainerTransform && end != null && end !== startView -> startView.morphInto(end, motion)
      end == null || end === startView -> startView.toggleVisibility(motion)
      else -> (startView.parent as? android.view.ViewGroup)?.animateChanges(motion) {
        startView.isVisible = false
        end.isVisible = true
      }
    }
  }
}
