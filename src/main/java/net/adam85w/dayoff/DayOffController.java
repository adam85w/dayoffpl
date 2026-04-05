package net.adam85w.dayoff;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/days-off")
class DayOffController {

    private static final String DEFAULT_LANGUAGE = "en";

    private final DayOffService service;

    DayOffController(DayOffService service) {
        this.service = service;
    }

    @GetMapping("/{day}")
    DayOffResult isDayOff(@PathVariable(name = "day") LocalDate day, @RequestParam(name = "lang", defaultValue = DEFAULT_LANGUAGE) String lang) {
        return service.isDayOff(day, lang);
    }

    @GetMapping(path = "/range", params = {"from", "to"})
    List<DayOffResult> isAnyDayOff(@RequestParam(name = "from") LocalDate from, @RequestParam(name = "to") LocalDate to, @RequestParam(name = "lang", defaultValue = DEFAULT_LANGUAGE) String lang) {

        return service.areDaysOff(from, to, lang);
    }

    @GetMapping(path="/dates", params = {"days"})
    List<DayOffResult> areDaysOff(@RequestParam(name = "days") List<LocalDate> days, @RequestParam(name = "lang", defaultValue = DEFAULT_LANGUAGE) String lang) {
        return service.areDaysOff(days, lang);
    }
}
