package com.github.truejacobg.all.in.one.assertj;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Race {
    private String name;

    public int compareTo(Race race) {
        return race.getName().length() - name.length();
    }
}
