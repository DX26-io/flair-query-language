package com.flair.bi.compiler.search;

import java.util.List;

public class WhereStatementResult implements IStatementResult {
    private final List<WhereConditionResult> conditions;

    public WhereStatementResult(List<WhereConditionResult> conditions) {
        this.conditions = conditions;
    }

    public List<WhereConditionResult> getConditions() {
        return conditions;
    }
}
