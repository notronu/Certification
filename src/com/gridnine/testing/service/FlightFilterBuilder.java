package com.gridnine.testing.service;


import com.gridnine.testing.entity.Flight;

import java.util.List;

public interface FlightFilterBuilder {

    List<Flight> build();

    FlightFilterBuilder filterDepartBeforeNow();

    FlightFilterBuilder filterArrivalFirstBeforeDeparture();

    FlightFilterBuilder filterSomeTimeSpentOnTheGroundForMoreThanTwoHours();


}
