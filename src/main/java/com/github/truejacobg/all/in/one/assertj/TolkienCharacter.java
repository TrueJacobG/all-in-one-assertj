package com.github.truejacobg.all.in.one.assertj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TolkienCharacter {
    private String name;
    private Integer age;
    private Race race;
}
