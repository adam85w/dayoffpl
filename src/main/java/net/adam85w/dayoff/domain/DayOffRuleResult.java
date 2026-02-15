package net.adam85w.dayoff.domain;

public record DayOffRuleResult(boolean is, String name) {

    public static final DayOffRuleResult FALSE = new DayOffRuleResult(false, null);
}
