package net.adam85w.dayoff.domain.movableholidays;

import net.adam85w.dayoff.domain.DayOffRule;
import net.adam85w.dayoff.domain.DayOffRuleResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@ConditionalOnProperty(prefix = "day-off.rule.holidays", name = "enabled", havingValue = "true")
class MovableHolidayRule implements DayOffRule {

    private final MovableHolidayCalendar calendar;

    MovableHolidayRule(MovableHolidayCalendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public DayOffRuleResult isDayOff(LocalDate day) {
        Optional<MovableHolidayCalculator.HolidayName> holiday = calendar.obtainsHolidayName(day);
        return holiday.map(holidayName -> new DayOffRuleResult(true, holidayName.name())).orElse(DayOffRuleResult.FALSE);
    }
}
