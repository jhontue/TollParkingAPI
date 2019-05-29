# TollParkingAPI
[![Build Status](https://travis-ci.com/jhontue/TollParkingAPI.svg?branch=master&style=flat-square)](https://travis-ci.com/jhontue/TollParkingAPI)

TollParkingAPI is a Java API for toll parking management.

## How to build from the sources
Clone this repository
  ```bash
  $ git clone git@github.com:jhontue/TollParkingAPI.git
  $ cd TollParkingAPI
  ```
Install with Maven
  ```bash
  $ mvn clean install
  ```
## How to use the API
Include the following lines to your `pom.xml` dependencies : 
```xml
<dependencies>
    <dependency>
      <groupId>com.jhontue.parking.api</groupId>
      <artifactId>toll-parking-api</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Create a new parking
For Toll parking creation, you should use the class `TollParking` and setup your parking configuration : 
```java

TollParking parking = TollParking.create()
    .addParkingSlot(CarType.GASOLINE, 10)
    .addParkingSlot(CarType.ELECTRIC_20KW, 5)
    .addParkingSlot(CarType.ELECTRIC_50KW, 3)
    .perHourPricing(new BigDecimal(2.00))
    .build();
```
### Pricing policy
Two pricing policy implementations are provided. One for each hour spent in the parking and another with a fixed
amount + each hour. Feel free to use your proper price implementation, it can be provided with the configuration.

```java
// each hour spent in the parking
TollParking parking = TollParking.create()
        .addParkingSlot(CarType.GASOLINE, 10)
        .perHourPricing(new BigDecimal(2.00)) // hour price
        .build();

// fixed amount + each hour spent in the parking
TollParking parking = TollParking.create()
        .addParkingSlot(CarType.GASOLINE, 10)
        .perHourAndFixedAmountPricing(new BigDecimal(2.00), new BigDecimal(5.00)) // hour price and fixed amount
        .build();

// custom pricing policy
TollParking parking = TollParking.create()
        .addParkingSlot(CarType.GASOLINE, 10)
        .pricingPolicy((arrivalTime, departureTime) -> BigDecimal.ZERO) // free parking
        .build();
```

### Play with it
```java
// create parking with 10 gasoline slots and per hour pricing
TollParking parking = TollParking.create()
        .addParkingSlot(CarType.GASOLINE, 10)
        .perHourPricing(new BigDecimal(2.00))
        .build();

// the car tries to enter the parking and gets a parking slot if available
Optional<ParkingSlot> parkingSlot = parking.checkinCar(Car.of(CarType.GASOLINE));

// the car leaves the parking and gets a bill
Bill bill = parking.checkoutCar(firstParkingSlot.get());
```

## Documentation
javadoc