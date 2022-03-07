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


## How to use In Fragments


```kotlin

  //Start Fragments onclick// 
  val extras = FragmentNavigatorExtras(view to item.pos.toString())
        val action = ArtistListFragmentDirections.navToCharacterDetailFragment(item)
        findNavController().navigate(action, extras)
		
//start fragment 
metaphorStartFragmentMaterialContainerTransform(view)// inside on onviewcreated

//destination fragment		
  metaphorDestinationFragmentMaterialContainerTransform(view, args.data.pos.toString())// inside onviewcreated


```

### How to use in views


```kotlin

   metaphorMaterialContainerTransformViewIntoAnotherView(
        viewBinding.root,
        viewBinding.fabDetail,
        viewBinding.controlsPanel
      )

```



## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/androidpoet/metaphor/stargazers)__ for this repository. :star:

# License
```xml
Copyright 2019 skydoves (Jaewoong Eum)

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
