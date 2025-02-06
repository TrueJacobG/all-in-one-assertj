package com.github.truejacobg.all.in.one.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTest {

    @Test
    void statusTest() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    void testJUnit() {
        assertEquals(1, 1);
    }
}
