package com.tedterrobias.platform.controller;

import com.tedterrobias.platform.enumeration.VehicleEnum;
import com.tedterrobias.platform.model.ParkingEntries;

import com.tedterrobias.platform.model.VacantParking;
import com.tedterrobias.platform.service.ParkingEntriesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/parking")
public class ParkingEntriesController
{

    @Autowired
    private ParkingEntriesService parkingEntriesService;

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "find parking by ID")
    public ParkingEntries findParkingById(@RequestParam String id)
    {
        return parkingEntriesService.findParkingEntryById(id);
    }

    @GetMapping(path = "available", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get current available parking slots for two-wheeled and four-wheeled vehicles")
    public VacantParking getAvailableParking() {
        return parkingEntriesService.calculateAvailableParkingSpaces();
    }

    @PostMapping(path = "start", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "start parking by vehicle type")
    public void startParking(@RequestParam("vehicleType") VehicleEnum vehicleType,
                             HttpServletResponse response)
    {
        parkingEntriesService.startParkingEntry(vehicleType, response);
    }

    @PostMapping(path = "end", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "end active parking by ID")
    public ParkingEntries endParking(@RequestParam("id") String id)
    {
        return parkingEntriesService.endParkingEntry(id);
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update parking entry. under development")
    public ParkingEntries updateParking(@RequestBody ParkingEntries parkingEntry)
    {
        return parkingEntriesService.updateParkingEntry(parkingEntry);
    }
}
