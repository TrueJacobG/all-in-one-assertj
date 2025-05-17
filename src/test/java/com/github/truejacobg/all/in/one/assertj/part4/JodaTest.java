package com.github.truejacobg.all.in.one.assertj.part4;

import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.jodatime.api.Assertions.assertThat;

public class JodaTest {

    @Test
    void jodaTest() {
        var localDateTime = LocalDateTime.now();
        var firstLocalDateTime = LocalDateTime.now().plusDays(1);
        var secondLocalDateTime = LocalDateTime.now().minusDays(1);


        assertThat(localDateTime).isBefore(firstLocalDateTime);
        assertThat(localDateTime).isAfterOrEqualTo(secondLocalDateTime);

        assertThat(new LocalDateTime("2000-01-01")).isEqualTo("2000-01-01");


        var dateTime1 = new LocalDateTime(2000, 1, 1, 23, 50, 0, 0);
        var dateTime2 = new LocalDateTime(2000, 1, 1, 23, 50, 10, 456);

        assertThat(dateTime1).isEqualToIgnoringSeconds(dateTime2);
    }
}
