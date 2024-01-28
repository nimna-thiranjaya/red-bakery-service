package com.redbakery.redbakeryservice.common;


public enum WellKnownStatus {
    ACTIVE(1),
    INACTIVE(2),
    PENDING(6),
    CONFIRMED(3),
    REJECTED(4),
    DELETED(5);

    private final int value;

    WellKnownStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
