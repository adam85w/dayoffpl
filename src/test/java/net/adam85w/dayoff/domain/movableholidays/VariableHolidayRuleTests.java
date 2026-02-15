package net.adam85w.dayoff.domain.movableholidays;

import net.adam85w.dayoff.domain.DayOffRuleResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

class VariableHolidayRuleTests {

    private static final LocalDate EASTER_SUNDAY_2023 = LocalDate.of(2023, 4, 9);
    private static final LocalDate EASTER_MONDAY_2023 =  LocalDate.of(2023, 4, 10);
    private static final LocalDate PENTECOST_SUNDAY_2023 = LocalDate.of(2023, 5, 28);
    private static final LocalDate CORPUS_CHRISTI_2023 = LocalDate.of(2023, 6, 8);

    private static final LocalDate EASTER_SUNDAY_2022 = LocalDate.of(2022, 4, 17);
    private static final LocalDate EASTER_MONDAY_2022 =  LocalDate.of(2022, 4, 18);
    private static final LocalDate PENTECOST_SUNDAY_2022 = LocalDate.of(2022, 6, 5);
    private static final LocalDate CORPUS_CHRISTI_2022 = LocalDate.of(2022, 6, 16);

    private static final LocalDate EASTER_SUNDAY_2013 = LocalDate.of(2013, 3, 31);
    private static final LocalDate EASTER_MONDAY_2013 =  LocalDate.of(2013, 4, 1);
    private static final LocalDate PENTECOST_SUNDAY_2013 = LocalDate.of(2013, 5, 19);
    private static final LocalDate CORPUS_CHRISTI_2013 = LocalDate.of(2013, 5, 30);

    private static final Set<LocalDate> HOLIDAYS_2023 = Collections.unmodifiableSet(Sets.newSet(EASTER_SUNDAY_2023, EASTER_MONDAY_2023, PENTECOST_SUNDAY_2023, CORPUS_CHRISTI_2023));

    private static final Set<LocalDate> HOLIDAYS_2022 = Collections.unmodifiableSet(Sets.newSet(EASTER_SUNDAY_2022, EASTER_MONDAY_2022, PENTECOST_SUNDAY_2022, CORPUS_CHRISTI_2022));

    private static final Set<LocalDate> HOLIDAYS_2013 = Collections.unmodifiableSet(Sets.newSet(EASTER_SUNDAY_2013, EASTER_MONDAY_2013, PENTECOST_SUNDAY_2013, CORPUS_CHRISTI_2013));

