package com.flair.bi.compiler.search;

public class SearchQuery {

    private final String statement;

    public SearchQuery(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "{" +
                "statement='" + statement + '\'' +
                '}';
    }
}
