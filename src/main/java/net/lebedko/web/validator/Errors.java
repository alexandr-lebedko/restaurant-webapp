package net.lebedko.web.validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * alexandr.lebedko : 21.06.2017
 */
public class Errors {

    private Map<String, String> errorsMap = new HashMap<>();

    public boolean hasErrors() {
        return errorsMap.size() > 0;
    }

    public void register(String code, String message) {
        errorsMap.put(code, message);
    }

    public String get(String code) {
        return errorsMap.get(code);
    }

    public Collection<String> values() {
        return errorsMap.values();

    }

}
