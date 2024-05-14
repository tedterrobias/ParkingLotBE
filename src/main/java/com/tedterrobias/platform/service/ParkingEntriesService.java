package com.tedterrobias.platform.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.tedterrobias.platform.enumeration.VehicleEnum;
import com.tedterrobias.platform.model.ParkingEntries;
import com.tedterrobias.platform.model.ParkingEntriesTicket;
import com.tedterrobias.platform.model.VacantParking;
import com.tedterrobias.platform.model.mapper.ParkingEntriesTicketMapper;
import com.tedterrobias.platform.util.DateUtil;
import com.tedterrobias.platform.util.PaymentUtil;
import io.vavr.control.Try;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ParkingEntriesService
{
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(ParkingEntriesService.class);

    private static final ParkingEntriesTicketMapper ticketMapper
        = Mappers.getMapper(ParkingEntriesTicketMapper.class);

    public ParkingEntriesService()
    {
    }

    public ParkingEntriesService(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    public void startParkingEntry(VehicleEnum vehicleType, HttpServletResponse response)
    {
        ParkingEntries parkingEntry = new ParkingEntries();
        parkingEntry.setVehicle(vehicleType);
        parkingEntry.setStartParkTime(LocalDateTime.now());
        parkingEntry.setAmount(BigDecimal.ZERO);
        parkingEntry.setActive(true);

        ParkingEntries savedParkingEntry = mongoTemplate.save(parkingEntry);

        Try.of(() -> {
            Document parkingTicket = new Document(PageSize.A7, 10, 10, 10, 10);
            PdfWriter pdfWriter = PdfWriter.getInstance(parkingTicket, response.getOutputStream());

            ParkingEntriesTicket ticket = ticketMapper.parkingEntriesToParkingEntriesTicket(parkingEntry);

            Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontBold.setSize(15F);

            Font font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setSize(8F);

            parkingTicket.open();
            parkingTicket.add(new Paragraph("TNT Parking Corp.\n", fontBold));
            parkingTicket.add(new Paragraph("\nVehicle Type: \n" + ticket.getVehicle() + "\n", font));
            parkingTicket.add(new Paragraph("\nStarting parking time: \n" +
                DateUtil.formatDate(ticket.getStartParkTime()) + "\n", font));
            parkingTicket.add(new Paragraph("\n\nPlease present this ticket upon exit.\n", font));
            parkingTicket.add(new Paragraph("\nThank you!\n\n\n", font));

            Barcode barcode = new Barcode128();
            barcode.setCode(parkingEntry.getId().toUpperCase());
            PdfContentByte pdfContentByte = new PdfContentByte(pdfWriter);
            Image image = barcode.createImageWithBarcode(pdfContentByte, Color.BLACK, Color.BLACK);
            image.scaleAbsolute(180F, 50F);
            parkingTicket.add(image);

            parkingTicket.close();
            return parkingTicket;
        });

        response.setHeader("Content-Disposition",
            "attachment; filename=" + savedParkingEntry.getId() + ".pdf");
    }

    public ParkingEntries endParkingEntry(String id)
    {
        ParkingEntries parkingEntry = findParkingEntryById(id);
        parkingEntry.setEndParkTime(LocalDateTime.now());

        //calculate pricing
        parkingEntry.setAmount(PaymentUtil.calculateParkingPayment(
                parkingEntry.getStartParkTime(), parkingEntry.getEndParkTime(), parkingEntry.getVehicle()));

        parkingEntry.setActive(false);

        log.info("end parking for id:{} {}", parkingEntry.getId(), parkingEntry);

        return mongoTemplate.save(parkingEntry);
    }

    public ParkingEntries findParkingEntryById(String id)
    {
        return mongoTemplate.findById(id, ParkingEntries.class);
    }

    public VacantParking calculateAvailableParkingSpaces()
    {
        VacantParking vacantParking = new VacantParking();

        Query twoWheelCountQuery = new Query();
        twoWheelCountQuery.addCriteria(Criteria.where("isActive").is(true));
        twoWheelCountQuery.addCriteria(Criteria.where("vehicle").is(VehicleEnum.TWO_WHEELED));

        int twoWheeledParkingAvailable = VehicleEnum.TWO_WHEELED.getTotalSpace() -
            (int) mongoTemplate.count(twoWheelCountQuery, ParkingEntries.class);

        vacantParking.setAvailableTwoWheels(twoWheeledParkingAvailable);

        Query fourWheelCountQuery = new Query();
        fourWheelCountQuery.addCriteria(Criteria.where("isActive").is(true));
        fourWheelCountQuery.addCriteria(Criteria.where("vehicle").is(VehicleEnum.FOUR_WHEELED));

        int fourWheeledParkingAvailable = VehicleEnum.FOUR_WHEELED.getTotalSpace() -
            (int) mongoTemplate.count(fourWheelCountQuery, ParkingEntries.class);

        vacantParking.setAvailableFourWheels(fourWheeledParkingAvailable);

        log.info("available two-wheeled parking: {}  | available four-wheeled parking: {}",
                twoWheeledParkingAvailable,
                fourWheeledParkingAvailable);

        return vacantParking;
    }

    public ParkingEntries updateParkingEntry(ParkingEntries parkingEntry)
    {
        //TODO: 2 modes of update—partial and full
        return mongoTemplate.save(parkingEntry);
    }
}
