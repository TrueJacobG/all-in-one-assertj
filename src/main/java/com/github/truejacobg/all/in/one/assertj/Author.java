package com.github.truejacobg.all.in.one.assertj;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    String firstName;
    String lastName;
    int age;
    String profileURL;
}
