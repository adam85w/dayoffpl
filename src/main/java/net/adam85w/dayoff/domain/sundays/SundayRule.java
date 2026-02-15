package net.adam85w.dayoff.domain.sundays;

import net.adam85w.dayoff.domain.DayOffRule;
import net.adam85w.dayoff.domain.DayOffRuleResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
@ConditionalOnProperty(prefix = "day-off.rule.sundays", name = "enabled", havingValue = "true")
class SundayRule implements DayOffRule {

    private static final String NAME = "SUNDAY";

    @Override
    public DayOffRuleResult isDayOff(LocalDate day) {
        if (DayOfWeek.SUNDAY == day.getDayOfWeek()) {
            return new DayOffRuleResult(true, NAME);
        }
        return DayOffRuleResult.FALSE;
    }
}
