package com.narae.fliwith.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Disability {
    HEARING("청각 장애"),
    VISUAL("시각 장애"),
    PHYSICAL("지체 장애"),
    NONDISABLED("장애 없음"),
    NONE("장애 없음");

    private final String disability;
}
