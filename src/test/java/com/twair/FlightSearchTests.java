package com.twair;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class FlightSearchTests {
    private String source;
    private String destination;
    private int seatsRequired;
    private FlightSearch allFlights;

    @Before
    public void setUp() throws Exception {
        source = "TestSource";
        destination = "TestDestination";
        seatsRequired = 0;
        Plane plane1 = new Plane("type1", 10);
        Flight flight1 = new Flight("F001", source, destination, plane1);
        Flight flight2 = new Flight("F002", "TestSource1", destination, plane1);
        Flight flight3 = new Flight("F003", source, destination, plane1);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight1);
        flightList.add(flight2);
        flightList.add(flight3);
        allFlights = new FlightSearch(flightList);
    }

    @Test
    public void shouldReturnListOfFlightsForMatchingSourceDestination() throws Exception {
        List<Flight> flights = allFlights.byLocation(source, destination).getFlightList();
        Assert.assertEquals(source, flights.get(0).getSource());
        Assert.assertEquals(destination, flights.get(0).getDestination());
        Assert.assertEquals(source, flights.get(1).getSource());
        Assert.assertEquals(destination, flights.get(1).getDestination());
        Assert.assertEquals(2, flights.size());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldMandateSource() throws Exception {
        allFlights.byLocation(null, destination);
    }

    @Test(expected=IllegalArgumentException.class)
    public void sourceCannotBeEmpty() throws Exception {
        allFlights.byLocation("", destination);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldMandateDestination() throws Exception {
        allFlights.byLocation(source, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void destinationCannotBeEmpty() throws Exception {
        allFlights.byLocation(source, "");
    }

    @Test
    public void shouldReturnListOfFlightsForMatchingSourceDestinationRequiredSeats() throws Exception {
        List<Flight> flights = allFlights.byLocationAndSeats(source, destination, seatsRequired).getFlightList();
        Assert.assertEquals(source, flights.get(0).getSource());
        Assert.assertEquals(destination, flights.get(0).getDestination());
        Assert.assertTrue(flights.get(0).getNumber()+" can allot atleast"+seatsRequired,
                (flights.get(0).getPlane().getNumberOfSeats() > seatsRequired));

        Assert.assertEquals(source, flights.get(1).getSource());
        Assert.assertEquals(destination, flights.get(1).getDestination());
        Assert.assertTrue(flights.get(1).getNumber()+" can allot atleast"+seatsRequired,
                (flights.get(1).getPlane().getNumberOfSeats() > seatsRequired));


        List<Flight> flightsWithAtleastOneSeat = allFlights.byLocationAndSeats("TestSource1", destination, 0).getFlightList();
        Assert.assertEquals(source, flights.get(0).getSource());
        Assert.assertEquals(destination, flights.get(0).getDestination());
        Assert.assertTrue(flights.get(0).getNumber()+" can allot atleast"+seatsRequired,
                (flights.get(0).getPlane().getNumberOfSeats() > 1));

    }
}
