package net.adam85w.dayoff;

import net.adam85w.dayoff.domain.DayOffRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
class DayOffService {

    private static final Logger LOGGER = LogManager.getLogger(DayOffService.class);

    private final List<DayOffRule> rules;

    private final DayOffTranslator translator;

    public DayOffService(List<DayOffRule> rules, DayOffTranslator translator) {
        this.rules = rules;
        this.translator = translator;
    }

    public DayOffResult isDayOff(LocalDate day, String lang) {
        LOGGER.info("Checking if the day {} is a day off", day);
        for (var rule : rules) {
            LOGGER.info("Checking a day {} against the rule {}", day, rule.getClass().getSimpleName());
            var result = rule.isDayOff(day);
            if (result.is()) {
                LOGGER.info("According to rule {} the day {} is a day off", rule.getClass().getSimpleName(), day);
                return new DayOffResult(day, true, translator.translate(result.name(), lang));
            }
        }
        LOGGER.info("The day {} is a working day", day);
        return new DayOffResult(day, false);
    }
}
