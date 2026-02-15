package net.adam85w.dayoff.domain.saturdays;

import net.adam85w.dayoff.domain.DayOffRule;
import net.adam85w.dayoff.domain.DayOffRuleResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
@ConditionalOnProperty(prefix = "day-off.rule.saturdays", name = "enabled", havingValue = "true")
class SaturdayRule implements DayOffRule {

    private static final String NAME = "SATURDAY";

    @Override
    public DayOffRuleResult isDayOff(LocalDate day) {
        if (DayOfWeek.SATURDAY == day.getDayOfWeek()) {
            return new DayOffRuleResult(true, NAME);
        }
        return DayOffRuleResult.FALSE;
    }
}
