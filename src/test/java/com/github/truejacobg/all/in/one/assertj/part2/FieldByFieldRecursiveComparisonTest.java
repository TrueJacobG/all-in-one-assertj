package com.github.truejacobg.all.in.one.assertj.part2;

import com.github.truejacobg.all.in.one.assertj.Address;
import com.github.truejacobg.all.in.one.assertj.AlmostHome;
import com.github.truejacobg.all.in.one.assertj.AlmostPerson;
import com.github.truejacobg.all.in.one.assertj.Home;
import com.github.truejacobg.all.in.one.assertj.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldByFieldRecursiveComparisonTest {

    @Test
    void basicUsage() {
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

        var sherlock2 = Person.builder()
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

        // assertion succeeds as the data of both objects are the same.
        assertThat(sherlock).usingRecursiveComparison()
                .isEqualTo(sherlock2);

        // assertion fails as Person equals only compares references.
        assertThat(sherlock).isEqualTo(sherlock2);
    }


    @Test
    void strictOrLenient() {
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

        var sherlockClone = Person.builder()
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

        // assertion succeeds as sherlock and sherlockClone have the same data and types
        assertThat(sherlock).usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(sherlockClone);

        var almostSherlock = AlmostPerson.builder()
                .name("Sherlock")
                .height(1.80)
                .home(AlmostHome.builder()
                        .address(Address.builder()
                                .street("Baker Street")
                                .number(221)
                                .build())
                        .ownedSince(LocalDate.of(1881, 12, 3))
                        .build())
                .build();

        // without strict checking
        assertThat(sherlock).usingRecursiveComparison()
                .isEqualTo(almostSherlock);
        // strict will ofc fail
        assertThat(sherlock).usingRecursiveComparison()
                .withStrictTypeChecking()
                .isNotEqualTo(almostSherlock);
    }
}
