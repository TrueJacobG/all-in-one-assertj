package com.github.truejacobg.all.in.one.assertj.part2;

import org.assertj.core.presentation.StandardRepresentation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomRepresentationTest {
    private class Example {
    }

    public class CustomRepresentation extends StandardRepresentation {

        @Override
        public String fallbackToStringOf(Object o) {
            if (o instanceof Example) return "Example";
            return super.fallbackToStringOf(o);
        }

        @Override
        protected String toStringOf(String str) {
            return "$" + str + "$";
        }
    }

    @Test
    void exampleUsage() {
        assertThat(new Example())
                .withRepresentation(new CustomRepresentation())
                .isNotNull();
        // because of the line 16
        // it returns "Example"


        assertThat("Hello")
                .withRepresentation(new CustomRepresentation())
                .isEqualTo("Hello");
        // output:
        // Expected :$Hello$
        // Actual   :$Hello$
    }
}
