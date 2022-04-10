
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
  None(1),
  ContainerTransform(2),
  FadeThrough(3),
  Fade(4),
  SharedAxisXForward(5),
  SharedAxisYForward(6),
  SharedAxisZForward(7),
  SharedAxisXBackward(8),
  SharedAxisYBackward(9),
  SharedAxisZBackward(10),
  ElevationScaleGrow(11),
  ElevationScale(12),
}
