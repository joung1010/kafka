package com.business.kafka.common.enums;

import com.business.kafka.systems.enums.DefaultEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SendTypeEnums implements DefaultEnums {

    EMAIL("E", "이메일");

    private final String code;
    private final String desc;
}
