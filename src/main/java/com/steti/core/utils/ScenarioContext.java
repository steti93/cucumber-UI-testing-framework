package com.steti.core.utils;

import com.steti.core.dataKeys.ScenarioKeys;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScenarioContext {

    private static final Map<ScenarioKeys, Object> data = new HashMap<>();

    public void save(ScenarioKeys scenarioKeys, Object value) {
        data.put(scenarioKeys, value);
    }

    public <T> T getData(ScenarioKeys scenarioKeys) {
        return (T) data.get(scenarioKeys);
    }
}
