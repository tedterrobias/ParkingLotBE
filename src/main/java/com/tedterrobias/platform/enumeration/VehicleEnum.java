package com.tedterrobias.platform.enumeration;

public enum VehicleEnum {
    TWO_WHEELED(600, "four-wheeled"),
    FOUR_WHEELED(400, "two-wheeled");

    private final int totalSpace;
    private final String vehicleType;

    VehicleEnum(int totalSpace, String vehicleType)
    {
        this.totalSpace = totalSpace;
        this.vehicleType = vehicleType;
    }

    public int getTotalSpace()
    {
        return totalSpace;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}
