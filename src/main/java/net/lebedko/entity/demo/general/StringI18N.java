package net.lebedko.entity.demo.general;

import net.lebedko.entity.Validatable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public class StringI18N implements Validatable {
    private Map<Locale, String> stringsToLocale = new HashMap<>();

    public StringI18N() {
    }

    public StringI18N(Map<Locale, String> stringsToLocale) {
        this.stringsToLocale = new HashMap(stringsToLocale);
    }

    public void add(Locale locale, String value) {
        stringsToLocale.put(locale, value);
    }

    public String get(Locale locale) {
        return stringsToLocale.get(locale);
    }

    public String get(String languageCode) {
        return stringsToLocale.get(new Locale(languageCode));
    }

    public Map<Locale, String> getMap() {
        return new HashMap<>(stringsToLocale);
    }

    @Override
    public boolean isValid() {
        return !stringsToLocale.isEmpty() && noEmptyStrings();
    }

    private boolean noEmptyStrings() {
        return stringsToLocale.entrySet().stream()
                .allMatch(entry -> !entry.getValue().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        StringI18N that = (StringI18N) o;

        return Objects.equals(stringsToLocale, that.stringsToLocale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringsToLocale);
    }

    @Override
    public String toString() {
        return "StringI18N{" +
                "stringsToLocale=" + stringsToLocale +
                '}';
    }

}
