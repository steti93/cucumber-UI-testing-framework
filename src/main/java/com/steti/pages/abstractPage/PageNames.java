package com.steti.pages.abstractPage;

public enum PageNames {

    //Demo
    FacebookHomePage("");

    private String description;

    PageNames(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
