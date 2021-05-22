package com.flair.bi.compiler.search;

import com.flair.bi.grammar.searchql.SearchQLParser;
import com.flair.bi.grammar.searchql.SearchQLParserBaseListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchQLListener extends SearchQLParserBaseListener {

    private final SearchResult searchResult = new SearchResult();

    @Override
    public void enterAggregation_statements(SearchQLParser.Aggregation_statementsContext ctx) {
        List<TerminalNode> comma = ctx.COMMA();
        List<SearchQLParser.Aggregation_statementContext> aggregation_statementContexts = ctx.aggregation_statement();

        List<AggregationStatementResult> statements = aggregation_statementContexts
                .stream()
                .map(as -> {
                    TerminalNode openPar = as.OPEN_PAR();
                    TerminalNode closePar = as.CLOSE_PAR();
                    AggregationStatementResult.State state;
                    if (openPar == null) {
                        state = AggregationStatementResult.State.FUNCTION;
                    } else if (closePar == null) {
                        state = AggregationStatementResult.State.FEATURE;
                    } else {
                        state = AggregationStatementResult.State.COMPLETED;
                    }
                    String featureName = Optional.ofNullable(as.feature()).map(f -> f.getText()).orElse(null);
                    String aggregationName = as.aggregation_function().getText();
                    return new AggregationStatementResult(aggregationName, featureName, state);
                })
                .collect(Collectors.toList());

        AggregationStatementsResult.State state = AggregationStatementsResult.State.EXPRESSION;
        if (comma.size() != aggregation_statementContexts.size()) {
            if (!statements.isEmpty()) {
                AggregationStatementResult last = statements.get(statements.size() - 1);
                AggregationStatementResult.State lastState = last.getState();
                if (lastState == AggregationStatementResult.State.COMPLETED) {
                    state = AggregationStatementsResult.State.COMPLETED;
                }
            }
        }

        AggregationStatementsResult aggregationStatement = new AggregationStatementsResult(statements, state);
        searchResult.setAggregationStatementsResult(aggregationStatement);
    }

    @Override
    public void exitBy_statement(SearchQLParser.By_statementContext ctx) {
        if (ctx.features() == null) {
            ByStatementResult byStatementResult = new ByStatementResult(new ArrayList<>());
            searchResult.setByStatementResult(byStatementResult);
            return;
        }
        List<String> features = ctx.features().feature()
                .stream()
                .map(f -> f.getText())
                .collect(Collectors.toList());
        searchResult.setByStatementResult(new ByStatementResult(features));
    }

    @Override
    public void exitWhere_statement(SearchQLParser.Where_statementContext ctx) {
        if (ctx.conditions() == null) {
            searchResult.setWhereStatementResult(new WhereStatementResult(new ArrayList<>()));
            return;
        }

        List<WhereConditionResult> whereConditions = ctx.conditions()
                .condition()
                .stream()
                .map(c -> {
                    SearchQLParser.Condition_inContext conditionIn = c.condition_in();
                    SearchQLParser.Condition_compareContext conditionCompare = c.condition_compare();
                    if (conditionIn != null) {
                        String featureName = conditionIn.feature().any_name().getText();
                        String statement = conditionIn.any_name().isEmpty() ?
                                null :
                                conditionIn.any_name().stream().map(any -> any.getText()).collect(Collectors.joining(","));
                        return new WhereConditionResult(featureName, null, statement);
                    } else if (conditionCompare != null) {
                        String featureName = conditionCompare.feature().getText();
                        String comparison = Optional.ofNullable(conditionCompare.comparison()).map(co -> co.getText()).orElse(null);
                        String statement = Optional.ofNullable(conditionCompare.any_name()).map(co -> co.getText()).orElse(null);
                        return new WhereConditionResult(featureName, comparison, statement);
                    } else {
                        throw new IllegalStateException("Unknown condition " + c);
                    }
                })
                .collect(Collectors.toList());
        searchResult.setWhereStatementResult(new WhereStatementResult(whereConditions));
    }

    @Override
    public void exitOrderby_statement(SearchQLParser.Orderby_statementContext ctx) {
        String featureName = Optional.ofNullable(ctx.feature()).map(co -> co.getText()).orElse(null);
        String orderDirection = Optional.ofNullable(ctx.order_direction()).map(co -> co.getText()).orElse(null);
        searchResult.setOrderByStatementResult(new OrderByStatementResult(featureName, orderDirection));
    }

    public SearchResult getResult() {
        return searchResult;
    }
}
