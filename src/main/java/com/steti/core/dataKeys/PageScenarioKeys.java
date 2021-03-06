package com.steti.core.dataKeys;

public enum PageScenarioKeys implements ScenarioKeys {
    OPEN_DRIVER("Open Driver"),
    CURRENT_PAGE("Saving current page Object"),
    TEST_EMAIL("Saving test email of new user"),
    LAST_CLICKED("Last clicked element"),
    SCENARIO_NAME("Name of executed scenario"),
    LIST_RANDOM_VALUES("(A list of random values selected during the test");



    private String description;

    PageScenarioKeys(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
