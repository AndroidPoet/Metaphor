
package com.androidpoet.metaphor
/*
 *
 *  * Copyright 2022 AndroidPoet (Ranbir Singh)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

/** MetaphorAnimation is an animation attribute of [MetaphorAnimation]'s the showing and dismissing. */

public enum class MetaphorAnimation(public val value: Int) {
  ContainerTransform(0),
  FadeThrough(1),
  Fade(2),
  SharedAxisXForward(3),
  SharedAxisYForward(4),
  SharedAxisZForward(5),
  SharedAxisXBackward(6),
  SharedAxisYBackward(7),
  SharedAxisZBackward(8),
  Hold(9),
  ElevationScaleGrow(10),
  ElevationScale(11),
}
