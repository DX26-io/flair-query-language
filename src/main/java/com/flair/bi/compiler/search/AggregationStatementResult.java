package com.flair.bi.compiler.search;

public class AggregationStatementResult {
    private final String function;
    private final String feature;

    public AggregationStatementResult(String function, String feature) {
        this.function = function;
        this.feature = feature;
    }

    public String getFunction() {
        return function;
    }

    public String getFeature() {
        return feature;
    }
}
