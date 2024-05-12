package com.tedterrobias.platform.model;

public class VacantParking
{
    private long availableTwoWheels;
    private long availableFourWheels;

    public VacantParking() {
    }

    public VacantParking(Integer availableTwoWheels, Integer availableFourWheels) {
        this.availableTwoWheels = availableTwoWheels;
        this.availableFourWheels = availableFourWheels;
    }

    public long getAvailableTwoWheels() {
        return availableTwoWheels;
    }

    public void setAvailableTwoWheels(long availableTwoWheels) {
        this.availableTwoWheels = availableTwoWheels;
    }

    public long getAvailableFourWheels() {
        return availableFourWheels;
    }

    public void setAvailableFourWheels(long availableFourWheels) {
        this.availableFourWheels = availableFourWheels;
    }

    @Override
    public String toString() {
        return "VacantParking{" +
                "availableTwoWheels=" + availableTwoWheels +
                ", availableFourWheels=" + availableFourWheels +
                '}';
    }
}
