package com.steti.core.dataKeys;

public enum KafkaTopics {

    portal_order_create("OrderKey", "Order");

    private String keyName;
    private String valueName;

    KafkaTopics(String keyName, String valueName) {

    }
}
