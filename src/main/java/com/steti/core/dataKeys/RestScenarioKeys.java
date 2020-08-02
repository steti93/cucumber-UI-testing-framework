package com.steti.core.dataKeys;

public enum RestScenarioKeys implements ScenarioKeys {

    REQUEST("Base request for test");

    private String description;

    RestScenarioKeys(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
