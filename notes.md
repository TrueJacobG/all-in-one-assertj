[[java]]

---

LINKS:
DOCS: https://assertj.github.io/doc/
JAVA DOC: https://www.javadoc.io/doc/org.assertj/assertj-core/latest/index.html
GITHUB: https://github.com/assertj/doc
STACK OVERFLOW: https://stackoverflow.com/questions/tagged/assertj

My contributions to assertJ/doc - https://github.com/TrueJacobG/doc

---

For version: `3.27.3`

---
## 1 [here](https://assertj.github.io/doc/#assertj-overview)

Overview


---
## 2 AssertJ Core [here](https://assertj.github.io/doc/#assertj-core)

Basic assertJ assertions.

AssertJ supports Java 8 and higher


#### Dependency:

```xml
<dependency>
  <groupId>org.assertj</groupId>
  <artifactId>assertj-core</artifactId>
  <version>3.27.2</version>
  <scope>test</scope>
</dependency>
```

```groovy
testImplementation 'org.assertj:assertj-core:3.27.3'
testImplementation 'org.junit.jupiter:junit-jupiter:5.11.4'
```

If you are using `Spring Boot` then `AssertJ` is included in `spring-boot-starter-test` [here](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)


#### `WithAssertions`

Instead of importing all `AssertJ` methods

```java
import static org.assertj.core.api.Assertions.*;
```

you can implement `WithAssertions`:

Ref: `part2/WithAssertionsTest.class`

```java
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class WithAssertionsTest implements WithAssertions {


  @Test
  public void withAssertions_examples() {

    // assertThat methods come from WithAssertions - no static import needed
    assertThat(1).isEqualTo(1);
  }
}
```


#### `BDDAssertions`

you can also use different style approach

Ref: `part2/BDDAssertionsTest.java`

```java
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
        then(frodo).isIn(fellowshipOfTheRing);
        then(frodo).isIn(sam, frodo);
        then(sauron).isNotIn(fellowshipOfTheRing);
        then(frodo.getAge()).matches(p -> p > 30);
    }
}
```


#### Description

You can change default `as()` describing method with `Assertions.setPrintAssertionsDescription()`. Remember to call `.as() and .describeAs()` BEFORE assertion.

```java
// initialize the description consumer
final StringBuilder descriptionReportBuilder = new StringBuilder(String.format("Assertions:%n"));

Consumer<Description> descriptionConsumer = desc -> descriptionReportBuilder.append(String.format("-- %s%n", desc));

// use the description consumer for any following assertions descriptions.
Assertions.setDescriptionConsumer(descriptionConsumer);

// execute some assertions
TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, Race.HOBBIT);
assertThat(frodo.getName()).as("check name")
                          .isEqualTo("Frodo");
assertThat(frodo.getAge()).as("check age")
                          .isEqualTo(33);

// get the report
var descriptionReport = descriptionReportBuilder.toString();
// result
// Assertions: 
// -- check name
// -- check age
```


#### Custom error messages

```java
var frodo = new TolkienCharacter("Frodo", 33, Race.HOBBIT);
var sam = new TolkienCharacter("Sam", 38, Race.HOBBIT); 

// failing assertion, remember to call withFailMessage/overridingErrorMessage before the assertion! 
assertThat(frodo.getAge()).withFailMessage("should be %s", frodo).isEqualTo(sam);

// IMPORTANT! if you want to build error message ONLY if assertion fails
assertThat(player.isRookie()).overridingErrorMessage(() -> "Expecting Player to be a rookie but was not.").isTrue(); 

assertThat(player.isRookie()).withFailMessage(() -> "Expecting Player to be a rookie but was not.") .isTrue();

// use Supplier instead of just String
```


#### Configuration

Preparing test env with `@AfterEach`, `@AfterAll`, `@BeforeEach`, `@BeforeAll`

Static configuration methods

Ref: `part2/ConfigurationTest.java`

