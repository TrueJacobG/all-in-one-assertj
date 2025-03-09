package com.github.truejacobg.all.in.one.assertj;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {
    String first_name;
    String last_name;
    int _age;
    String profile_url;
}
