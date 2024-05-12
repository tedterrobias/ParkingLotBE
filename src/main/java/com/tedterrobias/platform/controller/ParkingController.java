package com.tedterrobias.platform.controller;

import com.tedterrobias.platform.enumeration.VehicleEnum;
import com.tedterrobias.platform.model.Parkings;
import com.tedterrobias.platform.model.VacantParking;
import com.tedterrobias.platform.service.ParkingService;
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
    public Parkings findParkingById(@RequestParam String id) {
        return parkingService.findParkingEntryById(id);
    }

    @GetMapping(path = "available")
    public VacantParking getAvailableParking() {
        return parkingService.calculateAvailableParkingSpaces();
    }

    @PostMapping(path = "start", produces = MediaType.APPLICATION_JSON_VALUE)
    public Parkings startParking(@RequestParam("vehicleType") VehicleEnum vehicleType) {
        //should only include vehicle type
        return parkingService.startParkingEntry(vehicleType);
    }

    @PostMapping(path = "end", produces = MediaType.APPLICATION_JSON_VALUE)
    public Parkings endParking(@RequestParam("id") String id) {
        //should only include vehicle type
        return parkingService.endParkingEntry(id);
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Parkings updateParking(@RequestBody Parkings parkingEntry) {
        return parkingService.updateParkingEntry(parkingEntry);
    }
}
