/**
 * Copyright 2016 Jordan Zimmerman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.soabase.halva.tuple.details;

import io.soabase.halva.any.AnyVal;

public class Assign6<A, B, C, D, E, F>
{
    public final AnyVal<A> _1;
    public final AnyVal<B> _2;
    public final AnyVal<C> _3;
    public final AnyVal<D> _4;
    public final AnyVal<E> _5;
    public final AnyVal<F> _6;

    public Assign6(AnyVal<A> _1, AnyVal<B> _2, AnyVal<C> _3, AnyVal<D> _4, AnyVal<E> _5, AnyVal<F> _6)
    {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
    }

    public Tuple6<A, B, C, D, E, F> from(Tuple6<A, B, C, D, E, F> from)
    {
        _1.set(from._1);
        _2.set(from._2);
        _3.set(from._3);
        _4.set(from._4);
        _5.set(from._5);
        _6.set(from._6);
        return from;
    }
}
