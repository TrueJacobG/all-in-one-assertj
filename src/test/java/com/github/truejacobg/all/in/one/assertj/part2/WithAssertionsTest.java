package com.github.truejacobg.all.in.one.assertj.part2;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class WithAssertionsTest implements WithAssertions {

    @Test
    public void withAssertions_examples() {

        // assertThat methods come from WithAssertions - no static import needed
        assertThat(1).isEqualTo(1);
    }
}