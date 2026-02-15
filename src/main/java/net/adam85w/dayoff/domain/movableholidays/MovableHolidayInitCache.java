package net.adam85w.dayoff.domain.movableholidays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.IntStream;

@Component
class MovableHolidayInitCache implements ApplicationRunner {

    private static final Logger LOGGER = LogManager.getLogger(MovableHolidayInitCache.class);

    private final MovableHolidayCalendar calendar;

    MovableHolidayInitCache(MovableHolidayCalendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Start generating cache for movable holidays");
        var startingYear = 0;
        var finishingYear = 0;
        if (args.containsOption("hc")) {
            LOGGER.info("Obtaining cache configuration from arguments");
            var arg0 = Integer.parseInt(args.getOptionValues("hc").get(0));
            var arg1 = Integer.parseInt(args.getOptionValues("hc").get(1));
            if (arg0 > arg1) {
                startingYear = arg1;
                finishingYear = arg0;
            } else {
                startingYear = arg0;
                finishingYear = arg1;
            }
        } else {
            LOGGER.info("Obtaining cache configuration from defaults");
            startingYear = LocalDate.now().getYear()-1;
            finishingYear = startingYear+11;
        }
        LOGGER.info("Obtained startingYear={} and finishingYear {}", startingYear, finishingYear);
        IntStream.range(startingYear, finishingYear).forEach(year -> calendar.obtainsHolidayName(LocalDate.of(year, 1, 1)));
        LOGGER.info("Finish generating cache for movable holidays");
    }
}
