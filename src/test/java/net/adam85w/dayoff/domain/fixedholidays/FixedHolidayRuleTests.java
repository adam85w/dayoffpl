package net.adam85w.dayoff.domain.fixedholidays;

import net.adam85w.dayoff.domain.DayOffRuleResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class FixedHolidayRuleTests {

    private static final FixedHoliday NEW_YEAR = new FixedHoliday("NEW_YEAR", 1, 1);

    private static final FixedHoliday CHRISTMAS_DAY = new FixedHoliday("CHRISTMAS_DAY", 25, 12);

    private static final FixedHoliday SECOND_DAY_OF_CHRISTMAS = new FixedHoliday("SECOND_DAY_OF_CHRISTMAS", 26, 12);

    private static final FixedHolidayCalendar CALENDAR = new FixedHolidayCalendar(List.of(NEW_YEAR, CHRISTMAS_DAY, SECOND_DAY_OF_CHRISTMAS));

    @Test
    void shouldBeHoliday() {
        var rule = new FixedHolidayRule(CALENDAR);
        Assertions.assertThat(rule.isDayOff(LocalDate.of(2023, 1, 1))).isEqualTo(new DayOffRuleResult(true, "NEW_YEAR"));
        Assertions.assertThat(rule.isDayOff(LocalDate.of(2023, 12, 25))).isEqualTo(new DayOffRuleResult(true, "CHRISTMAS_DAY"));
        Assertions.assertThat(rule.isDayOff(LocalDate.of(2023, 12, 26))).isEqualTo(new DayOffRuleResult(true, "SECOND_DAY_OF_CHRISTMAS"));
    }

    @Test
    void shouldNotBeHoliday() {
        var rule = new FixedHolidayRule(CALENDAR);
        var day = LocalDate.of(2023, 1, 1);
        var stop = LocalDate.of(2023, 12, 31);
        var holidays = List.of(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 25),
                LocalDate.of(2023, 12, 26));
        while (!day.isAfter(stop)) {
            if (!holidays.contains(day)) {
                Assertions.assertThat(rule.isDayOff(day)).isEqualTo(DayOffRuleResult.FALSE);
            }
            day = day.plusDays(1);
        }
    }
}
