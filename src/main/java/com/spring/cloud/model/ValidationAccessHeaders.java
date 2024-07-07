package com.spring.cloud.model;

public enum ValidationAccessHeaders {
    SERVICE_ID("service-id"),
    SECURITY_GROUP("security-group"),
    ACTION_ID("action-id"),
    RESOURCE_ID("resource-id"),
    CONTEXT("context");

    private final String type;

    ValidationAccessHeaders(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getType() {
        return this.type;
    }
}
