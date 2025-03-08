package com.github.truejacobg.all.in.one.assertj.part2;

import com.github.truejacobg.all.in.one.assertj.Address;
import com.github.truejacobg.all.in.one.assertj.AlmostHome;
import com.github.truejacobg.all.in.one.assertj.AlmostPerson;
import com.github.truejacobg.all.in.one.assertj.Doctor;
import com.github.truejacobg.all.in.one.assertj.Home;
import com.github.truejacobg.all.in.one.assertj.Human;
import com.github.truejacobg.all.in.one.assertj.Person;
import com.github.truejacobg.all.in.one.assertj.TolkienCharacter;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.BiPredicate;

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

    @Test
    void ignoringFields() {
        var sherlock = Person.builder()
                .name("Sherlock")
                .height(1.80)
                .home(Home.builder()
                        .address(Address.builder()
                                .number(221)
                                .street("Baker Street")
                                .build())
                        .build())
                .build();

        var moriarty = Person.builder()
                .name("Moriarty")
                .height(1.80)
                .home(Home.builder()
                        .address(Address.builder()
                                .number(221)
                                .street("Other Street")
                                .build())
                        .build())
                .build();

        // assertion succeeds as name and home.address.street fields are ignored in the comparison
        assertThat(sherlock).usingRecursiveComparison()
                .ignoringFields("name", "home.address.street")
                .isEqualTo(moriarty);

        // assertion succeeds as once a field is ignored, its subfields are too
        assertThat(sherlock).usingRecursiveComparison()
                .ignoringFields("name", "home")
                .isEqualTo(moriarty);

        // ignoring fields matching regexes - name and home match .*me
        assertThat(sherlock).usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*me")
                .isEqualTo(moriarty);

        // ignoring null fields
        var nullishSherlock = Person.builder()
                .name(null)
                .height(1.80)
                .home(Home.builder()
                        .address(Address.builder()
                                .number(221)
                                .street(null)
                                .build())
                        .build())
                .build();
        assertThat(nullishSherlock).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(moriarty);

        // ignore height
        var tallSherlock = Person.builder()
                .name("Sherlock")
                .height(2.20)
                .home(Home.builder()
                        .address(Address.builder()
                                .number(221)
                                .street("Baker Street")
                                .build())
                        .build())
                .build();
        assertThat(sherlock).usingRecursiveComparison()
                .ignoringFieldsOfTypes(double.class, Address.class)
                .isEqualTo(tallSherlock);
    }

    class Address2 {
        int number;
        String street;

        @Override
        public boolean equals(final Object other) {
            if (!(other instanceof Address2 castOther)) return false;
            return Objects.equals(number, castOther.number);
        }
    }

    @Test
    void usingOverriddenEquals() {
        // just mentioned in notes
    }

    @Test
    void ignoringAllExpectedValues() {
        var sherlock = Person.builder()
                .name("Sherlock")
                .height(1.80)
                .home(Home.builder()
                        .address(Address.builder()
                                .number(221)
                                .street("Baker Street")
                                .build())
                        .build())
                .build();

        var noNames = Person.builder()
                .name(null)
                .height(1.80)
                .home(Home.builder()
                        .address(Address.builder()
                                .number(221)
                                .street(null)
                                .build())
                        .build())
                .build();

        // assertion succeeds as name and home.address.street fields are ignored in the comparison
        assertThat(sherlock).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(noNames);

        // assertion fails as name and home.address.street fields are populated for sherlock but not for noName.
        assertThat(noNames).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isNotEqualTo(sherlock);
    }

    @Test
    void ignoringAllEmptyOptionals() {
        var sherlocksHome = Home.builder()
                .address(Address.builder()
                        .number(221)
                        .street("Baker Street")
                        .build())
                .phoneNumber(Optional.of("123123123"))
                .size(OptionalLong.of(89L))
                .build();

        var emptyPersonHome = Home.builder()
                .address(Address.builder()
                        .number(221)
                        .street("Baker Street")
                        .build())
                .phoneNumber(Optional.empty())
                .size(OptionalLong.empty())
                .build();

        // assertion succeeds as phone is ignored in the comparison
        assertThat(emptyPersonHome).usingRecursiveComparison()
                .ignoringActualEmptyOptionalFields()
                .isEqualTo(sherlocksHome);

        // assertion fails as phone, age, id and height are not ignored and are populated for homerWithDetails but not for homerWithoutDetails.
        assertThat(sherlocksHome).usingRecursiveComparison()
                .ignoringActualEmptyOptionalFields()
                .isNotEqualTo(emptyPersonHome);
    }

    @Test
    void customComparatorForCertainFieldsAndTypes() {
        var frodo = TolkienCharacter.builder().name("Frodo").age(102).build();
        var olderFrodo = TolkienCharacter.builder().name("Frodo").age(103).build();
        var reallyOldFrodo = TolkienCharacter.builder().name("Frodo").age(907).build();

        // if age diff is less than 10 than it is ok
        BiPredicate<Integer, Integer> closeEnough = (d1, d2) -> Math.abs(d1 - d2) <= 10;

        // assertion succeeds
        assertThat(frodo).usingRecursiveComparison()
                .withEqualsForFields(closeEnough, "age")
                .isEqualTo(olderFrodo);

        assertThat(frodo).usingRecursiveComparison()
                .withEqualsForType(closeEnough, Integer.class)
                .isEqualTo(olderFrodo);

        // assertions fail
        assertThat(frodo).usingRecursiveComparison()
                .withEqualsForFields(closeEnough, "age")
                .isNotEqualTo(reallyOldFrodo);

        assertThat(frodo).usingRecursiveComparison()
                .withEqualsForType(closeEnough, Integer.class)
                .isNotEqualTo(reallyOldFrodo);
    }

    @Test
    void overridingErrorMessagesForSpecificFieldsOrTypes() {
        var frodo = TolkienCharacter.builder().name("Frodo").age(102).build();
        var olderFrodo = TolkienCharacter.builder().name("Frodo").age(307).build();

        var message = "Field { age } is different";

//        assertThat(frodo).usingRecursiveComparison()
//                .withErrorMessageForFields(message, "age")
//                .isEqualTo(olderFrodo);

//        assertThat(frodo).usingRecursiveComparison()
//                .withErrorMessageForType(message, Integer.class)
//                .isEqualTo(olderFrodo);

//        Expecting actual:
//        TolkienCharacter(name=Frodo, age=102, race=null)
//        to be equal to:
//        TolkienCharacter(name=Frodo, age=307, race=null)
//        when recursively comparing field by field, but found the following difference:
//
//        message:
//        Field { age } is different
    }

    @Test
    void recursiveComparisonForIterableAssertions() {
        // TBBT reference O_o
        var drSheldon = new Doctor("Sheldon Cooper", true);
        var drLeonard = new Doctor("Leonard Hofstadter", true);
        var drRaj = new Doctor("Raj Koothrappali", true);

        var sheldon = new Human("Sheldon Cooper", false);
        var leonard = new Human("Leonard Hofstadter", false);
        var raj = new Human("Raj Koothrappali", false);
        var howard = new Human("Howard Wolowitz", false);

        List<Object> doctors = List.of(drSheldon, drLeonard, drRaj);

        var configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("hasPhd")
                .build();

        // assertion succeeds as both lists contains equivalent items in order.
        assertThat(doctors).usingRecursiveFieldByFieldElementComparator(configuration)
                .contains(sheldon);

        // assertion fails because leonard names are different.
        leonard.setName("Leonard Ofstater");
        assertThat(doctors).usingRecursiveFieldByFieldElementComparator(configuration)
                .doesNotContain(leonard);

        // assertion fails because howard is missing and leonard is not expected.
        assertThat(doctors).usingRecursiveFieldByFieldElementComparator(configuration)
                .doesNotContain(howard);
    }
}
