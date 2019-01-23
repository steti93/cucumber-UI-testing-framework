package com.steti.core.dataKeys;

public enum RestKeys implements Keys {

    REQUEST("Base request for test");

    private String description;

    RestKeys(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
