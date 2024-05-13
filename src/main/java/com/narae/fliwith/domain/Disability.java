package com.narae.fliwith.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Disability {
    HEARING("hearing"),
    VISUAL("visual"),
    PHYSICAL("physical"),
    NONDISABLED("non-disabled"),
    NONE("none");

    private final String disability;
}
