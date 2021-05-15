package com.flair.bi.compiler.search;

import com.flair.bi.grammar.searchql.SearchQLParser;
import com.flair.bi.grammar.searchql.SearchQLParserBaseListener;

import java.util.List;
import java.util.stream.Collectors;

public class SearchQLListener extends SearchQLParserBaseListener {

    private final SearchResult searchResult = new SearchResult();

    @Override
    public void enterAggregation_statements(SearchQLParser.Aggregation_statementsContext ctx) {
        List<AggregationStatementResult> statements = ctx.aggregation_statement()
                .stream()
                .map(as -> {
                    String featureName = as.feature().getText();
                    String aggregationName = as.aggregation_function().getText();
                    return new AggregationStatementResult(aggregationName, featureName);
                })
                .collect(Collectors.toList());
        AggregationStatementsResult aggregationStatement = new AggregationStatementsResult(statements);
        searchResult.setAggregationStatementsResult(aggregationStatement);
    }

    @Override
    public void exitBy_statement(SearchQLParser.By_statementContext ctx) {
        List<String> features = ctx.features().feature()
                .stream()
                .map(f -> f.getText())
                .collect(Collectors.toList());
        searchResult.setByStatementResult(new ByStatementResult(features));
    }

    @Override
    public void exitWhere_statement(SearchQLParser.Where_statementContext ctx) {
        List<WhereConditionResult> whereConditions = ctx.conditions()
                .condition()
                .stream()
                .map(c -> {
                    SearchQLParser.Condition_inContext conditionIn = c.condition_in();
                    SearchQLParser.Condition_compareContext conditionCompare = c.condition_compare();
                    if (conditionIn != null) {
                        String featureName = conditionIn.feature().any_name().getText();
                        String statement = conditionIn.any_name().stream().map(any -> any.getText()).collect(Collectors.joining(","));
                        return new WhereConditionResult(featureName, null, statement);
                    } else if (conditionCompare != null) {
                        String featureName = conditionCompare.feature().getText();
                        String statement = conditionCompare.any_name().getText();
                        String comparison = conditionCompare.comparison().getText();
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
        String featureName = ctx.feature().getText();
        String orderDirection = ctx.order_direction().getText();
        searchResult.setOrderByStatementResult(new OrderByStatementResult(featureName, orderDirection));
    }

    public SearchResult getResult() {
        return searchResult;
    }
}
