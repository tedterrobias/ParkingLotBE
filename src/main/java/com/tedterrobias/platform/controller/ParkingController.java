package com.tedterrobias.platform.controller;

import com.tedterrobias.platform.enumeration.VehicleEnum;
import com.tedterrobias.platform.model.Parkings;
import com.tedterrobias.platform.model.VacantParking;
import com.tedterrobias.platform.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/parking")
public class ParkingController
{

    @Autowired
    private ParkingService parkingService;

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "find parking by ID")
    public Parkings findParkingById(@RequestParam String id) {
        return parkingService.findParkingEntryById(id);
    }

    @GetMapping(path = "available")
    @Operation(summary = "get current available parking slots for two-wheeled and four-wheeled vehicles")
    public VacantParking getAvailableParking() {
        return parkingService.calculateAvailableParkingSpaces();
    }

    @PostMapping(path = "start", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "start parking by vehicle type")
    public Parkings startParking(@RequestParam("vehicleType") VehicleEnum vehicleType) {
        return parkingService.startParkingEntry(vehicleType);
    }

    @PostMapping(path = "end", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "end active parking by ID")
    public Parkings endParking(@RequestParam("id") String id) {
        return parkingService.endParkingEntry(id);
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update parking entry. under development")
    public Parkings updateParking(@RequestBody Parkings parkingEntry) {
        return parkingService.updateParkingEntry(parkingEntry);
    }
}
