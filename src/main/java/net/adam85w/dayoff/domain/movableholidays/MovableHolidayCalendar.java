package net.adam85w.dayoff.domain.movableholidays;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@ConditionalOnProperty(prefix = "day-off.rule.holidays", name = "enabled", havingValue = "true")
class MovableHolidayCalendar {

    private final MovableHolidayCalculator calculator;

    private final ConcurrentMap<Integer, Map<MovableHolidayCalculator.HolidayName, LocalDate>> cache;

    public MovableHolidayCalendar(MovableHolidayCalculator calculator) {
        this.calculator = calculator;
        cache = new ConcurrentHashMap<>();
    }

    /**
     * Obtains a holiday name of a specified date (argument: day) and returns it.
     * If specified date is not a holiday then an empty optional is returned.
     *
     * @param day The date
     * @return Name of a holiday
     */
    public Optional<MovableHolidayCalculator.HolidayName> obtainsHolidayName(LocalDate day) {
        cache.computeIfAbsent(day.getYear(), key -> calculator.compute(day.getYear()));
        Map<MovableHolidayCalculator.HolidayName, LocalDate> holidays = cache.get(day.getYear());
        for (MovableHolidayCalculator.HolidayName name : holidays.keySet()) {
            if (holidays.get(name).equals(day)) {
                return Optional.of(name);
            }
        }
        return Optional.empty();
    }
}
