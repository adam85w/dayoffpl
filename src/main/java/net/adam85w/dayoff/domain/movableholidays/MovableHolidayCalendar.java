package net.adam85w.dayoff.domain.movableholidays;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(prefix = "day-off.rule.holidays", name = "enabled", havingValue = "true")
class MovableHolidayCalendar {

    private final MovableHolidayCalculator calculator;

    public MovableHolidayCalendar(MovableHolidayCalculator calculator) {
        this.calculator = calculator;
    }

    /**
     * Obtains a holiday name of a specified date (argument: day) and returns it.
     * If specified date is not a holiday then an empty optional is returned.
     *
     * @param day The date
     * @return Name of a holiday
     */
    public Optional<MovableHolidayCalculator.HolidayName> obtainsHolidayName(LocalDate day) {
        return calculator.compute(day.getYear()).entrySet().stream()
                .filter(entry -> entry.getValue().equals(day))
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
