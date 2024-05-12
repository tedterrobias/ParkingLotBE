package com.tedterrobias.platform.enumeration;

public enum PricingEnum {
    TWO_WHEELED_INITIAL_RATE(30),
    FOUR_WHEELED_INITIAL_RATE(50),
    TWO_WHEELED_SUCCEEDING_RATE(10),
    FOUR_WHEELED_SUCCEEDING_RATE(15),
    INITIAL_HOURS(3);

    private final int value;

    PricingEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
