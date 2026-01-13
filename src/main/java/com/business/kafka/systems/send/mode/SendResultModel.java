package com.business.kafka.systems.send.mode;

public record SendResultModel<T>(ResultStatus status,
                                 T body) {

    public enum ResultStatus {
        SUCCESS,
        FAIL
    }


    public static SendResultModel<Void> success() {
        return new SendResultModel<>(ResultStatus.SUCCESS, null);
    }

    public static <T> SendResultModel<T> success(T body) {
        return new SendResultModel<>(ResultStatus.SUCCESS, body);
    }


    public static SendResultModel<Void> fail() {
        return new SendResultModel<>(ResultStatus.FAIL, null);
    }

    public static <T> SendResultModel<T> fail(T body) {
        return new SendResultModel<>(ResultStatus.FAIL, body);
    }
}

