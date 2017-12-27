package net.lebedko.entity.general;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class StringI18N {
    private Map<Locale, String> stringsToLocale = new HashMap<>();

    public StringI18N() {
    }

    public StringI18N(Map<Locale, String> stringsToLocale) {
        this.stringsToLocale = new HashMap<>(stringsToLocale);
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

    public static String removeExtraSpaces(String value) {
        return value.replaceAll("[\\s]{2,}", " ");
    }

    public static StringI18N removeExtraSpaces(StringI18N value) {
        return new StringI18N(value.getMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> removeExtraSpaces(entry.getValue())
                )));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<Locale, String> localeToString;

        private Builder() {
            this.localeToString = new HashMap<>();
        }

        public Builder add(Locale locale, String value) {
            localeToString.put(locale, value);
            return this;
        }

        public StringI18N build() {
            return new StringI18N(localeToString);
        }


    }
}
