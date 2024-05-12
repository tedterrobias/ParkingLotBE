package com.tedterrobias.platform.service;

import com.tedterrobias.platform.enumeration.PricingEnum;
import com.tedterrobias.platform.enumeration.VehicleEnum;
import com.tedterrobias.platform.model.Parkings;
import com.tedterrobias.platform.model.VacantParking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ParkingService
{
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(ParkingService.class);

    public ParkingService() { }

    public ParkingService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Parkings startParkingEntry(VehicleEnum vehicleType)
    {
        Parkings parkingEntry = new Parkings();
        parkingEntry.setVehicle(vehicleType);
        parkingEntry.setStartParkTime(LocalDateTime.now());
        parkingEntry.setAmount(BigDecimal.ZERO);
        parkingEntry.setActive(true);

        return mongoTemplate.save(parkingEntry);
    }

    public Parkings endParkingEntry(String id)
    {
        Parkings parkingEntry = findParkingEntryById(id);
        parkingEntry.setEndParkTime(LocalDateTime.now());

        //calculate pricing
        parkingEntry.setAmount(calculateParkingPayment(
                parkingEntry.getStartParkTime(), parkingEntry.getEndParkTime(), parkingEntry.getVehicle()));

        parkingEntry.setActive(false);

        log.info("end parking for id:{} {}", parkingEntry.getId(), parkingEntry.toString());

        return mongoTemplate.save(parkingEntry);
    }

    public Parkings findParkingEntryById(String id)
    {
        return mongoTemplate.findById(id, Parkings.class);
    }

    public VacantParking calculateAvailableParkingSpaces()
    {
        VacantParking vacantParking = new VacantParking();

        Query twoWheelCountQuery = new Query();
        twoWheelCountQuery.addCriteria(Criteria.where("isActive").is(true));
        twoWheelCountQuery.addCriteria(Criteria.where("vehicle").is(VehicleEnum.TWO_WHEELED));

        int twoWheeledParkingAvailable = VehicleEnum.TWO_WHEELED.getTotalSpace() -
                (int) mongoTemplate.count(twoWheelCountQuery, Parkings.class);

        vacantParking.setAvailableTwoWheels(twoWheeledParkingAvailable);

        Query fourWheelCountQuery = new Query();
        fourWheelCountQuery.addCriteria(Criteria.where("isActive").is(true));
        fourWheelCountQuery.addCriteria(Criteria.where("vehicle").is(VehicleEnum.FOUR_WHEELED));

        int fourWheeledParkingAvailable = VehicleEnum.FOUR_WHEELED.getTotalSpace() -
                (int) mongoTemplate.count(fourWheelCountQuery, Parkings.class);

        vacantParking.setAvailableFourWheels(fourWheeledParkingAvailable);

        log.info("available two-wheeled parking: {}  | available four-wheeled parking: {}",
                twoWheeledParkingAvailable,
                fourWheeledParkingAvailable);

        return vacantParking;
    }

    public Parkings updateParkingEntry(Parkings parkingEntry)
    {
        //TODO: 2 modes of update—partial and full
        return mongoTemplate.save(parkingEntry);
    }

    private final BigDecimal calculateParkingPayment(LocalDateTime startParkTime, LocalDateTime endParkTime,
                                                     VehicleEnum vehicleType)
    {
        int minutes = (int) ChronoUnit.MINUTES.between(startParkTime, endParkTime);
        int hours = minutes / 60;
        int excessMinutes = minutes % 60;
        BigDecimal totalAmount = new BigDecimal(0);

        if((hours < 3) || (hours == 3 && excessMinutes == 0))
        {
            if (vehicleType.equals(VehicleEnum.TWO_WHEELED)) {
                totalAmount = BigDecimal.valueOf(PricingEnum.TWO_WHEELED_INITIAL_RATE.getValue());
            } else if (vehicleType.equals(VehicleEnum.FOUR_WHEELED)) {
                totalAmount = BigDecimal.valueOf(PricingEnum.FOUR_WHEELED_INITIAL_RATE.getValue());
            }
        } else {
            int excessHours = hours - PricingEnum.INITIAL_HOURS.getValue();

            if (vehicleType.equals(VehicleEnum.TWO_WHEELED)) {
                totalAmount = BigDecimal.valueOf(
                        PricingEnum.TWO_WHEELED_INITIAL_RATE.getValue() +
                        (int) (excessHours * PricingEnum.TWO_WHEELED_SUCCEEDING_RATE.getValue()) +
                        (excessMinutes > 0 ? PricingEnum.TWO_WHEELED_SUCCEEDING_RATE.getValue() : 0));
            } else {
                totalAmount = BigDecimal.valueOf(
                         PricingEnum.FOUR_WHEELED_INITIAL_RATE.getValue() +
                         (int) (excessHours * PricingEnum.FOUR_WHEELED_SUCCEEDING_RATE.getValue()) +
                         (excessMinutes > 0 ? PricingEnum.FOUR_WHEELED_SUCCEEDING_RATE.getValue() : 0));
            }
        }
        return totalAmount;
    }

}
