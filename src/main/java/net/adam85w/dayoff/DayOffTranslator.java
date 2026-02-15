package net.adam85w.dayoff;

@FunctionalInterface
public interface DayOffTranslator {

    String translate(String name, String language);
}
