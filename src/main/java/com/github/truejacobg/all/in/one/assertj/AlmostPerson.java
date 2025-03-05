package com.github.truejacobg.all.in.one.assertj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmostPerson {
    private String name;
    private double height;
    @Builder.Default
    private AlmostHome home = new AlmostHome();
}
