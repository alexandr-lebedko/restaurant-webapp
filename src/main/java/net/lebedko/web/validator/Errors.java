package net.lebedko.web.validator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Errors {

    private Map<String, String> errorsMap = new LinkedHashMap<>();

    public boolean hasErrors() {
        return errorsMap.size() > 0;
    }

    public void register(String code, String message) {
        errorsMap.put(code, message);
    }

    public String get(String code) {
        return errorsMap.get(code);
    }

    public Set<Map.Entry<String,String>> getEntrySet() {
        return errorsMap.entrySet();
    }

    @Override
    public String toString() {
        return "Errors{" +
                "errorsMap=" + errorsMap +
                '}';
    }
}