    @Test
    void shouldBeHolidayWithoutCache() {
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(EASTER_SUNDAY_2023)).isEqualTo(new DayOffRuleResult(true, "EASTER_SUNDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(EASTER_MONDAY_2023)).isEqualTo(new DayOffRuleResult(true, "EASTER_MONDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(PENTECOST_SUNDAY_2023)).isEqualTo(new DayOffRuleResult(true, "PENTECOST_SUNDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(CORPUS_CHRISTI_2023)).isEqualTo(new DayOffRuleResult(true, "CORPUS_CHRISTI"));

        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(EASTER_SUNDAY_2022)).isEqualTo(new DayOffRuleResult(true, "EASTER_SUNDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(EASTER_MONDAY_2022)).isEqualTo(new DayOffRuleResult(true, "EASTER_MONDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(PENTECOST_SUNDAY_2022)).isEqualTo(new DayOffRuleResult(true, "PENTECOST_SUNDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(CORPUS_CHRISTI_2022)).isEqualTo(new DayOffRuleResult(true, "CORPUS_CHRISTI"));

        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(EASTER_SUNDAY_2013)).isEqualTo(new DayOffRuleResult(true, "EASTER_SUNDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(EASTER_MONDAY_2013)).isEqualTo(new DayOffRuleResult(true, "EASTER_MONDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(PENTECOST_SUNDAY_2013)).isEqualTo(new DayOffRuleResult(true, "PENTECOST_SUNDAY"));
        Assertions.assertThat(new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator())).isDayOff(CORPUS_CHRISTI_2013)).isEqualTo(new DayOffRuleResult(true, "CORPUS_CHRISTI"));
    }

    @Test
    void shouldBeHolidayWithCache() {
        var rule = new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator()));
        Assertions.assertThat(rule.isDayOff(EASTER_SUNDAY_2023)).isEqualTo(new DayOffRuleResult(true, "EASTER_SUNDAY"));
        Assertions.assertThat(rule.isDayOff(EASTER_MONDAY_2023)).isEqualTo(new DayOffRuleResult(true, "EASTER_MONDAY"));
        Assertions.assertThat(rule.isDayOff(PENTECOST_SUNDAY_2023)).isEqualTo(new DayOffRuleResult(true, "PENTECOST_SUNDAY"));
        Assertions.assertThat(rule.isDayOff(CORPUS_CHRISTI_2023)).isEqualTo(new DayOffRuleResult(true, "CORPUS_CHRISTI"));

        Assertions.assertThat(rule.isDayOff(EASTER_SUNDAY_2022)).isEqualTo(new DayOffRuleResult(true, "EASTER_SUNDAY"));
        Assertions.assertThat(rule.isDayOff(EASTER_MONDAY_2022)).isEqualTo(new DayOffRuleResult(true, "EASTER_MONDAY"));
        Assertions.assertThat(rule.isDayOff(PENTECOST_SUNDAY_2022)).isEqualTo(new DayOffRuleResult(true, "PENTECOST_SUNDAY"));
        Assertions.assertThat(rule.isDayOff(CORPUS_CHRISTI_2022)).isEqualTo(new DayOffRuleResult(true, "CORPUS_CHRISTI"));

        Assertions.assertThat(rule.isDayOff(EASTER_SUNDAY_2013)).isEqualTo(new DayOffRuleResult(true, "EASTER_SUNDAY"));
        Assertions.assertThat(rule.isDayOff(EASTER_MONDAY_2013)).isEqualTo(new DayOffRuleResult(true, "EASTER_MONDAY"));
        Assertions.assertThat(rule.isDayOff(PENTECOST_SUNDAY_2013)).isEqualTo(new DayOffRuleResult(true, "PENTECOST_SUNDAY"));
        Assertions.assertThat(rule.isDayOff(CORPUS_CHRISTI_2013)).isEqualTo(new DayOffRuleResult(true, "CORPUS_CHRISTI"));
    }

    @Test
    void shouldNotBeHoliday() {
        var rule = new MovableHolidayRule(new MovableHolidayCalendar(new MovableHolidayCalculator()));

        var y2013 = LocalDate.of(2013, 1, 1);
        var y2014 = LocalDate.of(2014, 1, 1);
        while (y2013.isBefore(y2014)) {
            if (HOLIDAYS_2013.contains(y2013)) { // it is a holiday
                y2013 = y2013.plusDays(1);
                continue;
            }
            Assertions.assertThat(rule.isDayOff(y2013)).isEqualTo(DayOffRuleResult.FALSE);
            y2013 = y2013.plusDays(1);
        }

        var y2022 = LocalDate.of(2022, 1, 1);
        var y2023 = LocalDate.of(2023, 1, 1);
        while (y2022.isBefore(y2013)) {
            if (HOLIDAYS_2022.contains(y2022)) { // it is a holiday
                y2022 = y2022.plusDays(1);
                continue;
            }
            Assertions.assertThat(rule.isDayOff(y2022)).isEqualTo(DayOffRuleResult.FALSE);
            y2022 = y2022.plusDays(1);
        }

        y2023 = LocalDate.of(2023, 1, 1);
        var y2024 = LocalDate.of(2024, 1, 1);
        while (y2023.isBefore(y2024)) {
            if (HOLIDAYS_2023.contains(y2023)) { // it is a holiday
                y2023 = y2023.plusDays(1);
                continue;
            }
            Assertions.assertThat(rule.isDayOff(y2023)).isEqualTo(DayOffRuleResult.FALSE);
            y2023 = y2023.plusDays(1);
        }
    }
}
