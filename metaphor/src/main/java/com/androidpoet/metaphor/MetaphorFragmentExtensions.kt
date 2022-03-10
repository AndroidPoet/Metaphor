package com.androidpoet.metaphor

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis


// ////Fragments//////

/**MaterialSharedAxis extension function witch returns MaterialSharedAxis Object
parma axis which will decide the the axis from the animation
foreword is used the decide the direction of animation*/

public fun Fragment.metaphorMaterialSharedAxisInFragment(
    axis: Int,
    forword: Boolean
): MaterialSharedAxis {
    var materialSharedAxis = MaterialSharedAxis(axis, forword)
    enterTransition = materialSharedAxis
    exitTransition = materialSharedAxis
    if (forword) {
        materialSharedAxis = MaterialSharedAxis(axis, true)
    } else {
        materialSharedAxis = MaterialSharedAxis(axis, false)
    }
    reenterTransition = materialSharedAxis
    returnTransition = materialSharedAxis
    return materialSharedAxis
}

/** MaterialFade will create fade effects between two fragments*/

public fun Fragment.metaphorMaterialFadeInFragment(): MaterialFade {
    val materialFade = MaterialFade()
    materialFade.duration = 500L
    enterTransition = materialFade
    exitTransition = materialFade
    reenterTransition = materialFade
    returnTransition = materialFade
    return materialFade
}

/** MaterialFadeThrough will create fade effects between two fragments*/

public fun Fragment.metaphorMaterialFadeThroughInFragment(): MaterialFadeThrough {
    val materialFadeThrough = MaterialFadeThrough()
    materialFadeThrough.duration = 550L
    enterTransition = materialFadeThrough
    exitTransition = materialFadeThrough
    reenterTransition = materialFadeThrough
    returnTransition = materialFadeThrough
    return materialFadeThrough
}

/** We need three function to perform a MaterialContainerTransform between two fragments*/

/** 1st method for creating MaterialContainerTransform inside detail fragment and will be called inside
 * OnViewCreated*/
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Fragment.metaphorDestinationFragmentMaterialContainerTransform(
    view: View,
    transitionName: String
): MaterialContainerTransform {
    val materialContainerTransform = MaterialContainerTransform()
    view.transitionName = transitionName
    sharedElementEnterTransition = materialContainerTransform.apply {
        duration = 300L
        setAllContainerColors(Color.TRANSPARENT)
        scrimColor = Color.TRANSPARENT
        setPathMotion(MaterialArcMotion())
    }
    return materialContainerTransform
}

/** MaterialElevationScale will bes used as Hold to manage change int he component size it be merge size changes smoothly*/

public fun Fragment.metaphorStartFragmentMaterialContainerTransform(view: View) {
    var materialElevationScale = MaterialElevationScale(false)
    exitTransition = materialElevationScale.apply {
        duration = resources.getInteger(R.integer.metaphorreply_motion_duration_large).toLong()
    }
    materialElevationScale = MaterialElevationScale(true)
    reenterTransition = materialElevationScale.apply {
        duration = resources.getInteger(R.integer.metaphorreply_motion_duration_large).toLong()
    }
    postponeEnterTransition()
    view.doOnPreDraw { startPostponedEnterTransition() }
}

// ////Views//////

/** This method will be used for fade FadeThrough animation between two views
 * it will take root view basically CoordinatorLayout
 * start view and end view will be needed to perform a animation between them
 * */
public fun Fragment.metaphorMaterialFadeThroughBetweenViews(
    root: CoordinatorLayout,
    startView: View,
    endView: View
): MaterialFadeThrough {
    val fadeThrough = MaterialFadeThrough()
    fadeThrough.duration = 1000L
// Begin watching for changes in the View hierarchy.
    TransitionManager.beginDelayedTransition(root, fadeThrough)
// Make any changes to the hierarchy to be animated by the fade through transition.
    startView.visibility = View.GONE

    endView.visibility = View.VISIBLE
    return fadeThrough
}

/** This method will be used for fade FadeThrough animation between two views
 * it will take root view basically CoordinatorLayout
 * start view and end view will be needed to perform a animation between them
 * */
