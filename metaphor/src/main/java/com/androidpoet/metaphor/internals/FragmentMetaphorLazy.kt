package com.androidpoet.metaphor.internals


import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.androidpoet.metaphor.MetaphorFragment
import java.io.Serializable
import kotlin.reflect.KClass

/**
 * An implementation of [Lazy] for creating an instance of the [MetaphorFragment] lazily in Fragments.
 *
 * @param Fragment A context for creating resources of the [MetaphorFragment] lazily.
 * @param factory A [MetaphorFragment.Factory] kotlin class for creating a new instance of the MetaphorFragment.
 */
@PublishedApi
internal class FragmentMetaphorLazy<out T : MetaphorFragment.Factory>(
    private val fragment: Fragment,
    private val factory: KClass<T>
) : Lazy<MetaphorFragment>, Serializable {

    private var cached: MetaphorFragment? = null

    override val value: MetaphorFragment
        get() {
            var instance = cached
            if (instance === null) {
                val factory = factory::java.get().newInstance()
                instance = factory.create(fragment)
                cached = instance
            }

            return instance
        }

    override fun isInitialized(): Boolean = cached !== null

    override fun toString(): String =
        if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}