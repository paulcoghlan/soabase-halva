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
package io.soabase.halva.tuple;

import io.soabase.halva.any.Any;
import io.soabase.halva.any.AnyVal;
import io.soabase.halva.sugar.ConsList;
import org.junit.Assert;
import org.junit.Test;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static io.soabase.halva.matcher.Matcher.match;
import static io.soabase.halva.sugar.Sugar.List;
import static io.soabase.halva.tuple.Assign.Assign;
import static io.soabase.halva.tuple.Tuple.Pair;
import static io.soabase.halva.tuple.Tuple.Tu;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class TestTuple
{
    @Test
    public void testBasic()
    {
        assertEquals(Tu(), Tu());
        assertEquals(Tu("x"), Tu("x"));
        assertNotEquals("x", Tu("x"));
        assertEquals(Tu("x"), "x");

        assertEquals(Tu("x", 10, Charset.defaultCharset()), Tu("x", 10, Charset.defaultCharset()));

        List<String> arrayList = Arrays.asList("a", "b", "c");
        Collection<String> set = arrayList.stream().collect(Collectors.toSet());
        assertNotEquals(Tu(arrayList), Tu(set));

        assertEquals(Tu("a", Tu(1, 2, 3), "b"), Tu("a", Tu(1, 2, 3), "b"));
    }

    private String extractFunc(ConsList<Pair<String, Integer>> list)
    {
        AnyVal<Pair<String, Integer>> p = new AnyVal<Pair<String, Integer>>(){};
        AnyVal<ConsList<Pair<String, Integer>>> t = new AnyVal<ConsList<Pair<String, Integer>>>(){};

        AnyVal<?> a = Any.headAnyTail(Pair("10", 10), t);
        AnyVal<?> b = Any.headTail(Pair("-10", -10), List(Pair("20", 20)));
        AnyVal<?> c = Any.anyHeadTail(p, List(Pair("20", 20), Pair("30", 30)));
        AnyVal<?> d = Any.anyHeadAnyTail(p, t);

        return match(list)
            .caseOf(List(), () -> "empty")
            .caseOf(a, () -> "10/10 :: " + t.val())
            .caseOf(b, () -> "-10/-10 :: 20/20")
            .caseOf(c, () -> p.val() + " :: 20/20 30/30")
            .caseOf(d, () -> p.val() + " :: " + t.val())
            .caseOf(() -> "error")
            .get();
    }

    @Test
    public void testConsListExtraction()
    {
        Assert.assertEquals("empty", extractFunc(List()));
        Assert.assertEquals("10/10 :: " + List(Pair("100", 100), Pair("200", 200)), extractFunc(List(Pair("10", 10), Pair("100", 100), Pair("200", 200))));
        Assert.assertEquals("-10/-10 :: 20/20", extractFunc(List(Pair("-10", -10), Pair("20", 20))));
        Assert.assertEquals(Pair("100", 100) + " :: 20/20 30/30", extractFunc(List(Pair("100", 100), Pair("20", 20), Pair("30", 30))));
        Assert.assertEquals(Pair("66", 66) + " :: " + List(Pair("100", 100), Pair("200", 200)), extractFunc(List(Pair("66", 66), Pair("100", 100), Pair("200", 200))));
    }

    @Test
    public void testMultiAssignment()
    {
        AnyVal<Integer> a = Any.any();
        AnyVal<Integer> b = Any.any();
        Assign(a, b).from(Tu(1, 2));
        Assert.assertEquals(a.val().intValue(), 1);
        Assert.assertEquals(b.val().intValue(), 2);

        AnyVal<String> x = Any.any();
        AnyVal<List<String>> y = Any.any();
        Assign(x, y, a, b).from(Tu("hey", List("one", "two"), 10, 20));
        Assert.assertEquals(x.val(), "hey");
        Assert.assertEquals(y.val(), List("one", "two"));
        Assert.assertEquals(a.val().intValue(), 10);
        Assert.assertEquals(b.val().intValue(), 20);
    }
}
