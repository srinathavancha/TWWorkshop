package com.twair;

import org.apache.tomcat.util.codec.binary.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FlightSearch {

    private List<Flight> flightList;

    public FlightSearch(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public FlightSearch byLocation(String source, String destination) {
        if(source == null || source.isEmpty() || destination == null || destination.isEmpty()) {
            throw new IllegalArgumentException("source cannot be null");
        }
        List<Flight> matchingFlights = new ArrayList<Flight>();
        for (Flight flight : flightList) {
            if (flight.getSource().equals(source) && flight.getDestination().equals(destination)) {
                matchingFlights.add(flight);
            }
        }
        return new FlightSearch(matchingFlights);
    }

    /**
     * Method for filtering flights based on source, destination
     * and number of seats required.
     *
     * @param source {@link String}
     * @param destination {@link String}
     * @param requiredSeats {@link Integer}
     * @return {@link FlightSearch}
     */
    public FlightSearch byLocationAndSeats(String source, String destination, int requiredSeats){
        if (source == null || source.isEmpty() || destination == null || destination.isEmpty()) {
            throw new IllegalArgumentException("Source cannot be null");
        }
        List<Flight> matchingFlights = new ArrayList<Flight>();
        for (Flight flight : flightList) {
            //assign required seats as atleast 1
            requiredSeats = (requiredSeats > 1) ? requiredSeats : 1;
            if (flight.getSource().equals(source)
                    && flight.getDestination().equals(destination) && flight.getPlane().getNumberOfSeats() >= requiredSeats) {
                matchingFlights.add(flight);
            }
        }
        return new FlightSearch(matchingFlights);
    }
}
