package net.adam85w.dayoff.translator;

import net.adam85w.dayoff.DayOffTranslator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;
import java.util.Optional;

@ConfigurationProperties(prefix = "day-off.translations")
class DayOffTranslatorPropertyConfiguration implements DayOffTranslator {

    private static final Logger LOGGER = LogManager.getLogger(DayOffTranslatorPropertyConfiguration.class);
    private static final String UNKNOWN = "UNKNOWN";

    private final List<DayOffTranslatorProperty> list;

    @ConstructorBinding
    DayOffTranslatorPropertyConfiguration(List<DayOffTranslatorProperty> list) {
        this.list = list;
    }

    @Override
    public String translate(String name, String language) {
        LOGGER.info("Searching for a holiday name {} in a language {}", name, language);
        Optional<DayOffTranslatorProperty> property = list.stream().filter(p -> p.name().equals(name)).findFirst();
        if (property.isPresent()) {
            LOGGER.info("The holiday name {} was found", name);
            var p = property.get();
            if (language.equalsIgnoreCase("pl") && p.pl() != null && !p.pl().isEmpty()) {
                return p.pl();
            } else if (p.en() != null && !p.en().isEmpty()) {
                return p.en();
            }
        }
        LOGGER.warn("The holiday name {} was not found", name);
        return UNKNOWN;
    }
}
