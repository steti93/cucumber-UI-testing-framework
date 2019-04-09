package com.steti.core.utils;

public class ItemList {
    private String fieldName;

    private String fieldValue;

    private String parentElement;

    private String clickedElement;

    private String produceName;

    private String producePrice;

    public ItemList() {

    }

    public ItemList(String fieldName, String fieldValue, String parentElement, String clickedElement) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.parentElement = parentElement;
        this.clickedElement = clickedElement;
    }

    public ItemList(String parentElement, String produceName, String producePrice) {
        this.parentElement = parentElement;
        this.produceName = produceName;
        this.producePrice = producePrice;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getParentElement() {
        return parentElement;
    }

    public String getClickedElement() {
        return clickedElement;
    }

    public String getProduceName() {
        return produceName;
    }

    public String getProducePrice() {
        return producePrice;
    }
}
