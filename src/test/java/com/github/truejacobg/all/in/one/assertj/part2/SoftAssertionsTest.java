package com.github.truejacobg.all.in.one.assertj.part2;

import com.github.truejacobg.all.in.one.assertj.Race;
import com.github.truejacobg.all.in.one.assertj.TolkienCharacter;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AutoCloseableBDDSoftAssertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.lang.String.format;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(SoftAssertionsExtension.class)
public class SoftAssertionsTest {

    @InjectSoftAssertions
    private SoftAssertions softlyField;

    @Test
    void all(SoftAssertions softlyParameter) {
        // calling .assertAll()

        var softlyAssertAll = new SoftAssertions();

        softlyAssertAll.assertThat("George Martin").as("great authors").hasSameSizeAs("JRR Tolkien12");
        softlyAssertAll.assertThat(42).as("response to Everything").isLessThan(100);
        softlyAssertAll.assertThat("Gandalf").isNotEqualTo("Sauron");

        softlyAssertAll.assertAll();


        // bdd approach
        var softlyBDD = new BDDSoftAssertions();

        softlyBDD.then("George Martin").as("great authors").hasSameSizeAs("JRR Tolkien12");
        softlyBDD.then(42).as("response to Everything").isLessThan(100);
        softlyBDD.then("Gandalf").isNotEqualTo("Sauron");

        softlyBDD.assertAll();


        // junit 5 approach
        // you don't have to call .assertAll()

        // junit 5 approach - field
        softlyField.assertThat("Mi Bulls").startsWith("Mi")
                .contains("Bulls");

        // junit 5 approach - parameter
        softlyParameter.assertThat("Mi Bulls").startsWith("Mi")
                .contains("Bulls");


        // autocloseable
        try (var softlyAutocloseable = new AutoCloseableSoftAssertions()) {
            softlyAutocloseable.assertThat("George Martin").as("great authors").hasSameSizeAs("JRR Tolkien12");
            softlyAutocloseable.assertThat(42).as("response to Everything").isLessThan(100);
            softlyAutocloseable.assertThat("Gandalf").isNotEqualTo("Sauron");
        }

        // autocloseable - bdd style
        try (var softlyAutocloseable = new AutoCloseableBDDSoftAssertions()) {
            softlyAutocloseable.then("George Martin").as("great authors").hasSameSizeAs("JRR Tolkien12");
            softlyAutocloseable.then(42).as("response to Everything").isLessThan(100);
            softlyAutocloseable.then("Gandalf").isNotEqualTo("Sauron");
        }


        // assertSoftly
        assertSoftly(softlyAssertSoftly -> {
            softlyAssertSoftly.assertThat("George Martin").as("great authors").hasSameSizeAs("JRR Tolkien12");
            softlyAssertSoftly.assertThat(42).as("response to Everything").isLessThan(100);
            softlyAssertSoftly.assertThat("Gandalf").isNotEqualTo("Sauron");
        });


        // change soft assertions report
        var reportBuilder = new StringBuilder(format("Assertions report:%n"));
        softlyAssertAll.setAfterAssertionErrorCollected(error ->
                reportBuilder.append(format("------------------%n%s%n", error.getMessage())));


        // custom soft assertion
        var frodo = new TolkienCharacter("Frodo", 102, new Race("HOBBIT"));
        FantasySoftAssertions.assertThatCustom(frodo).hasRace("HOBBIT");
    }


    public static class FantasySoftAssertions extends SoftAssertions {

        public static TolkienCharacterAssert assertThatCustom(TolkienCharacter actual) {
            return new TolkienCharacterAssert(actual);
        }
    }

    public static class TolkienCharacterAssert extends AbstractAssert<TolkienCharacterAssert, TolkienCharacter> {

        public TolkienCharacterAssert(TolkienCharacter actual) {
            super(actual, TolkienCharacterAssert.class);
        }

        public void hasRace(String expectedRace) {
            isNotNull();

            if (!actual.getRace().getName().equals(expectedRace)) {
                failWithMessage("Expected character's race to be <%s> but was <%s>", expectedRace, actual.getRace());
            }
        }
    }
}
