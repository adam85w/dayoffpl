package net.adam85w.dayoff.domain.fixedholidays;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ConfigurationProperties(prefix = "day-off.rule.holidays")
@ConditionalOnProperty(prefix = "day-off.rule.holidays", name = "enabled", havingValue = "true")
class FixedHolidayCalendar {

    private final List<FixedHoliday> list;

    @ConstructorBinding
    FixedHolidayCalendar(List<FixedHoliday> list) {
        this.list = list;
    }

    /**
     * Obtains the holiday for the specified date (argument: day) and returns it.
     * If the specified date is not a holiday, an empty Optional is return
     *
     * @param day
     * @return Holiday name
     */
    public Optional<FixedHoliday> obtainsHoliday(LocalDate day) {
        return list.stream().filter(holiday -> holiday.day() == day.getDayOfMonth() && holiday.month() == day.getMonthValue()).findFirst();
    }
}
