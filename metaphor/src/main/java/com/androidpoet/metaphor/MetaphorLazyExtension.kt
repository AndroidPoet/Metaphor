
package com.androidpoet.metaphor

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.internals.ActivityMetaphorLazy
import com.androidpoet.metaphor.internals.FragmentMetaphorLazy

/**
 * Returns a [Lazy] delegate to access the [MetaphorFragment]'s Balloon property.
 * The balloon property will be initialized lazily.
 *
 * @see [Lazy Initialization](https://github.com/skydoves/Balloon#lazy-initialization)
 */
@MainThread
@JvmSynthetic
@MetaphorFragmentInlineDsl
public inline fun <reified T : MetaphorFragment.Factory> Fragment.metaphorFragment(): Lazy<MetaphorFragment> {
  return FragmentMetaphorLazy(fragment = this, factory = T::class)
}

/**
 * Returns a [Lazy] delegate to access the [MetaphorActivity]'s Balloon property.
 * The balloon property will be initialized lazily.
 *
 * @see [Lazy Initialization](https://github.com/skydoves/Balloon#lazy-initialization)
 */
@MainThread
@JvmSynthetic
@MetaphorActivityInlineDsl
public inline fun <reified T : MetaphorActivity.Factory> AppCompatActivity.metaphorActivity(): Lazy<MetaphorActivity> {
  return ActivityMetaphorLazy(activity = this, factory = T::class)
}
