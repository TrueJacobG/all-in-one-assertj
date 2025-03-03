package com.github.truejacobg.all.in.one.assertj.part2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class ExceptionAssertionsTest {

    @Test
    void exceptions() {
        var throwableWithMessage = new IllegalArgumentException("wrong amount 123");
        var throwable = new Throwable(new NullPointerException("1 billion $ mistake"));

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
    }
}
