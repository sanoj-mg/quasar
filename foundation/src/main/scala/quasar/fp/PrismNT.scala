/*
 * Copyright 2014–2016 SlamData Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package quasar.fp

import quasar.Predef._

import matryoshka._
import scalaz._, Scalaz._

/** Just like Prism, but operates over Functors.
  */
final case class PrismNT[F[_], G[_]]
  (get: F ~> (Option ∘ G)#λ, reverseGet: G ~> F) {

  val getOrModify: F ~> λ[α => F[α] \/ G[α]] =
    new (F ~> λ[α => F[α] \/ G[α]]) {
      def apply[A](fa: F[A]) = get(fa).fold(fa.left[G[A]])(_.right)
    }
}
