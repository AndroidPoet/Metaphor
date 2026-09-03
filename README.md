<h1 align="center">Metaphor </h1>

<p align="center">
	💠Android Material's motion system animations.
</p>




<p align="center">
  <a href="https://devlibrary.withgoogle.com/authors/AndroidPoet"><img alt="Google" src="https://user-images.githubusercontent.com/13647384/162663007-d911f6ce-ac1b-4754-a63b-eadbef38087f.svg"/></a>
<br>
	<br>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://medium.com/@androidpoet/metaphor-make-your-app-shine-with-material-motion-animations-73e5ffc698b4"><img alt="Medium"       src="https://user-images.githubusercontent.com/13647384/162663072-9d93cb76-1af0-49fc-b003-372e536ae171.svg"/></a>
  <a href="https://github.com/AndroidPoet"><img alt="Profile" src="https://user-images.githubusercontent.com/13647384/162662962-82e3c1eb-baf8-4e21-ad26-d4c4e3c31e44.svg"/>
    <a href="https://androidweekly.net/issues/issue-509"><img alt="Android Weekly" src="https://androidweekly.net/issues/issue-509/badge"/></a>	
	</a>



<p align="center">
<a href="https://mailchi.mp/kotlinweekly/kotlin-weekly-295"><img alt="Kotlin Weekly" src="https://img.shields.io/badge/Featured%20in%20kotlinweekly-Issue%20%20295-blue.svg"/></a>

</p> <br>







<p align="center">
	<img src="https://user-images.githubusercontent.com/13647384/176620969-686a3d1b-cf5c-4689-8a3e-ee7c35293a95.png" width="20%"/>

</p> <br>

<p align="center">

</p>

