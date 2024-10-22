package com.gridnine.testing.service;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.entity.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class FlightFilterBuilderImpl implements FlightFilterBuilder {


    private List<Flight> flights;

    public FlightFilterBuilderImpl(List<Flight> flights) {

        this.flights = new ArrayList<>(flights);
    }

    /**
     * Метод build(),  тип List<Flight>  возвращает  List <flights> flights.
     */


    @Override
    public List<Flight> build() {
        return flights;
    }

    /**
     * Метод filterDepartBeforeNow() использует .removeIf
     * Метод removeIf() в ArrayList используется для удаления всех элементов этого ArrayList - List <flights> flights,
     * удовлетворяющих заданному фильтру предикатов, который передается в качестве параметра методу.
     * С помощью return this возвращается текущий объект с тем свойством, которое мы вызвали.
     */
    @Override
    public FlightFilterBuilder filterDepartBeforeNow() {
        flights.removeIf(flight ->
                flight.getSegments()
                        .stream()
                        .anyMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now()))
        );
        return this;
    }

    /**
     * Метод filterArrivalFirstBeforeDeparture() использует .removeIf
     * Метод removeIf() в ArrayList используется для удаления всех элементов этого ArrayList - List <flights> flights,
     * удовлетворяющих заданному фильтру предикатов, который передается в качестве параметра методу.
     * Метод isBefore() класса LocalDate в Java проверяет, предшествует ли эта дата указанной дате.
     * С помощью return this возвращается текущий объект с тем свойством, которое мы вызвали.
     * Результат: остаются в списке сегменты с датой прилёта раньше даты вылета.
     */
    @Override
    public FlightFilterBuilder filterArrivalFirstBeforeDeparture() {
        flights.removeIf(flight ->
                flight.getSegments()
                        .stream()
                        .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()))
        );
        return this;
    }

    /**
     * Метод filterSomeTimeSpentOnTheGroundForMoreThanTwoHours() использует .removeIf
     * Метод removeIf() в ArrayList используется для удаления всех элементов этого ArrayList - List <flights> flights,
     * удовлетворяющих заданному фильтру предикатов, который передается в качестве параметра методу.
     * С помощью return this возвращается текущий объект с тем свойством, которое мы вызвали.
     * Результат: остаются в списке перелеты, где общее время, проведённое на земле, превышает два часа
     * (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним).
     */

    @Override
    public FlightFilterBuilder filterSomeTimeSpentOnTheGroundForMoreThanTwoHours() {
        flights.removeIf(flight -> {
            List<Segment> segments = flight.getSegments();
            LocalDateTime nextDeparture;
            LocalDateTime lastArrival;
            /**
             *    Нулевое поле класса Duration в пакете java.time используется для
             *    установки этого значения длительности равным нулю.
             *    Оно аналогично нулевому значению для других типов данных в Java.
             *    Period использует значения, основанные на дате, в то время как Duration
             *    использует значения, основанные на времени.
             */
            Duration duration = Duration.ZERO;

            for (int i = 1; i < segments.size(); i++) {
                /**
                 *  Поскольку самолет сначала прибыл потом вылетает в качестве прибытия берем предыдущий сегмент
                 *  рассчитываем продолжительность полета (между следующим вылетом и прошлым прибытием
                 *  - нас интересует время, проведенное самолетом на земле
                 *  устанавливаем для вывода продолжительности знак >, то есть больше 2 часов
                 */

                nextDeparture = segments.get(i).getDepartureDate();
                lastArrival = segments.get(i - 1).getArrivalDate();
                duration = duration.plus(Duration.between(nextDeparture, lastArrival).abs());
            }
            return duration.toHours() > 2;
        });
        return this;

    }
}
