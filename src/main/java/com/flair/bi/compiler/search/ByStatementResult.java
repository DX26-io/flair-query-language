package com.flair.bi.compiler.search;

import java.util.List;

public class ByStatementResult {

    private final List<String> feature;

    public ByStatementResult(List<String> feature) {
        this.feature = feature;
    }

    public List<String> getFeature() {
        return feature;
    }
}
