package com.tedterrobias.platform.enumeration;

public enum VehicleEnum {
    TWO_WHEELED(600),
    FOUR_WHEELED(400);

    private final int totalSpace;

    VehicleEnum(int totalSpace)
    {
        this.totalSpace = totalSpace;
    }

    public int getTotalSpace()
    {
        return totalSpace;
    }
}
