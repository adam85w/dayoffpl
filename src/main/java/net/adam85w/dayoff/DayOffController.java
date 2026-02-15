package net.adam85w.dayoff;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/days-off")
class DayOffController {

    private static final Logger LOGGER = LogManager.getLogger(DayOffController.class);

    private static final String DEFAULT_LANGUAGE = "en";

    private final DayOffService service;

    DayOffController(DayOffService service) {
        this.service = service;
    }

    @GetMapping("/{day}")
    DayOffResult isDayOff(@PathVariable(name = "day") LocalDate day, @RequestParam(name = "lang", defaultValue = DEFAULT_LANGUAGE) String lang) {
        LOGGER.info("Checking if day {} is off", day);
        return service.isDayOff(day, lang);
    }

    @GetMapping(path = "/range", params = {"from", "to"})
    List<DayOffResult> isAnyDayOff(@RequestParam(name = "from") LocalDate from, @RequestParam(name = "to") LocalDate to, @RequestParam(name = "lang", defaultValue = DEFAULT_LANGUAGE) String lang) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException(String.format("from %s is before to %s", from, to));
        }
        return from.datesUntil(to.plusDays(1))
                .peek(day -> LOGGER.info("Checking if day {} is off", day))
                .map(date -> service.isDayOff(date, lang))
                .toList();
    }

    @GetMapping(path="/dates", params = {"days"})
    List<DayOffResult> areDaysOff(@RequestParam(name = "days") List<LocalDate> days, @RequestParam(name = "lang", defaultValue = DEFAULT_LANGUAGE) String lang) {
        if (days.isEmpty()) {
            return Collections.emptyList();
        }
        return days.stream()
                .peek(day -> LOGGER.info("Checking if day {} is off", day))
                .map(day -> service.isDayOff(day, lang)).collect(Collectors.toList());
    }
}
