package com.flair.bi.compiler.search;

import java.util.List;
import java.util.Optional;

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

    public Optional<String> lastFeature() {
        if (feature.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(feature.get(feature.size() - 1));
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
