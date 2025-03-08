package com.github.truejacobg.all.in.one.assertj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalLong;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Home {
    @Builder.Default
    private Address address = new Address();
    private LocalDate ownedSince;
    private Optional<String> phoneNumber;
    private OptionalLong size;
}