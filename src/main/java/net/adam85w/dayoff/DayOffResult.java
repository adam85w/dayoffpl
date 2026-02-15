package net.adam85w.dayoff;

import java.time.LocalDate;

record DayOffResult(LocalDate day, boolean isOff, String name) {

    DayOffResult(LocalDate day, boolean isOff) {
        this(day, isOff, null);
    }
}
