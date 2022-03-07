<h1 align="center">Metaphor </h1></br>

<p align="center">
Metaphor is library to easily add Material Motion animations.
</p>
</br>
<p align="center">

</p> <br>

<p align="center">

</p>



## Including in your project
[![Maven Central](https://img.shields.io/maven-central/v/io.github.androidpoet/metaphor.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.androidpoet/metaphor)

### Gradle
Add the dependency below to your **module**'s `build.gradle` file:

```gradle
dependencies {
    implementation("io.github.androidpoet:metaphor:1.0.4")
}
```
Metaphor provides support for all four motion patterns
defined in the Material spec.

1.  [Container transform](#container-transform)
2.  [Shared axis](#shared-axis)
3.  [Fade through](#fade-through)
4.  [Fade](#fade)



<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157047014-2cf69797-090f-41a3-97e9-a1aeda55307a.gif" width="32%"/>

</p>

## Container transform How to use In Fragments


```kotlin

  //Start Fragments onclick// 
  val extras = FragmentNavigatorExtras(view to item.pos.toString())
        val action = ArtistListFragmentDirections.navToCharacterDetailFragment(item)
        findNavController().navigate(action, extras)
		
//start fragment 
metaphorStartFragmentMaterialContainerTransform(view)// inside on onviewcreated  ////it also retuns the object of MaterialContainerTransform() you can use .apply { } to change values

//destination fragment		
  metaphorDestinationFragmentMaterialContainerTransform(view, args.data.pos.toString())// inside onviewcreated ////it also retuns the object of MaterialContainerTransform() you can use .apply { } to change values


```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157047720-d6dcb0ab-3fe4-4078-84f3-f3be70cbb0f4.gif" width="32%"/>

</p>

## Container transform How to use in views


```kotlin

   metaphorMaterialContainerTransformViewIntoAnotherView(
        viewBinding.root,
        viewBinding.fabDetail,
        viewBinding.controlsPanel
      ) //it also retuns the object of MaterialContainerTransform() you can use .apply { } to change values

```




<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157048740-76908bb0-0937-4a33-9759-894d389a46b1.gif" width="32%"/>

</p>

## Shared axis How to use In Fragments


```kotlin
		
//start fragment 

metaphorMaterialSharedAxisInFragment(Metaphor.SharedX, true)// inside on onCreate   ////it also retuns the object of MaterialSharedAxis() you can use .apply { } to change values

//destination fragment		

metaphorMaterialSharedAxisInFragment(Metaphor.SharedX, true)// inside onCreate   ////it also retuns the object of MaterialSharedAxis() you can use .apply { } to change values


```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157049004-82bd3875-f0a6-4853-98f4-ad2d166d1259.gif" width="32%"/>

</p>

## Shared axis How to use in views


```kotlin

     metaphorSharedAxisTransformationBetweenViews(
        binding.root,
        binding.img,
        binding.img,
        Metaphor.SharedX,
        true
      )  ////it also retuns the object of MaterialSharedAxis() you can use .apply { } to change values

```






<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157048740-76908bb0-0937-4a33-9759-894d389a46b1.gif" width="32%"/>

</p>

## Fade through How to use In Fragments


```kotlin
		
//start fragment 

metaphorMaterialFadeThroughInFragment()// inside on onCreate  ////it also retuns the object of MaterialFadeThrough () you can use .apply { } to change values
 
//destination fragment		

metaphorMaterialFadeThroughInFragment()// inside onCreate  ////it also retuns the object of MaterialFadeThrough () you can use .apply { } to change values


```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157051396-9eaa6437-5c86-4fd8-abba-00b0ebafac55.gif" width="32%"/>

</p>

## Fade through How to use in views


```kotlin

    metaphorMaterialFadeThroughBetweenViews(
  root: CoordinatorLayout,
  startView: View,
  endView: View
) ////it also retuns the object of MaterialFadeThrough () you can use .apply { } to change values

```




<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157051144-645ebfed-a388-4c5c-a43d-d7c2f647ffbd.gif" width="32%"/>

</p>

## Fade  How to use In Fragments


```kotlin
		
//start fragment 

metaphorMaterialFadeInFragment()// inside on onCreate  ////it also retuns the object of MaterialFade () you can use .apply { } to change values

//destination fragment		

metaphorMaterialFadeInFragment()// inside onCreate  ////it also retuns the object of MaterialFade () you can use .apply { } to change values


```

<p align="center">
<img src="https://user-images.githubusercontent.com/13647384/157052869-9e124cef-0b3e-416b-a577-9d515e76d428.gif" width="32%"/>

</p>

## Fade  How to use in views


```kotlin

  //ShowView
   metaphorShowViewWithMaterialFade(root: CoordinatorLayout, view: View)  ////it also retuns the object of MaterialFade () you can use .apply { } to change values

//Hide View

metaphorHideViewWithMaterialFade(root: CoordinatorLayout, view: View)  ////it also retuns the object of MaterialFade () you can use .apply { } to change values

```





## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/androidpoet/metaphor/stargazers)__ for this repository. :star:

# License
```xml
Copyright 2019 AndroidPoet (Ranbir Singh)

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
