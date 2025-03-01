package com.github.truejacobg.all.in.one.assertj.part2;

import org.assertj.core.api.Assertions;
import org.assertj.core.configuration.Configuration;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.jupiter.api.BeforeAll;

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
