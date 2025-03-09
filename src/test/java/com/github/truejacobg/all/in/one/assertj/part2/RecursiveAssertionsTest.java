package com.github.truejacobg.all.in.one.assertj.part2;

import com.github.truejacobg.all.in.one.assertj.Address;
import com.github.truejacobg.all.in.one.assertj.Home;
import com.github.truejacobg.all.in.one.assertj.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class RecursiveAssertionsTest {

    @Test
    void allFieldsSatisfy() {
        var sherlock = Person.builder()
                .name("Sherlock")
                .height(1.80)
                .home(Home.builder()
                        .address(Address.builder()
                                .street("Baker Street")
                                .number(221)
                                .build())
                        .ownedSince(LocalDate.of(1881, 12, 3))
                        .build())
                .build();

        // assertion succeeds
        assertThat(sherlock).usingRecursiveAssertion()
                .allFieldsSatisfy(Objects::nonNull);
    }
}
