package com.flair.bi.compiler.search;

import java.util.List;

public class ByStatementResult implements IStatementResult {

    private final List<String> feature;
    private final State state;

    public ByStatementResult(List<String> feature, State state) {
        this.feature = feature;
        this.state = state;
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

    public State getState() {
        return state;
    }

    public enum State {
        EXPRESSION, COMPLETED;
    }
}
