package net.adam85w.dayoff.domain.movableholidays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Movable holiday
 * The information on the Polish Wikipedia: https://pl.wikipedia.org/wiki/%C5%9Awi%C4%99ta_ruchome
 */
@Component
@ConditionalOnProperty(prefix = "day-off.rule.holidays", name = "enabled", havingValue = "true")
class MovableHolidayCalculator {

    private static final Logger LOGGER = LogManager.getLogger(MovableHolidayCalculator.class);

    @Cacheable(value = "movable-holidays")
    public Map<HolidayName, LocalDate> compute(int year) {
        LOGGER.info("Start computing holidays for year={}", year);
        LocalDate easterSunday = computeEasterSunday(year);

        var result = new HashMap<HolidayName, LocalDate>() {{
            put(HolidayName.EASTER_SUNDAY, easterSunday);
            put(HolidayName.EASTER_MONDAY, computeEasterMonday(easterSunday));
            put(HolidayName.PENTECOST_SUNDAY, computePentecostSunday(easterSunday));
            put(HolidayName.CORPUS_CHRISTI, computeCorpusChristi(easterSunday));
        }};
        LOGGER.info("Finish computing holidays for year={}", year);
        return result;
    }

    /**
     * The algorithm for computing the Easter Sunday holiday date: https://en.wikipedia.org/wiki/Date_of_Easter#Anonymous_Gregorian_algorithm
     */
    private LocalDate computeEasterSunday(int year) {
        int a = year%19;
        int b = year/100;
        int c = year%100;
        int d = b/4;
        int e = b%4;
        int g = (8*b+13)/25;
        int h = ((19*a)+b-d-g+15)%30;
        int i = c/4;
        int k = c%4;
        int l = (32+(2*e)+(2*i)-h-k)%7;
        int m = (a+(11*h)+(19*l))/433;
        int month = (h+l-(7*m)+90)/25;
        int day = (h+l-(7*m)+(33*month)+19)%32;
        return LocalDate.of(year, month, day);
    }

    private LocalDate computeEasterMonday(LocalDate easterSunday) {
        return easterSunday.plusDays(1);
    }

    /**
     * The algorithm for computing the Pentecost Sunday holiday date: https://pl.wikipedia.org/wiki/Zes%C5%82anie_Ducha_%C5%9Awi%C4%99tego
     */
    private LocalDate computePentecostSunday(LocalDate easterSunday) {
        return easterSunday.plusDays(49);
    }

    /**
     * The algorithm for computing the Corpus Christi holiday date: https://pl.wikipedia.org/wiki/Uroczysto%C5%9B%C4%87_Naj%C5%9Bwi%C4%99tszego_Cia%C5%82a_i_Krwi_Chrystusa
     */
    private LocalDate computeCorpusChristi(LocalDate easterSunday) {
        return easterSunday.plusDays(60);
    }

    /**
     * The enum for public holidays in Poland: https://en.wikipedia.org/wiki/Public_holidays_in_Poland
     */
    public enum HolidayName {
        EASTER_SUNDAY, // Pierwszy dzień Wielkiej Nocy
        EASTER_MONDAY, // Drugi dzień Wielkiej Nocy
        PENTECOST_SUNDAY, // Zielone świątki
        CORPUS_CHRISTI // Boże Ciało
    }
}