public fun Fragment.metaphorMaterialFadeBetweenViews(
    root: CoordinatorLayout,
    view: View
): MaterialFade {
    val fade = MaterialFade()
// Begin watching for changes in the View hierarchy.
    TransitionManager.beginDelayedTransition(root, fade)
// Make any changes to the hierarchy to be animated by the fade through transition.
    view.visibility = View.VISIBLE

    return fade
}

/** This method will be used for fade MaterialContainerTransform animation between two views
 * it will take root view basically CoordinatorLayout
 * start view and end view will be needed to perform a animation between them
 * */
public fun Fragment.metaphorMaterialContainerTransformViewIntoAnotherView(
    root: CoordinatorLayout,
    startView: View,
    endView: View

): MaterialContainerTransform {
    val transition = buildContainerTransformation()

    transition.startView = startView
    transition.endView = endView

    transition.addTarget(endView)

    TransitionManager.beginDelayedTransition(root, transition)
    startView.visibility = View.INVISIBLE
    endView.visibility = View.VISIBLE
    return transition
}

/**
 * builds MaterialContainerTransform object
 * */

public fun Fragment.buildContainerTransformation(): MaterialContainerTransform =
    MaterialContainerTransform().apply {
        scrimColor = Color.TRANSPARENT
        duration = 300
        setPathMotion(ArcMotion())
    }

/** This method will be used for fade MaterialContainerTransform animation between two views
 * it will take root view basically CoordinatorLayout
 * start view and end view will be needed to perform a animation between them
 * Axis is axis in which animation will be perform
 * forward is bool function to perform animation in up or down
 * */
public fun Fragment.metaphorSharedAxisTransformationBetweenViews(
    root: CoordinatorLayout,
    startView: View,
    endView: View,
    Axis: Int,
    forward: Boolean
): MaterialSharedAxis {

    // Set up a new MaterialSharedAxis in the specified axis and direction.
    val sharedAxis = MaterialSharedAxis(Axis, forward)

// Begin watching for changes in the View hierarchy.
    TransitionManager.beginDelayedTransition(root, sharedAxis)

// Make any changes to the hierarchy to be animated by the shared axis transition.
    startView.visibility = View.GONE
    endView.visibility = View.VISIBLE
    return sharedAxis
}

/** Show view with MaterialFade */
public fun Fragment.metaphorShowViewWithMaterialFade(
    root: CoordinatorLayout,
    view: View
): MaterialFade {
    val materialFade = MaterialFade().apply {
        duration = 550L
    }
    TransitionManager.beginDelayedTransition(root, materialFade)
    view.visibility = View.VISIBLE
    return materialFade
}

/** Hide view with MaterialFade */
public fun Fragment.metaphorHideViewWithMaterialFade(
    root: CoordinatorLayout,
    view: View
): MaterialFade {
    val materialFade = MaterialFade().apply {
        duration = 550L
    }
    TransitionManager.beginDelayedTransition(root, materialFade)
    view.visibility = View.INVISIBLE
    return materialFade
}

/** transform view into Fragment with MaterialContainerTransform */
public fun Fragment.metaphorMaterialContainerTransformViewIntoFragment(
    viewStart: View,
    viewEnd: View
): MaterialContainerTransform {
// Set transitions here so we are able to access Fragment's binding views.
    val materialFade = MaterialContainerTransform()
    enterTransition = materialFade.apply {
        // Manually add the Views to be shared since this is not a standard Fragment to
        // Fragment shared element transition.

        startView = viewStart
        endView = viewEnd
        duration = resources.getInteger(R.integer.metaphorreply_motion_duration_large).toLong()
        scrimColor = Color.TRANSPARENT
        containerColor = Color.TRANSPARENT
        startContainerColor = Color.TRANSPARENT
        endContainerColor = Color.TRANSPARENT
        setPathMotion(MaterialArcMotion())
    }
    returnTransition = Slide().apply {
        duration = resources.getInteger(R.integer.metaphorreply_motion_duration_medium).toLong()
        addTarget(viewEnd)
    }
    return materialFade
}

public fun Context.activity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}




/** hide view extensions*/
