package net.adam85w.dayoff.domain;

import java.time.LocalDate;

@FunctionalInterface
public interface DayOffRule {

    DayOffRuleResult isDayOff(LocalDate day);
}
