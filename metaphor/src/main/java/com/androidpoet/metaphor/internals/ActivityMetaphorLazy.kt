package com.androidpoet.metaphor.internals

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.androidpoet.metaphor.MetaphorActivity
import java.io.Serializable
import kotlin.reflect.KClass


/**
 * An implementation of [Lazy] for creating an instance of the [MetaphorActivity] lazily in Activities.
 * Tied to the given [lifecycleOwner], [factory].
 *
 * @param AppCompatActivity A context for creating resources of the [MetaphorActivity] lazily.
 * @param factory A [MetaphorActivity.Factory] kotlin class for creating a new instance of the MetaphorActivity.
 */
@PublishedApi
internal class ActivityMetaphorLazy<out T : MetaphorActivity.Factory>(
    private val activity: AppCompatActivity,
    private val factory: KClass<T>
) : Lazy<MetaphorActivity>, Serializable {

    private var cached: MetaphorActivity? = null

    override val value: MetaphorActivity
        get() {
            var instance = cached
            if (instance === null) {
                val factory = factory::java.get().newInstance()
                instance = factory.create(activity)
                cached = instance
            }

            return instance
        }

    override fun isInitialized(): Boolean = cached !== null

    override fun toString(): String =
        if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}
