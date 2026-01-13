package com.business.kafka.systems.enums;

public interface DefaultEnums {

    String getCode();

    String getDesc();

    default boolean isTrue(final String code) {
        if (code == null || code == "") {
            return false;
        }

        return this.getCode().equals(code);
    }
}
