package com.tedterrobias.platform.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingEntriesTicket
{
    private String id;
    private String vehicle;
    private LocalDateTime startParkTime;

    private BigDecimal amount;

    private LocalDateTime endParkTime;

    public ParkingEntriesTicket() { }

    public ParkingEntriesTicket(String id, String vehicle, LocalDateTime startParkTime, BigDecimal amount,
                                LocalDateTime endParkTime)
    {
        this.id = id;
        this.vehicle = vehicle;
        this.startParkTime = startParkTime;
        this.amount = amount;
        this.endParkTime = endParkTime;
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

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public LocalDateTime getEndParkTime()
    {
        return endParkTime;
    }

    public void setEndParkTime(LocalDateTime endParkTime)
    {
        this.endParkTime = endParkTime;
    }
}
