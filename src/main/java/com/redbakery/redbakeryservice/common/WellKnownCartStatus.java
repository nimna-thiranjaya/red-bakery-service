package com.redbakery.redbakeryservice.common;

public enum WellKnownCartStatus {
    PENDING(6),
    CONFIRMED(3),
    DELETED(5);

    private final int value;

    WellKnownCartStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
