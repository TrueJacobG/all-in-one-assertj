package com.github.truejacobg.all.in.one.assertj.part2;

import com.github.truejacobg.all.in.one.assertj.Race;
import com.github.truejacobg.all.in.one.assertj.TolkienCharacter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.tuple;

public class BasicAssertionsTest {

    @Test
    void basic() {
        var frodo = new TolkienCharacter("Frodo", 111, new Race("Hobbit"));
        var sam = new TolkienCharacter("Sam", 93, new Race("Hobbit"));
        var sauron = new TolkienCharacter("Sauron", 400, new Race("idk"));
        var fellowshipOfTheRing = List.of(frodo, sam);

        // basic assertions
        assertThat(frodo.getName()).isEqualTo("Frodo");
        assertThat(frodo).isNotEqualTo(sauron);

        // chaining string specific assertions
        assertThat(frodo.getName()).startsWith("Fro")
                .endsWith("do")
                .isEqualToIgnoringCase("frodo");

        // collection specific assertions (there are plenty more)
        // in the examples below fellowshipOfTheRing is a List<TolkienCharacter>
        assertThat(fellowshipOfTheRing).hasSize(2)
                .contains(frodo, sam)
                .doesNotContain(sauron);

        // as() is used to describe the test and will be shown before the error message
        assertThat(frodo.getAge()).as("check %s's age", frodo.getName()).isEqualTo(111);

        // exception assertion, standard style ...
        assertThatThrownBy(() -> {
            throw new Exception("boom!");
        }).hasMessage("boom!");
        // ... or BDD style
        Throwable thrown = catchThrowable(() -> {
            throw new Exception("boom!");
        });
        assertThat(thrown).hasMessageContaining("boom");

        // using the 'extracting' feature to check fellowshipOfTheRing character's names
        assertThat(fellowshipOfTheRing).extracting(TolkienCharacter::getName)
                .doesNotContain("Sauron", "Elrond");

        // extracting multiple values at once grouped in tuples
        assertThat(fellowshipOfTheRing).extracting("name", "age", "race.name")
                .contains(tuple("Frodo", 111, "Hobbit"),
                        tuple("Sam", 93, "Hobbit"));

        // filtering a collection before asserting
        assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                .containsOnly(frodo);

        // combining filtering and extraction (yes we can)
        assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                .containsOnly(frodo)
                .extracting(character -> character.getRace().getName())
                .contains("Hobbit");
    }
}
