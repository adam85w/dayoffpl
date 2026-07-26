package net.adam85w.dayoff;

import net.adam85w.dayoff.domain.DayOffRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
class DayOffService {

    private static final Logger LOGGER = LogManager.getLogger(DayOffService.class);

    private final List<DayOffRule> rules;

    private final DayOffTranslator translator;

    private final int yearsLimitation;

    private final int threadPoolSize;

    public DayOffService(List<DayOffRule> rules, DayOffTranslator translator, @Value("${day-off.utils.validation.years-limitation:100}") int yearsLimitation, @Value("${day-off.utils.thread-pool-size:4}") int threadPoolSize) {
        this.rules = rules;
        this.translator = translator;
        this.yearsLimitation = yearsLimitation;
        this.threadPoolSize = threadPoolSize;
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

    public List<DayOffResult> areDaysOff(List<LocalDate> days, String lang) {
        if (days.isEmpty()) {
            return Collections.emptyList();
        }
        return days.stream()
                .peek(day -> LOGGER.info("Checking if day {} is off", day))
                .map(day -> isDayOff(day, lang)).collect(Collectors.toList());
    }

    public List<DayOffResult> areDaysOff(LocalDate from, LocalDate to, String lang) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Date from cannot be after date to.");
        }
        if (!from.plusYears(yearsLimitation).isAfter(to)) {
            throw new IllegalArgumentException("Years limitation exceeded.");
        }
        try (ForkJoinPool threadPool = new ForkJoinPool(threadPoolSize)) {
            LOGGER.warn("Start checking dates between {} and {}", from, to);
            var result = threadPool.submit(() ->
                    from.datesUntil(to.plusDays(1)).parallel()
                            .peek(day -> LOGGER.info("Checking if day {} is off", day))
                            .map(date -> isDayOff(date, lang))
                            .toList()
            ).join();
            LOGGER.warn("Stop checking dates between {} and {}", from, to);
            return result;
        }
    }
}
