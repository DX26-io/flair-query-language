package com.flair.bi.compiler.search;

import java.util.List;

public class ByStatementResult implements IStatementResult {

    private final List<String> feature;

    public ByStatementResult(List<String> feature) {
        this.feature = feature;
    }

    public List<String> getFeature() {
        return feature;
    }

    @Override
    public String toString() {
        return "{" +
                "feature=" + feature +
                '}';
    }
}