```java
public class ConfigurationTest {

    @BeforeAll
    static void setup() {
        // AllowComparingPrivateFields
        // Globally sets whether the use of private fields is allowed for field/property by field/property comparison.
        // default - true
        Assertions.setAllowComparingPrivateFields(true);

        // AllowExtractingPrivateFields
        // Globally sets whether the AssertJ extracting capability should be allowed to extract private fields.
        // default - true
        Assertions.setAllowExtractingPrivateFields(false);

        // ExtractBareNamePropertyMethods
        // Globally sets whether the AssertJ extracting capability considers bare-named property methods like String name().
        // default - true
        Assertions.setExtractBareNamePropertyMethods(false);

        // LenientDateParsing
        // Specify whether or not date/time parsing is to be lenient for AssertJ default date formats.
        // With lenient parsing, the parser may use heuristics to interpret inputs that do not precisely match this object’s format.
        // With strict parsing, inputs must match this object’s format.
        // default - false
        Assertions.setLenientDateParsing(true);

        // MaxElementsForPrinting
        // In error messages, sets the threshold for how many elements from one iterable/array/map will be included in the description.
        // default - 1000
        Assertions.setMaxElementsForPrinting(100);

        // MaxLengthForSingleLineDescription
        // In error messages, sets the threshold when iterable/array formatting will be on one line (if their String description is less than this parameter) or it will be formatted with one element per line.
        // default - 80
        Assertions.setMaxLengthForSingleLineDescription(250);

        // RemoveAssertJRelatedElementsFromStackTrace
        // Sets whether the elements related to AssertJ are removed from assertion errors stack trace.
        // default - true
        Assertions.setRemoveAssertJRelatedElementsFromStackTrace(true);


        // Representation
        // This property allows you to register a Representation to control the way AssertJ formats the different types displayed in the assertion error messages.
        // Consult the Controlling type formatting chapter for details.
        // default - StandardRepresentation
        var myRepresentation = new StandardRepresentation();
        Assertions.useRepresentation(myRepresentation);

        // Custom DateFormat
        // In addition to the default date formats,
        // you can register some custom ones that AssertJ will use in date assertions (see also Assertions.registerCustomDateFormat).
        var myCustomDateFormat = "yyyy-MM-dd HH:mm:ss";
        Assertions.registerCustomDateFormat(myCustomDateFormat);

        // PrintAssertionsDescription
        // This property allows you to set if assertions descriptions should be printed to the console
        // default - false
        Assertions.setPrintAssertionsDescription(true);
    }

    @BeforeAll
    static void setupConfiguration() {
        // you can also set the configuration using the Configuration class
        var configuration = new Configuration();

        configuration.setBareNamePropertyExtraction(false);
        configuration.setComparingPrivateFields(false);
        configuration.setExtractingPrivateFields(false);
        configuration.setLenientDateParsing(true);
        configuration.setMaxElementsForPrinting(1001);
        configuration.setMaxLengthForSingleLineDescription(81);
        configuration.setRemoveAssertJRelatedElementsFromStackTrace(false);

        configuration.applyAndDisplay();
    }
}
```

