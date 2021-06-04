package com.flair.bi.compiler.search;

public class OrderByStatementResult implements IStatementResult {

    private final String feature;
    private final String direction;
    private final State state;

    public OrderByStatementResult(String feature, String direction, State state) {
        this.feature = feature;
        this.direction = direction;
        this.state = state;
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

    public State getState() {
        return state;
    }

    public enum State {
        START, FEATURE, DIRECTION, COMPLETED
    }
}
