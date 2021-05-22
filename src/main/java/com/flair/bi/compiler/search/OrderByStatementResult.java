package com.flair.bi.compiler.search;

public class OrderByStatementResult implements IStatementResult {

    private final String feature;
    private final String direction;

    public OrderByStatementResult(String feature, String direction) {
        this.feature = feature;
        this.direction = direction;
    }

    public String getFeature() {
        return feature;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "{" +
                "feature='" + feature + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
