package com.tedterrobias.platform.model;

import com.tedterrobias.platform.enumeration.VehicleEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "parkings")
public class Parkings
{
    @Id
    private String id;
    private VehicleEnum vehicle;
    private BigDecimal amount;
    private LocalDateTime startParkTime;
    private LocalDateTime endParkTime;
    private boolean isActive;

    public Parkings() {
    }

    public Parkings(String id, VehicleEnum vehicle, BigDecimal amount,
                    LocalDateTime startParkTime, LocalDateTime endParkTime, boolean isActive) {
        this.id = id;
        this.vehicle = vehicle;
        this.amount = amount;
        this.startParkTime = startParkTime;
        this.endParkTime = endParkTime;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VehicleEnum getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEnum vehicle) {
        this.vehicle = vehicle;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getStartParkTime() {
        return startParkTime;
    }

    public void setStartParkTime(LocalDateTime startParkTime) {
        this.startParkTime = startParkTime;
    }

    public LocalDateTime getEndParkTime() {
        return endParkTime;
    }

    public void setEndParkTime(LocalDateTime endParkTime) {
        this.endParkTime = endParkTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Parkings{" +
                ", vehicle=" + vehicle +
                ", amount=" + amount +
                ", startParkTime=" + startParkTime +
                ", endParkTime=" + endParkTime +
                ", isActive=" + isActive +
                '}';
    }
}