## Who's using Metaphor?
**👉 [Check out who's using Metaphor](/usecases.md)**

## Include in your project
[![Maven Central](https://img.shields.io/maven-central/v/io.github.androidpoet/metaphor.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.androidpoet/metaphor)

### Gradle
Add the dependency below to your **module**'s `build.gradle` file:

```gradle
dependencies {
    implementation("io.github.androidpoet:metaphor:2.0.0")
}
```

Metaphor provides support for all four motion patterns
defined in the Material spec, plus elevation scale and hold.

1.  [Container transform](#container-transform)
2.  [Shared axis](#shared-axis)
3.  [Fade through](#fade-through)
4.  [Fade](#fade)

## How it works

Metaphor separates **what** animates from **where** it animates.

- A `Motion` is one Material pattern as plain data: `Motion.Fade()`, `Motion.SharedAxis.x()`,
  `Motion.ContainerTransform()` and so on. Each carries only its own parameters and defaults
  to the duration the Material spec recommends.
- A `MotionSpec` is the set of motions for one screen: `enter`, `exit`, `popEnter`, `popExit`
  and an optional `sharedElement`. It holds no reference to a Fragment or Activity, so it can
  be declared once and reused anywhere.
- `applyMotion(...)` is the only thing a target needs to call. It exists for `Fragment`,
  `Activity` and `PopupWindow`. Views use `morphInto`, `animateVisibility` and `animateChanges`.

```kotlin
object Motions {
  val listOrigin = motionSpec {
    exit = Motion.ElevationScale.shrink()
    popEnter = Motion.ElevationScale.grow()
  }
  val detail = motionSpec {
    sharedElement = Motion.ContainerTransform()
  }
}
```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157047014-2cf69797-090f-41a3-97e9-a1aeda55307a.gif" width="32%"/>
</p>

## Container transform

### How to use in Fragments

The origin screen holds a list. The destination screen grows out of the tapped item and shrinks
back into it on return.

```kotlin
// Origin fragment (the list)
class ArtistListFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applyMotion {
      exit = Motion.ElevationScale.shrink()   // list fades back while the detail grows
      popEnter = Motion.ElevationScale.grow() // list comes forward again on return
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Wait for the list to lay out so the return transform can find the item view.
    postponeEnterUntilDrawn()
  }

  private fun openDetail(itemView: View, item: Artist) {
    val extras = FragmentNavigatorExtras(itemView to item.id)
    findNavController().navigate(ArtistListFragmentDirections.toDetail(item), extras)
  }
}

// Destination fragment (the detail)
class ArtistDetailFragment : Fragment() {

  private val args: ArtistDetailFragmentArgs by navArgs()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    applyMotion(
      spec = motionSpec {
        sharedElement = Motion.ContainerTransform(
          duration = 300,
          scrimColor = Color.TRANSPARENT,
          containerColor = Color.WHITE,
          path = MotionPath.Arc,
        )
      },
      sharedElement = SharedElement.Destination(view, args.item.id),
    )
  }
}
```

`Motion.ContainerTransform` takes `scrimColor`, `containerColor`, `path` (`MotionPath.Arc` or
`MotionPath.Linear`), `fadeMode` (`In`, `Out`, `Cross`, `Through`) and `duration`.

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157047720-d6dcb0ab-3fe4-4078-84f3-f3be70cbb0f4.gif" width="32%"/>
</p>

### How to use in Views

Morph one view into a sibling. The first view is hidden and the target is shown when the
transition starts. Both views must share the same parent.

```kotlin
viewBinding.fabDetail.setOnClickListener {
  viewBinding.fabDetail.morphInto(viewBinding.controls, Motion.ContainerTransform(duration = 300))
}

viewBinding.controls.setOnClickListener {
  viewBinding.controls.morphInto(viewBinding.fabDetail)
}
```

### How to use in Activities

```kotlin
// Origin activity
class ArtistListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    applyMotion(SharedElement.Origin) {
      sharedElement = Motion.ContainerTransform()
      exit = Motion.Hold()
    }
  }

  private fun openDetail(card: View) {
    card.transitionName = "card"
    val options = ActivityOptions.makeSceneTransitionAnimation(this, card, "card")
    startActivity(Intent(this, ArtistDetailActivity::class.java), options.toBundle())
  }
}

// Destination activity
class ArtistDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    applyMotion(SharedElement.Destination(binding.root, "card")) {
      sharedElement = Motion.ContainerTransform()
    }
  }
}
```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157048740-76908bb0-0937-4a33-9759-894d389a46b1.gif" width="32%"/>
</p>

## Shared axis

### How to use in Fragments

Both screens set the same axis. `forward = true` plays when moving deeper, `forward = false`
plays when popping back.

```kotlin
// Origin fragment
class SettingsFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applyMotion {
      exit = Motion.SharedAxis.x(forward = true)
      popEnter = Motion.SharedAxis.x(forward = false)
    }
  }
}

// Destination fragment
class AccountFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applyMotion {
      enter = Motion.SharedAxis.x(forward = true)
      popExit = Motion.SharedAxis.x(forward = false)
    }
  }
}
```

`Motion.SharedAxis.y()` and `Motion.SharedAxis.z()` work the same way, or build one directly:

```kotlin
Motion.SharedAxis(axis = Motion.SharedAxis.Axis.Z, forward = true, duration = 400)
```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157049004-82bd3875-f0a6-4853-98f4-ad2d166d1259.gif" width="32%"/>
</p>

### How to use in Views

```kotlin
viewBinding.sharedX.setOnClickListener {
  viewBinding.img.toggleVisibility(Motion.SharedAxis.x(forward = true, duration = 1000))
}

viewBinding.sharedY.setOnClickListener {
  viewBinding.img.toggleVisibility(Motion.SharedAxis.y(forward = true))
}

viewBinding.sharedZ.setOnClickListener {
  viewBinding.img.toggleVisibility(Motion.SharedAxis.z(forward = true))
}
```

### How to use in Activities

```kotlin
// Origin activity, in onCreate
applyMotion {
  exit = Motion.SharedAxis.x(forward = true)
  popEnter = Motion.SharedAxis.x(forward = false)
}

// Destination activity, in onCreate
applyMotion {
  enter = Motion.SharedAxis.x(forward = true)
  popExit = Motion.SharedAxis.x(forward = false)
}
```

## Fade through

### How to use in Fragments

Fade through suits screens that are not related, such as bottom navigation tabs.

```kotlin
// Origin fragment
class HomeFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applyMotion {
      enter = Motion.FadeThrough()
      exit = Motion.FadeThrough()
    }
  }
}

// Destination fragment
class DashboardFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applyMotion {
      enter = Motion.FadeThrough(duration = 300)
      exit = Motion.FadeThrough(duration = 300)
    }
  }
}
```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157051396-9eaa6437-5c86-4fd8-abba-00b0ebafac55.gif" width="32%"/>
</p>

### How to use in Views

```kotlin
viewBinding.materialFadeThrough.setOnClickListener {
  viewBinding.img2.toggleVisibility(Motion.FadeThrough(duration = 1000))
}
```

Swap two views in one go:

```kotlin
viewBinding.container.animateChanges(Motion.FadeThrough()) {
  viewBinding.summary.isVisible = false
  viewBinding.detail.isVisible = true
}
```

### How to use in Activities

```kotlin
// In onCreate of both activities
applyMotion {
  enter = Motion.FadeThrough()
  exit = Motion.FadeThrough()
}
```

## Fade

### How to use in Fragments

```kotlin
// Origin fragment
class NotificationsFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applyMotion {
      exit = Motion.Fade()
      popEnter = Motion.Fade()
    }
  }
}

// Destination fragment
class NotificationDetailFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applyMotion {
      enter = Motion.Fade(duration = 150)
      popExit = Motion.Fade(duration = 75)
    }
  }
}
```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157052869-9e124cef-0b3e-416b-a577-9d515e76d428.gif" width="32%"/>
</p>

### How to use in Views

```kotlin
viewBinding.materialFade.setOnClickListener {
  viewBinding.img3.toggleVisibility(Motion.Fade(duration = 1000))
}

// or set the state explicitly
viewBinding.banner.animateVisibility(visible = false, motion = Motion.Fade())
```

## Elevation scale

Elevation scale is the usual partner of a container transform: the origin screen scales down
and fades while the detail grows over it, then scales back up on return.

```kotlin
// Origin fragment, in onCreate
applyMotion {
  exit = Motion.ElevationScale.shrink()
  popEnter = Motion.ElevationScale.grow()
}
```

## Hold

Hold keeps the origin screen frozen in place for the length of the transition. Use it on the
screen an Activity container transform starts from so it does not fade under the growing
container.

```kotlin
applyMotion(SharedElement.Origin) {
  sharedElement = Motion.ContainerTransform()
  exit = Motion.Hold(duration = 300)
}
```

## PopupWindow

```kotlin
val popup = PopupWindow(contentView, WRAP_CONTENT, WRAP_CONTENT, true)
popup.applyMotion {
  enter = Motion.Fade()
  exit = Motion.Fade()
}
popup.showAsDropDown(anchor)
```

## Declare once, apply anywhere

A `MotionSpec` has no reference to a Fragment or Activity, so app-wide motion lives in one
place and each screen applies what it needs.

```kotlin
object Motions {
  val tab = motionSpec {
    enter = Motion.FadeThrough()
    exit = Motion.FadeThrough()
  }

  val listOrigin = motionSpec {
    exit = Motion.ElevationScale.shrink()
    popEnter = Motion.ElevationScale.grow()
  }

  fun detail(duration: Long = 300) = motionSpec {
    sharedElement = Motion.ContainerTransform(duration = duration)
  }
}

// Home tab
override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  applyMotion(Motions.tab)
}

// List screen
override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  applyMotion(Motions.listOrigin)
}

// Detail screen
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)
  applyMotion(Motions.detail(), SharedElement.Destination(view, args.id))
}
```

Specs are plain data classes, so `copy` works for small variations:

```kotlin
val slowDetail = Motions.detail().copy(allowEnterOverlap = true)
```

## Use from Java

```java
MotionSpec spec = new MotionSpec(
    Motion.SharedAxis.x(true, 300L),   // enter
    Motion.SharedAxis.x(true, 300L),   // exit
    Motion.SharedAxis.x(false, 300L),  // popEnter
    Motion.SharedAxis.x(false, 300L),  // popExit
    null,                              // sharedElement
    false,                             // allowEnterOverlap
    false                              // allowReturnOverlap
);
FragmentMotionKt.applyMotion(fragment, spec, null);
```

## Supported motions

```kotlin
Motion.Fade(duration = 150)
Motion.FadeThrough(duration = 300)
Motion.SharedAxis(axis = Axis.X, forward = true, duration = 300)   // also SharedAxis.x() / .y() / .z()
Motion.ElevationScale(growing = true, duration = 300)              // also ElevationScale.grow() / .shrink()
Motion.ContainerTransform(scrimColor, containerColor, path, fadeMode, duration = 300)
Motion.Hold(duration = 300)
```

## Migrating from 1.x

The 1.x builders (`MetaphorFragment`, `MetaphorActivity`, `MetaphorView`, `MetaphorWindow`) and
the `MetaphorAnimation` enum still compile and delegate to the new API, but are deprecated and
will be removed in 3.0. `hold()` is now `postponeEnterUntilDrawn()`. The `Factory` and lazy
`metaphorFragment<T>()` helpers were removed; declare a `MotionSpec` in an object and apply it
instead. `BottomNavigationView.show()` / `hide()` moved to the `com.androidpoet.metaphor.widgets`
package.

| 1.x | 2.0 |
| --- | --- |
| `MetaphorFragment.Builder(this).setEnterAnimation(FadeThrough).build().animate()` | `applyMotion { enter = Motion.FadeThrough() }` |
| `.setExitAnimation(ContainerTransform).setView(view).setTransitionName(name)` | `applyMotion(spec, SharedElement.Destination(view, name))` |
| `MetaphorView.Builder(a).setEndView(b).setMetaphorAnimation(ContainerTransform)` | `a.morphInto(b)` |
| `MetaphorView.Builder(v).setEndView(v).setMetaphorAnimation(Fade)` | `v.toggleVisibility(Motion.Fade())` |
| `SharedAxisXBackward` | `Motion.SharedAxis.x(forward = false)` |
| `ElevationScaleGrow` / `ElevationScale` | `Motion.ElevationScale.grow()` / `.shrink()` |


images credit: https://picsum.photos/ (Unsplash photos)


## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/AndroidPoet/Metaphor/stargazers)__ for this repository. :star: <br>
Also, __[follow me](https://github.com/AndroidPoet)__ on GitHub for more cool projects! 🤩

<a href="https://www.buymeacoffee.com/AndroidPoet" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/default-orange.png" alt="Buy Me A Coffee" height="41" width="174"></a>



# License
```xml
Copyright 2022 AndroidPoet (Ranbir Singh)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
