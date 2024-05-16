package com.tedterrobias.platform.model.mapper;

import com.tedterrobias.platform.model.ParkingEntries;
import com.tedterrobias.platform.model.ParkingEntriesTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ParkingEntriesTicketMapper {

    @Mapping(target = "vehicle", expression = "java(parkingEntries.getVehicle().getVehicleType())")
    ParkingEntriesTicket parkingEntriesToParkingEntriesTicket(ParkingEntries parkingEntries);
}
