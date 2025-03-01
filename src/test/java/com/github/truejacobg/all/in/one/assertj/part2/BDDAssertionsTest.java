package com.github.truejacobg.all.in.one.assertj.part2;

import com.github.truejacobg.all.in.one.assertj.Race;
import com.github.truejacobg.all.in.one.assertj.TolkienCharacter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class BDDAssertionsTest {

    @Test
    public void withAssertions_examples() {
        var frodo = new TolkienCharacter("Frodo", 111, new Race("Hobbit"));
        var sam = new TolkienCharacter("Sam", 93, new Race("Hobbit"));
        var sauron = new TolkienCharacter("Sauron", 400, new Race("idk"));
        var fellowshipOfTheRing = List.of(frodo, sam);

        then(frodo.getAge()).isEqualTo(111);
        then(frodo.getName()).isEqualTo("Frodo").isNotEqualTo("Frodon");

        then(frodo).isIn(fellowshipOfTheRing);
        then(frodo).isIn(sam, frodo);
        then(sauron).isNotIn(fellowshipOfTheRing);

        then(frodo).matches(p -> p.getAge() > 30 && p.getRace().getName().equals("Hobbit"));
        then(frodo.getAge()).matches(p -> p > 30);
    }
}