You can also create configuration file that will get applied for all tests classes by default - more info [here](https://assertj.github.io/doc/#automatic-configuration-discovery)


#### Type formatting

Assertions error messages use a `Representation` to format the different types involved.

Ways to register a custom `Representation` for assertions:

- Changing the default global `Representation` by calling `Assertions.useRepresentation(myRepresentation)`

- Changing the `Representation` per assertion with `assertThat(actual).withRepresentation(myRepresentation)`

- Globally registering a `Configuration` that specifies the `Representation` to use

more info about creating global configuration: [here](https://assertj.github.io/doc/#assertj-core-fine-grained-representations)

Ref: `part2/CustomRepresentationTest`


#### Common assertions

// TODO
link to javadoc simple assertions: [here](https://www.javadoc.io/static/org.assertj/assertj-core/3.27.2/org/assertj/core/api/AbstractAssert.html#method.summary)
object assertions: [here](https://www.javadoc.io/static/org.assertj/assertj-core/3.27.2/org/assertj/core/api/AbstractObjectAssert.html#method.summary)
string assertions: [here](https://www.javadoc.io/static/org.assertj/assertj-core/3.27.2/org/assertj/core/api/AbstractCharSequenceAssert.html#method.summary)
arrays & iterable: [here](https://www.javadoc.io/doc/org.assertj/assertj-core/latest/org/assertj/core/api/AbstractObjectArrayAssert.html#method.summary)


#### Arrays & Iterable

Ref: `part2/ArraysAndIterablesTest.java`

|                           |                                                                                                                             |                                                                                       |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| Assertion                 | Description                                                                                                                 | Example                                                                               |
| contains                  | Verifies the actual group contains the given values, in any order.                                                          | assertThat(Arrays.asList("a", "b")).contains("b", "a")                                |
| containsOnly              | Verifies the actual group contains only the given values and nothing else, in any order, ignoring duplicates.               | assertThat(Arrays.asList("a", "a", "b")).containsOnly("b", "a")                       |
| containsExactly           | Verifies the actual group contains exactly the given values and nothing else, in order.                                     | assertThat(Arrays.asList("a", "b")).containsExactly("a", "b")                         |
| containsExactlyInAnyOrder | Verifies the actual group contains exactly the given values and nothing else, in any order.                                 | assertThat(Arrays.asList("b", "a")).containsExactlyInAnyOrder("a", "b")               |
| containsSequence          | Verifies the actual group contains the given sequence in the given order, without extra values between the sequence values. | assertThat(Arrays.asList("a", "b", "c", "d")).containsSequence("b", "c")              |
| containsSubsequence       | Verifies the actual group contains the given subsequence in the given order, possibly with other values between them.       | assertThat(Arrays.asList("a", "b", "x", "c", "d")).containsSubsequence("b", "c", "d") |
| containsOnlyOnce          | Verifies the actual group contains the given values only once.                                                              | assertThat(Arrays.asList("a", "b")).containsOnlyOnce("a")                             |
| containsAnyOf             | Verifies the actual group contains at least one of the given values.                                                        | assertThat(Arrays.asList("a", "b")).containsAnyOf("a", "c")                           |

.allSatisfy, .anySatisfy, .noneSatisfy

Asserts that (whole collection) / (at least one element) / (none of elements) meets provided requirements

```java
assertThat(hobbits).allSatisfy(character -> {  
    assertThat(character.getRace().getName()).isEqualTo("Hobbit");  
    assertThat(character.getName()).isNotEqualTo("Sauron");  
});
```


.allMatch, .anyMatch, .noneMatch

Works in very similar way but instead of assertions you have to provided `Predicate`

```java
assertThat(hobbits)
.allMatch(character -> character.getRace().getName().equals("Hobbit"))
.anyMatch(character -> character.getName().contains("pp"))
.noneMatch(character -> character.getName().contains("Her"));
```


.first, .element, .last

Asserts that certain object exist at the position

```java
assertThat(hobbits).first().isEqualTo(frodo);  
assertThat(hobbits).element(1).isEqualTo(sam);  
assertThat(hobbits).last().isEqualTo(sauron);
```


.singleElement

Checks if iterable contains only one element and returns that element for future assertions chaining

```java
Iterable<String> babySimpsons = List.of("Maggie");

assertThat(babySimpsons).singleElement()  
        .isEqualTo("Maggie");
```


filtering

Filter collection and assert that filtered values meet requirements

```java
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
```


##### extracting elements values

```java
// "name" needs to be either a property or a field of the TolkienCharacter class
        // you can make it strongly type by providing class
        assertThat(fellowshipOfTheRing).extracting("name", String.class)
                .contains("Sauron", "Sam", "Frodo")
                .doesNotContain("NotSauron", "SomethingElse");

        // specifying nested field/property is supported
        assertThat(fellowshipOfTheRing).extracting("race.name")
                .contains("Hobbit", "idk");

        // same thing with a lambda which is type safe and refactoring friendly:
        assertThat(fellowshipOfTheRing).extracting(TolkienCharacter::getName)
                .contains("Frodo");
        // same thing with map
        assertThat(fellowshipOfTheRing).map(TolkienCharacter::getName)
                .contains("Frodo");

        // extracting multiple values
        assertThat(fellowshipOfTheRing).extracting("name", "age")
                .contains(tuple("Frodo", 111))
                .doesNotContain(tuple("Sauron", 2115)); // because there is no combination of those values

        // and the same with lambdas
        assertThat(fellowshipOfTheRing).extracting(TolkienCharacter::getName, tolkienCharacter -> tolkienCharacter.getAge())
                .contains(tuple("Frodo", 111));

        // flat extracting
        // with that you don't have to use tuples
        assertThat(fellowshipOfTheRing).flatExtracting("name", "race.name")
                .contains("Frodo", "Hobbit", "Sam", "Hobbit");

        // using custom comparator
        assertThat(fellowshipOfTheRing).usingElementComparator((t1, t2) -> t1.getRace().compareTo(t2.getRace()))
                .contains(sauron);
```



#### Exception assertions

Ref: `part2/ExceptionAssertionsTest.java`

There are various ways for checking the exception message content, you can check the exact message, what it contains, its start, its end, if it matches a regex.

Most of the assertions expecting a `String` can use the `String.format` syntax.

```java
// check the message
        assertThat(throwableWithMessage).hasMessage("wrong amount 123")
                .hasMessage("%s amount %d", "wrong", 123)
                // check start
                .hasMessageStartingWith("wrong")
                .hasMessageStartingWith("%s a", "wrong")
                // check content
                .hasMessageContaining("wrong amount")
                .hasMessageContaining("wrong %s", "amount")
                .hasMessageContainingAll("wrong", "amount")
                // check end
                .hasMessageEndingWith("123")
                .hasMessageEndingWith("amount %s", "123")
                // check with regex
                .hasMessageMatching("wrong amount .*")
                // check does not contain
                .hasMessageNotContaining("right")
                .hasMessageNotContainingAny("right", "price");

        // check the cause
        assertThat(throwable).hasCause(new NullPointerException("1 billion $ mistake"))
                // hasCauseInstanceOf will match inheritance.
                .hasCauseInstanceOf(NullPointerException.class)
                .hasCauseInstanceOf(RuntimeException.class)
                // hasCauseExactlyInstanceOf will match only exact same type
                .hasCauseExactlyInstanceOf(NullPointerException.class);
        // or
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    throw new RuntimeException(new IllegalArgumentException("boom!"));
                })
                .havingCause()
                .withMessage("boom!");


        // and checking the root cause
        assertThat(throwable).hasRootCause(new NullPointerException("1 billion $ mistake"))
                .hasRootCauseMessage("1 billion $ mistake")
                .hasRootCauseMessage("%d %s mistake", 1, "billion $")
                // hasRootCauseInstanceOf will match inheritance
                .hasRootCauseInstanceOf(NullPointerException.class)
                .hasRootCauseInstanceOf(RuntimeException.class)
                // hasRootCauseExactlyInstanceOf will match only exact same type
                .hasRootCauseExactlyInstanceOf(NullPointerException.class);
        // or
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    throw new RuntimeException(new IllegalArgumentException(new NullPointerException("root error")));
                })
                .havingRootCause()
                .withMessage("root error");

        // check if has no cause
        assertThat(throwableWithMessage).hasNoCause();

        // and all of that exists in BDD style ofc: https://assertj.github.io/doc/#assertj-core-exception-assertions-bdd-style


        // no exception has been thrown
        assertThatNoException().isThrownBy(() -> System.out.println("OK"));
        assertThatCode(() -> System.out.println("OK")).doesNotThrowAnyException();
```


#### Field by field recursive comparison

Ref: `part2/FieldByFieldRecursiveComparisonTest.java`

##### Basic usage

```java
// assertion succeeds as the data of both objects are the same.  
assertThat(sherlock).usingRecursiveComparison()  
        .isEqualTo(sherlock2);  
  
// assertion fails as Person equals only compares references.  
assertThat(sherlock).isEqualTo(sherlock2);
```

The comparison is **not symmetrical** since it is **limited to `actual` fields**

The algorithm gathers `actual` fields and then compares them to the corresponding `expected` fields. It is then possible for the `expected` object to have more fields than `actual`, which can be handy when comparing a base type to a subtype with additional fields.

The recursive comparison uses introspection to find out the fields to compare and their values.

It first looks for the object under test fields (skipping any ignored ones as specified in the configuration), then it looks for the same fields in the expected object to compare to.

The next step is resolving the field values using first a getter method (if any) or reading the field value. The getter methods for a field `x` are `getX()` or `isX()` for boolean fields. If you enable bare properties resolution, a method `x()` is also used considered as a valid getter.

Bare name property is enabled by calling `Assertions.setExtractBareNamePropertyMethods(true)
`default - false`

Lastly if the object under test is a map, the recursive comparison tries to resolve the field value by looking it up in the map with `map.get(fieldName)`.

**important**
By default the objects to compare can be of different types but must have the same properties/fields. For example if object under test has a `work` field of type `Address`, the expected object to compare the object under test to must also have one but it can of a different type like `AddressDto`.

It is possible to enforce strict type checking by calling `withStrictTypeChecking()` and make the comparison fail whenever the compared objects or their fields are not compatible. Compatible means that the expected object/field types are the same or a subtype of actual/field types.

strict vs lenient

```java
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
```

##### ignoring fields in the comparison

```java
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
        assertThat(sherlock).usingRecursiveComparison()
                .ignoringFieldsOfTypes(double.class, Address.class)
                .isEqualTo(tallSherlock);
```

##### Using overridden equals

```java
public static class Address2 {
  int number;
  String street;

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof Address)) return false;
    Address castOther = (Address) other;
    return Objects.equals(number, castOther.number);
  }
}

// sherlock - address(221, "Baker Street")
// sherlock2 - address(221, "Other Street")

// assertion succeeds but that's not what we expected since the home.address.street fields differ
// but the equals implementation in Address does not compare them
assertThat(sherlock).usingRecursiveComparison()
                    .usingOverriddenEquals()
                    .isEqualTo(sherlock2);

// to avoid the previous issue, we force a recursive comparison on the Address type
// now this assertion fails as expected since the home.address.street fields differ.
assertThat(sherlock).usingRecursiveComparison()
                    .usingOverriddenEquals()
                    .ignoringOverriddenEqualsForTypes(Address.class)
                    .isEqualTo(sherlock2);
```
##### ignoring all expected null fields

```java
// assertion succeeds as name and home.address.street fields are ignored in the comparison
        assertThat(sherlock).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(noNames);

        // assertion fails as name and home.address.street fields are populated for sherlock but not for noName.
        assertThat(noNames).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isNotEqualTo(sherlock);
```

##### ignoring all empty optionals

```java
 // assertion succeeds as phone is ignored in the comparison
        assertThat(emptyPersonHome).usingRecursiveComparison()
                .ignoringActualEmptyOptionalFields()
                .isEqualTo(sherlocksHome);

        // assertion fails as phone, age, id and height are not ignored and are populated for homerWithDetails but not for homerWithoutDetails.
        assertThat(sherlocksHome).usingRecursiveComparison()
                .ignoringActualEmptyOptionalFields()
                .isNotEqualTo(emptyPersonHome);
```

##### custom comparator for certain fields and types

```java
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
```

##### Overriding error messages for specific Fields or types

```java
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
```

##### Recursive comparison for iterable assertions

```java
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

```

##### Specifying how to introspect the objects to compare

To use your own introspection strategy, you need to:

- implement `RecursiveComparisonIntrospectionStrategy`

- call `withIntrospectionStrategy(myIntrospectionStrategy)` with an instance of your strategy


AssertJ provides a few strategies out of the box:

- `ComparingFields`: introspect fields only (no properties, map keys are not considered as fields)

- `ComparingProperties`: introspect properties only (no fields, map keys are not considered as properties)

- `ComparingSnakeOrCamelCaseFields`: compare fields only, can match camel case fields against snake case ones, ex: `firstName` vs `first_name` which is useful when comparing types with different fields naming conventions

- `ComparingNormalizedFields`: an abstract strategy that compares fields after normalizing them, you just need to implement `normalizeFieldName(String fieldName)`


`ComparingSnakeOrCamelCaseFields` is an example of `ComparingNormalizedFields` that normalizes snake case to camel case.

Here’s an example using `ComparingSnakeOrCamelCaseFields` where we compare `Author`/`Book` against `AuthorDto`/`BookDto`, `Author`/`Book` follow the regular camel case field naming convention while the dto classes follow the snake case naming convention.

The recursive comparison would fail comparing the `Author`/`Book` fields against `AuthorDto`/`BookDto` ones, it would not know to match `Author.firstName` against `AuthorDto.first_name` for example but with `ComparingSnakeOrCamelCaseFields` it will know how to match these fields.

#### Recursive assertions

Ref: `part2/RecursiveAssertionsTest.java`

The recursive assertion `allFieldsSatisfy` lets you verify a `Predicate` is met for all the fields of the object under test graph recursively (but not the object itself).

For example if the object under test is an instance of class A, A has a B field and B a C field then `allFieldsSatisfy` checks A’s B field and B’s C field and all C’s fields.

The recursive assertion provides these methods to exclude fields, the predicate won’t be applied on the excluded fields:

- `ignoringFields(String…​fieldsToIgnore)` - the assertion ignores the specified fields in the object under test

- `ignoringFieldsMatchingRegexes(String…​regexes)` - the assertion ignores the fields matching the specified regexes in the object under test

- `ignoringFieldsOfTypes(Class<?>…​typesToIgnore)` - the assertion ignores the object under test fields of the given types

- `ignoringPrimitiveFields()` - avoid running the assertion on primitive fields


#### Soft Assertions

With soft assertions AssertJ collects all assertion errors instead of stopping at the first one.

Ref: `part2/SoftAssertionsTest.java`

```java
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
```


#### Assumptions

If assumption fails, than all of the rest assertions will get ignored (not performed)

```java
// will fail
assumeThat(frodo.getRace()).isEqualTo(ORC);
// won't get perform
assertThat(fellowshipOfTheRing).contains(sauron);
```

### 2.6 Extending assertions


```java
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
```


### 2.7 Migration to AssertJ

You can use script https://github.com/assertj/assertj/blob/main/scripts/convert-junit5-assertions-to-assertj.sh to migrate from Junit 5 to AssertJ assertions in your project.

usage:

```bash
cd ./src/test/java ./convert-junit-assertions-to-assertj.sh
```


### 2.8 AssertJ Examples

Sample projects: https://github.com/assertj/assertj-examples


## 3 AssertJ Guava

AssertJ has full support of google's guava support so you can use preferred types and APIs.


## 4 AssertJ Joda

Time module used for time related tests (for org.joda.time module)

```xml
<dependency>
  <groupId>org.assertj</groupId>
  <artifactId>assertj-joda-time</artifactId>
  <version>2.2.0</version>
  <scope>test</scope>
</dependency>
```


## 5 AssertJ Neo4j

Official docs has empty section for [Neo4j](https://assertj.github.io/doc/#assertj-neo4j) support so I am linking prev version of docs for that -> [here](https://joel-costigliola.github.io/assertj/assertj-neo4j.html)


## 6 AssertJ Db

AssertJ Db provides assertions to test data directly in db.


## 7 AssertJ Swing

Official docs has empty section for [Swing](https://assertj.github.io/doc/#assertj-swing) support so I am linking prev version of docs for that -> [here](https://joel-costigliola.github.io/assertj/assertj-swing-news.html)