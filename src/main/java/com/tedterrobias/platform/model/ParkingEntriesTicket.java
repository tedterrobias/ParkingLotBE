package com.tedterrobias.platform.model;

import java.time.LocalDateTime;

public class ParkingEntriesTicket
{
    private String id;
    private String vehicle;
    private LocalDateTime startParkTime;

    public ParkingEntriesTicket() { }

    public ParkingEntriesTicket(String id, String vehicle, LocalDateTime startParkTime) {
        this.id = id;
        this.vehicle = vehicle;
        this.startParkTime = startParkTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getStartParkTime() {
        return startParkTime;
    }

    public void setStartParkTime(LocalDateTime startParkTime) {
        this.startParkTime = startParkTime;
    }


}
