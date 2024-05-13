# ParkingLotBE
Parking lot management back end application by Ted Terrobias. I started 
this project on May 24, 2024. This application is my interpretation 
of a parking lot application based on my personal experiences with paid parking. 
A car enters a paid parking space, gets a ticket from a receptionist, 
either a person or a machine. Then before exiting, 
the ticket is returned to the receptionist for payment.

This is a _pet project_ of mine. Feel free to leave suggestions. Thanks.

### You may contact me through the following:
- Email: tedterrobias@gmail.com
- LinkedIn: [Ted Terrobias](https://www.linkedin.com/in/tedterrobias/)

## Major Changes
### 1.0.0 (May 12, 2024)
- Added core functionalities
  - start a single parking entry (by vehicle type)
  - end a single parking entry (by ID)
  - get number of vacant parking spaces by vehicle type
  - update parking entry _(partially implemented)_


## Planned future updates
- create parking entries manually
    - JSON request (single and bulk)
    - spreadsheet upload
    - download spreadsheet template
- calculate earnings by date range
    - scheduled job (daily, from 12MN to 11:59PM)
    - send out email
- print/generate single parking ticket
    - pdf format
    - QR code
- periodical update of active parking fees
    - scheduled job
- print/generate multiple parking info
    - pdf format
- Generate payment receipt
- Apply discounts and promos
  - loyalty cards?
- Loadable cards
- unit and integration testing
  - test containers

## Minor changes
### 1.0.2 (May 13, 2024)
- Added swagger descriptions per endpoint
- updated readme.md
### 1.0.1 (May 12, 2024)
- bug fix