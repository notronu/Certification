package com.gridnine.testing;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.service.FlightFilterBuilderImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        List<Flight> flightsDepartBeforeNow = new FlightFilterBuilderImpl(flights)
                .filterDepartBeforeNow()
                .build();


        List<Flight> flightsArrivalFirstBeforeDeparture = new FlightFilterBuilderImpl(flights)
                .filterArrivalFirstBeforeDeparture()
                .build();


        List<Flight> flightsSomeTimeSpentOnTheGroundForMoreThanTwoHours = new FlightFilterBuilderImpl(flights)
                .filterSomeTimeSpentOnTheGroundForMoreThanTwoHours()
                .build();


        System.out.println("Все рейсы:\n" + flights);
        System.out.println();
        System.out.println("Отправление до настоящего времени:\n" + flightsDepartBeforeNow);
        System.out.println();
        System.out.println("Прибытие перед последующим отправлением:\n" + flightsArrivalFirstBeforeDeparture);
        System.out.println();
        System.out.println("Провел время на земле более двух часов:\n" + flightsSomeTimeSpentOnTheGroundForMoreThanTwoHours);

    }
}