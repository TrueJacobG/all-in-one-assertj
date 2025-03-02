package com.github.truejacobg.all.in.one.assertj.part2;

import com.github.truejacobg.all.in.one.assertj.Race;
import com.github.truejacobg.all.in.one.assertj.TolkienCharacter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.assertj.core.api.AssertionsForClassTypes.notIn;


public class ArraysAndIterablesTest {

    @Test
    void arraysAndIterables() {
        var numbers = List.of(1, 2, 3, 4, 5);
        var duplicateNumbers = List.of(1, 2, 2, 3, 4, 5);
        var orderedNumbers = List.of(1, 2, 3, 4, 5);
        var unorderedNumbers = List.of(5, 1, 3, 2, 4);
        var sequenceNumbers = List.of(1, 2, 3, 6, 7, 8, 4, 5);
        var subsequenceNumbers = List.of(1, 9, 2, 10, 3, 4, 11, 5);
        var onlyOnceNumbers = List.of(1, 2, 3, 4, 5);
        var anyOfNumbers = List.of(1, 6, 7, 8);

        // contains
        assertThat(numbers).contains(3, 1); // Passes: 1 and 3 are in the list
        // Example: verifying presence of elements without order

        // containsOnly
        assertThat(unorderedNumbers).containsOnly(1, 2, 3, 4, 5); // Passes: all elements are present, no extra elements
        assertThat(duplicateNumbers).containsOnly(1, 2, 3, 4, 5); //Passes, duplicates ignored.
        // Example: verifying exact elements, ignoring order and duplicates.

        // containsExactly
        assertThat(orderedNumbers).containsExactly(1, 2, 3, 4, 5); // Passes: elements are in the specified order
        // Example: verifying exact elements in the exact order.

        // containsExactlyInAnyOrder
        assertThat(unorderedNumbers).containsExactlyInAnyOrder(1, 2, 3, 4, 5); // Passes: all elements are present, order does not matter
        // Example: verifying exact elements, order irrelevant.

        // containsSequence
        assertThat(sequenceNumbers).containsSequence(1, 2, 3); // Passes: 1, 2, 3 appear in that order without gaps
        assertThat(sequenceNumbers).containsSequence(4, 5); //Passes
        // Example: verifying a specific sequence of elements without interruptions.

        // containsSubsequence
        assertThat(subsequenceNumbers).containsSubsequence(1, 2, 3, 4, 5); // Passes: 1, 2, 3, 4, 5 appear in that order with possible gaps
        assertThat(subsequenceNumbers).containsSubsequence(2, 4); //Passes
        // Example: verifying a subsequence of elements in order, allowing gaps.

        // containsOnlyOnce
        assertThat(onlyOnceNumbers).containsOnlyOnce(1, 2); // Passes: 1 and 2 appear only once
        // Example: verifying elements appear only once.

        // containsAnyOf
        assertThat(anyOfNumbers).containsAnyOf(1, 9); // Passes: 1 is present
        // Example: verifying at least one of the given elements is present.
    }

    @Test
    void satisfies() {
        var frodo = new TolkienCharacter("Frodo", 111, new Race("Hobbit"));
        var sam = new TolkienCharacter("Sam", 93, new Race("Hobbit"));
        var hobbits = List.of(frodo, sam);

        // all elements must satisfy the given assertions
        assertThat(hobbits).allSatisfy(character -> {
            assertThat(character.getRace().getName()).isEqualTo("Hobbit");
            assertThat(character.getName()).isNotEqualTo("Sauron");
        });

        // at least one element must satisfy the given assertions
        assertThat(hobbits).anySatisfy(character -> {
            assertThat(character.getRace().getName()).isEqualTo("Hobbit");
            assertThat(character.getName()).isEqualTo("Sam");
        });

        // no element must satisfy the given assertions
        assertThat(hobbits).noneSatisfy(character -> assertThat(character.getRace().getName()).isEqualTo("Elf"));
    }

    @Test
    void match() {
        var frodo = new TolkienCharacter("Frodo", 111, new Race("Hobbit"));
        var sam = new TolkienCharacter("Sam", 93, new Race("Hobbit"));
        var hobbits = List.of(frodo, sam);

        assertThat(hobbits)
                .allMatch(character -> character.getRace().getName().equals("Hobbit"))
                .anyMatch(character -> character.getName().contains("do"))
                .noneMatch(character -> character.getName().contains("Her"));
    }

    @Test
    void element() {
        var frodo = new TolkienCharacter("Frodo", 111, new Race("Hobbit"));
        var sam = new TolkienCharacter("Sam", 93, new Race("Hobbit"));
        var sauron = new TolkienCharacter("Sauron", 830, new Race("idk"));
        var hobbits = List.of(frodo, sam, sauron);

        assertThat(hobbits).first().isEqualTo(frodo);
        assertThat(hobbits).element(1).isEqualTo(sam);
        assertThat(hobbits).last().isEqualTo(sauron);
    }

    @Test
    void singleElement() {
        Iterable<String> babySimpsons = List.of("Maggie");

        // only object assertions available
        assertThat(babySimpsons).singleElement()
                .isEqualTo("Maggie");
    }

    @Test
    void filtering() {
        var frodo = new TolkienCharacter("Frodo", 111, new Race("Hobbit"));
        var sam = new TolkienCharacter("Sam", 93, new Race("Hobbit"));
        var sauron = new TolkienCharacter("Sauron", null, new Race("idk"));
        var fellowshipOfTheRing = List.of(frodo, sam, sauron);

        // predicate
        assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                .containsOnly(frodo, sauron);

        // filters use introspection to get property/field values
        assertThat(fellowshipOfTheRing).filteredOn("name", "Frodo")
                .containsOnly(frodo);

        // nested properties are supported
        assertThat(fellowshipOfTheRing).filteredOn("race.name", "Hobbit")
                .containsOnly(frodo, sam);

        // you can apply different comparison, notIn
        assertThat(fellowshipOfTheRing).filteredOn("name", notIn("Hello", "World"))
                .containsOnly(frodo, sam, sauron);

        // in
        assertThat(fellowshipOfTheRing).filteredOn("name", in("Frodo"))
                .containsOnly(frodo);

        // not
        assertThat(fellowshipOfTheRing).filteredOn("name", not("Sam"))
                .containsOnly(frodo, sauron);

        // you can chain multiple filter criteria
        assertThat(fellowshipOfTheRing)
                .filteredOn("race.name", "Hobbit")
                .filteredOn("name", not("Sam"))
                .containsOnly(frodo);

        // on function return type
        assertThat(fellowshipOfTheRing).filteredOn(TolkienCharacter::getName, "Frodo")
                .containsOnly(frodo);

        // filter on null
        assertThat(fellowshipOfTheRing).filteredOnNull("age")
                .singleElement()
                .isEqualTo(sauron);

        // filter on assertions
        assertThat(fellowshipOfTheRing).filteredOnAssertions(hobbit -> assertThat(hobbit.getAge()).isLessThan(100))
                .containsOnly(sam);
    }
}