package net.adam85w.dayoff.domain.sundays;

import net.adam85w.dayoff.domain.DayOffRuleResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class SundayRuleTests {

    private static final String NAME = "SUNDAY";

    private static final LocalDate MONDAY = LocalDate.of(2023, 10, 23);
    private static final LocalDate TUESDAY = LocalDate.of(2023, 10, 24);
    private static final LocalDate WEDNESDAY = LocalDate.of(2023, 10, 25);
    private static final LocalDate THURSDAY = LocalDate.of(2023, 10, 26);
    private static final LocalDate FRIDAY = LocalDate.of(2023, 10, 27);
    private static final LocalDate SATURDAY = LocalDate.of(2023, 10, 28);
    private static final LocalDate SUNDAY = LocalDate.of(2023, 10, 29);


    @Test
    void shouldBeSaturday() {
        Assertions.assertThat(new SundayRule().isDayOff(SUNDAY)).isEqualTo(new DayOffRuleResult( true, NAME));
    }

    @Test
    void shouldNotBeSaturday() {
        Assertions.assertThat(new SundayRule().isDayOff(MONDAY)).isEqualTo(DayOffRuleResult.FALSE);
        Assertions.assertThat(new SundayRule().isDayOff(TUESDAY)).isEqualTo(DayOffRuleResult.FALSE);
        Assertions.assertThat(new SundayRule().isDayOff(WEDNESDAY)).isEqualTo(DayOffRuleResult.FALSE);
        Assertions.assertThat(new SundayRule().isDayOff(THURSDAY)).isEqualTo(DayOffRuleResult.FALSE);
        Assertions.assertThat(new SundayRule().isDayOff(FRIDAY)).isEqualTo(DayOffRuleResult.FALSE);
        Assertions.assertThat(new SundayRule().isDayOff(SATURDAY)).isEqualTo(DayOffRuleResult.FALSE);
    }
}

