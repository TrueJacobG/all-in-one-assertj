package com.github.truejacobg.all.in.one.assertj.part2;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.assertj.core.util.Sets.newLinkedHashSet;

public class ConditionsTest {

    static Set<String> JEDIS = newLinkedHashSet("Luke", "Yoda", "Obiwan");
    static List<String> SITHS = List.of("Sidious", "Vader", "Plagueis");

    @Test
    void conditions() {
        Condition<String> jedi = new Condition<>(JEDIS::contains, "jedi power");

        assertThat("Luke").is(jedi);
        // or assertThat("Luke").has(jedi);
        assertThat("Luky").isNot(jedi);

        // with lists
        assertThat(List.of("Luke", "Yoda")).are(jedi);
        assertThat(List.of("Leia", "Solo")).areNot(jedi);

        // or assertThat(List.of("Luke", "Yoda")).have(jedi);
        // or assertThat(List.of("Leia", "Solo")).doNotHave(jedi);

        assertThat(List.of("Luke", "Yoda", "Leia")).areAtLeast(2, jedi);
        // or assertThat(List.of("Luke", "Yoda", "Leia")).haveAtLeast(2, jedi);

        assertThat(List.of("Luke", "Yoda", "Leia")).areAtMost(2, jedi);
        // or assertThat(List.of("Luke", "Yoda", "Leia")).haveAtMost(2, jedi);

        assertThat(List.of("Luke", "Yoda", "Leia")).areExactly(2, jedi);
        // or assertThat(List.of("Luke", "Yoda", "Leia")).haveExactly(2, jedi);

        // combining conditions

        var sith = new Condition<>(SITHS::contains, "sith");

        assertThat("Vader").is(anyOf(jedi, sith));
        assertThat("Solo").is(allOf(not(jedi), not(sith)));
    }
}