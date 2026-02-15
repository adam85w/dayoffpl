package net.adam85w.dayoff.domain.fixedholidays;

import net.adam85w.dayoff.domain.DayOffRule;
import net.adam85w.dayoff.domain.DayOffRuleResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@ConditionalOnProperty(prefix = "day-off.rule.holidays", name = "enabled", havingValue = "true")
class FixedHolidayRule implements DayOffRule {

    private final FixedHolidayCalendar calendar;

    FixedHolidayRule(FixedHolidayCalendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public DayOffRuleResult isDayOff(LocalDate day) {
        return calendar.obtainsHoliday(day).map(h -> new DayOffRuleResult(true, h.name())).orElse(DayOffRuleResult.FALSE);
    }
}
