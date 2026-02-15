package net.adam85w.dayoff.translator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class TranslatorPropertyConfigurationTest {

    private static final DayOffTranslatorProperty NEW_YEAR_PL = new DayOffTranslatorProperty("new_year", null, "Nowy Rok"); // only PL
    private static final DayOffTranslatorProperty NEW_YEAR_BOTH = new DayOffTranslatorProperty("new_year", "New Year", "Nowy Rok"); // both PL and EN.

    @Test
    void noneTranslationProvidedShouldReturnUnknown() {
        var translator = new DayOffTranslatorPropertyConfiguration(Collections.emptyList());
        Assertions.assertThat(translator.translate("new_year", "pl")).isEqualTo("UNKNOWN");
    }

    @Test
    void onlyPolishTranslationProvidedShouldReturnUnknownIfEnglishIsDemand() {
        var translator = new DayOffTranslatorPropertyConfiguration((List.of(NEW_YEAR_PL)));
        Assertions.assertThat(translator.translate("new_year", "en")).isEqualTo("UNKNOWN");
    }

    @Test
    void onlyPolishTranslationProvidedShouldReturnPolishIfPolishIsDemand() {
        var translator = new DayOffTranslatorPropertyConfiguration((List.of(NEW_YEAR_PL)));
        Assertions.assertThat(translator.translate("new_year", "pl")).isEqualTo("Nowy Rok");
    }

    @Test
    void bothTranslationsProvidedShouldReturnName() {
        var translator = new DayOffTranslatorPropertyConfiguration((List.of(NEW_YEAR_BOTH)));
        Assertions.assertThat(translator.translate("new_year", "en")).isEqualTo("New Year");
        Assertions.assertThat(translator.translate("new_year", "pl")).isEqualTo("Nowy Rok");
    }
}
