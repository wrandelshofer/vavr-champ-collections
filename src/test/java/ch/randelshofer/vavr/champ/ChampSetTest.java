/* ____  ______________  ________________________  __________
 * \   \/   /      \   \/   /   __/   /      \   \/   /      \
 *  \______/___/\___\______/___/_____/___/\___\______/___/\___\
 *
 * The MIT License (MIT)
 *
 * Copyright 2024 Vavr, https://vavr.io
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ch.randelshofer.vavr.champ;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.assertj.core.api.BooleanAssert;
import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.api.LongAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.StringAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChampSetTest extends AbstractSetTest {

    @Override
    protected <T> IterableAssert<T> assertThat(Iterable<T> actual) {
        return new IterableAssert<T>(actual) {
            private java.util.Map<T, Integer> countMap(Iterable<? extends T> it) {
                final java.util.HashMap<T, Integer> cnt = new java.util.HashMap<>();
                it.forEach(i -> cnt.merge(i, 1, (v1, v2) -> v1 + v2));
                return cnt;
            }

            @Override
            public IterableAssert<T> isEqualTo(Object obj) {
                @SuppressWarnings("unchecked") final Iterable<T> expected = (Iterable<T>) obj;
                final java.util.Map<T, Integer> actualMap = countMap(actual);
                final java.util.Map<T, Integer> expectedMap = countMap(expected);
                ChampSetTest.this.assertThat(actualMap.size()).isEqualTo(expectedMap.size());
                actualMap.keySet().forEach(k -> ChampSetTest.this.assertThat(actualMap.get(k)).isEqualTo(expectedMap.get(k)));
                return this;
            }
        };
    }

    @Override
    protected <T> ObjectAssert<T> assertThat(T actual) {
        return new ObjectAssert<T>(actual) {
        };
    }

    @Override
    protected BooleanAssert assertThat(Boolean actual) {
        return new BooleanAssert(actual) {
        };
    }

    @Override
    protected DoubleAssert assertThat(Double actual) {
        return new DoubleAssert(actual) {
        };
    }

    @Override
    protected IntegerAssert assertThat(Integer actual) {
        return new IntegerAssert(actual) {
        };
    }

    @Override
    protected LongAssert assertThat(Long actual) {
        return new LongAssert(actual) {
        };
    }

    @Override
    protected StringAssert assertThat(String actual) {
        return new StringAssert(actual) {
        };
    }

    // -- construction

    @Override
    protected <T> Collector<T, ArrayList<T>, ChampSet<T>> collector() {
        return ChampSet.collector();
    }

    @Override
    protected <T> ChampSet<T> empty() {
        return ChampSet.empty();
    }

    @Override
    protected <T> ChampSet<T> emptyWithNull() {
        return empty();
    }

    @Override
    protected <T> ChampSet<T> fill(int n, Supplier<? extends T> s) {
        return ChampSet.fill(n, s);
    }

    @Override
    protected int getPeekNonNilPerformingAnAction() {
        return 1;
    }

    @Override
    protected <T> ChampSet<T> of(T element) {
        return ChampSet.of(element);
    }

    @SuppressWarnings("varargs")
    @SafeVarargs
    @Override
    protected final <T> ChampSet<T> of(T... elements) {
        return ChampSet.of(elements);
    }

    @Override
    protected <T> ChampSet<T> ofAll(Iterable<? extends T> elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Boolean> ofAll(boolean... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Byte> ofAll(byte... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Character> ofAll(char... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Double> ofAll(double... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Float> ofAll(float... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Integer> ofAll(int... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Long> ofAll(long... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected ChampSet<Short> ofAll(short... elements) {
        return ChampSet.ofAll(elements);
    }

    @Override
    protected <T extends Comparable<? super T>> ChampSet<T> ofJavaStream(java.util.stream.Stream<? extends T> javaStream) {
        return ChampSet.ofAll(javaStream);
    }

    @Override
    protected ChampSet<Character> range(char from, char toExclusive) {
        return ChampSet.range(from, toExclusive);
    }

    // -- static narrow

    @Override
    protected ChampSet<Integer> range(int from, int toExclusive) {
        return ChampSet.range(from, toExclusive);
    }

    @Override
    protected ChampSet<Long> range(long from, long toExclusive) {
        return ChampSet.range(from, toExclusive);
    }

    // TODO move to traversable
    // -- zip

    @Override
    protected ChampSet<Character> rangeBy(char from, char toExclusive, int step) {
        return ChampSet.rangeBy(from, toExclusive, step);
    }

    @Override
    protected ChampSet<Double> rangeBy(double from, double toExclusive, double step) {
        return ChampSet.rangeBy(from, toExclusive, step);
    }

    @Override
    protected ChampSet<Integer> rangeBy(int from, int toExclusive, int step) {
        return ChampSet.rangeBy(from, toExclusive, step);
    }

    @Override
    protected ChampSet<Long> rangeBy(long from, long toExclusive, long step) {
        return ChampSet.rangeBy(from, toExclusive, step);
    }

    @Override
    protected ChampSet<Character> rangeClosed(char from, char toInclusive) {
        return ChampSet.rangeClosed(from, toInclusive);
    }

    @Override
    protected ChampSet<Integer> rangeClosed(int from, int toInclusive) {
        return ChampSet.rangeClosed(from, toInclusive);
    }

    @Override
    protected ChampSet<Long> rangeClosed(long from, long toInclusive) {
        return ChampSet.rangeClosed(from, toInclusive);
    }

    // TODO move to traversable
    // -- zipAll

    @Override
    protected ChampSet<Character> rangeClosedBy(char from, char toInclusive, int step) {
        return ChampSet.rangeClosedBy(from, toInclusive, step);
    }

    @Override
    protected ChampSet<Double> rangeClosedBy(double from, double toInclusive, double step) {
        return ChampSet.rangeClosedBy(from, toInclusive, step);
    }

    @Override
    protected ChampSet<Integer> rangeClosedBy(int from, int toInclusive, int step) {
        return ChampSet.rangeClosedBy(from, toInclusive, step);
    }

    @Override
    protected ChampSet<Long> rangeClosedBy(long from, long toInclusive, long step) {
        return ChampSet.rangeClosedBy(from, toInclusive, step);
    }

    @Test
    public void shouldBeEqual() {
        assertTrue(ChampSet.of(1).equals(ChampSet.of(1)));
    }

    @Override
    @Test
    public void shouldComputeDistinctByOfNonEmptyTraversableUsingComparator() {
        // TODO
    }

    @Override
    @Test
    public void shouldComputeDistinctByOfNonEmptyTraversableUsingKeyExtractor() {
        // TODO
    }

    // TODO move to traversable
    // -- zipWithIndex

    @Override
    @Test
    public void shouldDropRightAsExpectedIfCountIsLessThanSize() {
        assertThat(of(1, 2, 3).dropRight(2)).isEqualTo(of(3));
    }

    @Override
    @Test
    public void shouldFindLastOfNonNil() {
        final int actual = of(1, 2, 3, 4).findLast(i -> i % 2 == 0).get();
        assertThat(actual).isIn(List.of(1, 2, 3, 4));
    }

    // -- transform()

    @Override
    @Test
    public void shouldFoldRightNonNil() {
        final String actual = of('a', 'b', 'c').foldRight("", (x, xs) -> x + xs);
        final List<String> expected = List.of('a', 'b', 'c').permutations().map(List::mkString);
        assertThat(actual).isIn(expected);
    }

    // ChampSet special cases

    @Override
    @Test
    public void shouldGetInitOfNonNil() {
        assertThat(of(1, 2, 3).init()).isEqualTo(of(2, 3));
    }

    @Override
    @Test
    public void shouldMkStringWithDelimiterAndPrefixAndSuffixNonNil() {
        final String actual = of('a', 'b', 'c').mkString("[", ",", "]");
        final List<String> expected = List.of('a', 'b', 'c').permutations().map(l -> l.mkString("[", ",", "]"));
        assertThat(actual).isIn(expected);
    }

    @Override
    @Test
    public void shouldMkStringWithDelimiterNonNil() {
        final String actual = of('a', 'b', 'c').mkString(",");
        final List<String> expected = List.of('a', 'b', 'c').permutations().map(l -> l.mkString(","));
        assertThat(actual).isIn(expected);
    }

    @Test
    public void shouldNarrowHashSet() {
        final ChampSet<Double> doubles = of(1.0d);
        final ChampSet<Number> numbers = ChampSet.narrow(doubles);
        final int actual = numbers.add(new BigDecimal("2.0")).sum().intValue();
        assertThat(actual).isEqualTo(3);
    }

    @Test
    public void shouldNotHaveOrderedSpliterator() {
        assertThat(of(1, 2, 3).spliterator().hasCharacteristics(Spliterator.ORDERED)).isFalse();
    }

    @Test
    public void shouldNotHaveSortedSpliterator() {
        assertThat(of(1, 2, 3).spliterator().hasCharacteristics(Spliterator.SORTED)).isFalse();
    }

    @Override
    @Test
    public void shouldReduceRightNonNil() {
        final String actual = of("a", "b", "c").reduceRight((x, xs) -> x + xs);
        final List<String> expected = List.of("a", "b", "c").permutations().map(List::mkString);
        assertThat(actual).isIn(expected);
    }

    @Test
    public void shouldReturnFalseWhenIsSequentialCalled() {
        assertThat(of(1, 2, 3).isSequential()).isFalse();
    }

    @Test
    @Disabled("WR our collection is not the same class than the one in io.vavr")
    public void shouldReturnSelfOnConvertToSet() {
        final ChampSet<Integer> value = of(1, 2, 3);
        assertThat(value.toSet()).isSameAs(value);
    }

    @Override
    @Test
    public void shouldReturnSomeInitWhenCallingInitOptionOnNonNil() {
        // TODO
    }

    // -- slideBy is not expected to work for larger subsequences, due to unspecified iteration order
    @Test
    public void shouldSlideNonNilBySomeClassifier() {
        // ignore
    }

    @Override
    @Test
    public void shouldTakeRightAsExpectedIfCountIsLessThanSize() {
        assertThat(of(1, 2, 3).takeRight(2)).isEqualTo(of(1, 2));
    }

    @Test
    public void shouldThrowIfZipAllWithThatIsNull() {
        assertThrows(NullPointerException.class, () -> empty().zipAll(null, null, null));
    }

    @Test
    public void shouldThrowIfZipWithThatIsNull() {
        assertThrows(NullPointerException.class, () -> empty().zip(null));
    }

    @Override
    @Test
    public void shouldThrowWhenFoldRightNullOperator() {
        assertThrows(NullPointerException.class, () -> Objects.requireNonNull(null)); // TODO
    }

    @Test
    public void shouldTransform() {
        final String transformed = of(42).transform(v -> String.valueOf(v.get()));
        assertThat(transformed).isEqualTo("42");
    }

    @Test
    public void shouldZipAllEmptyAndNonNil() {
        // ignore
    }

    @Test
    public void shouldZipAllNils() {
        // ignore
    }

    @Test
    public void shouldZipAllNonEmptyAndNil() {
        final ChampSet<?> actual = of(1).zipAll(empty(), null, null);
        final ChampSet<Tuple2<Integer, Object>> expected = of(Tuple.of(1, null));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldZipAllNonNilsIfThatIsSmaller() {
        final ChampSet<Tuple2<Integer, String>> actual = of(1, 2, 3).zipAll(of("a", "b"), 9, "z");
        final ChampSet<Tuple2<Integer, String>> expected = of(Tuple.of(1, "a"), Tuple.of(2, "b"), Tuple.of(3, "z"));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldZipAllNonNilsIfThisIsSmaller() {
        final ChampSet<Tuple2<Integer, String>> actual = of(1, 2).zipAll(of("a", "b", "c"), 9, "z");
        final ChampSet<Tuple2<Integer, String>> expected = of(Tuple.of(1, "a"), Tuple.of(2, "b"), Tuple.of(9, "c"));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldZipAllNonNilsOfSameSize() {
        final ChampSet<Tuple2<Integer, String>> actual = of(1, 2, 3).zipAll(of("a", "b", "c"), 9, "z");
        final ChampSet<Tuple2<Integer, String>> expected = of(Tuple.of(1, "a"), Tuple.of(2, "b"), Tuple.of(3, "c"));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldZipEmptyAndNonNil() {
        final ChampSet<Tuple2<Object, Integer>> actual = empty().zip(of(1));
        assertThat(actual).isEqualTo(empty());
    }

    @Test
    public void shouldZipNilWithIndex() {
        assertThat(this.<String>empty().zipWithIndex()).isEqualTo(this.<Tuple2<String, Integer>>empty());
    }

    @Test
    public void shouldZipNils() {
        final ChampSet<Tuple2<Object, Object>> actual = empty().zip(empty());
        assertThat(actual).isEqualTo(empty());
    }

    @Test
    public void shouldZipNonEmptyAndNil() {
        final ChampSet<Tuple2<Integer, Integer>> actual = of(1).zip(empty());
        assertThat(actual).isEqualTo(empty());
    }

    @Test
    public void shouldZipNonNilWithIndex() {
        final ChampSet<Tuple2<String, Integer>> actual = of("a", "b", "c").zipWithIndex();
        final ChampSet<Tuple2<String, Integer>> expected = of(Tuple.of("a", 0), Tuple.of("b", 1), Tuple.of("c", 2));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldZipNonNilsIfThatIsSmaller() {
        final ChampSet<Tuple2<Integer, String>> actual = of(1, 2, 3).zip(of("a", "b"));
        final ChampSet<Tuple2<Integer, String>> expected = of(Tuple.of(1, "a"), Tuple.of(2, "b"));
        assertThat(actual).isEqualTo(expected);
    }

    // -- toSet

    @Test
    public void shouldZipNonNilsIfThisIsSmaller() {
        final ChampSet<Tuple2<Integer, String>> actual = of(1, 2).zip(of("a", "b", "c"));
        final ChampSet<Tuple2<Integer, String>> expected = of(Tuple.of(1, "a"), Tuple.of(2, "b"));
        assertThat(actual).isEqualTo(expected);
    }

    // -- spliterator

    @Test
    public void shouldZipNonNilsOfSameSize() {
        final ChampSet<Tuple2<Integer, String>> actual = of(1, 2, 3).zip(of("a", "b", "c"));
        final ChampSet<Tuple2<Integer, String>> expected = of(Tuple.of(1, "a"), Tuple.of(2, "b"), Tuple.of(3, "c"));
        assertThat(actual).isEqualTo(expected);
    }

    @Override
    protected <T> ChampSet<T> tabulate(int n, Function<? super Integer, ? extends T> f) {
        return ChampSet.tabulate(n, f);
    }

    // -- isSequential()

    //fixme: delete, when useIsEqualToInsteadOfIsSameAs() will be eliminated from AbstractValueTest class
    @Override
    protected boolean useIsEqualToInsteadOfIsSameAs() {
        return false;
    }

}
