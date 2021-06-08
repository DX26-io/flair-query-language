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

        searchResult.addResult(new AggregationStatementsResult(statements, state));
    }

    @Override
    public void exitBy_statement(SearchQLParser.By_statementContext ctx) {
        if (ctx.features() == null) {
            searchResult.addResult(new ByStatementResult(new ArrayList<>(), ByStatementResult.State.EXPRESSION));
            return;
        }

        List<TerminalNode> comma = ctx.features().COMMA();
        List<SearchQLParser.FeatureContext> featureList = ctx.features().feature();

        List<String> features = featureList
                .stream()
                .map(f -> f.getText())
                .collect(Collectors.toList());

        ByStatementResult.State state = ByStatementResult.State.EXPRESSION;
        if (comma.size() < featureList.size()) {
            state = ByStatementResult.State.COMPLETED;
        }

        searchResult.addResult(new ByStatementResult(features, state));
    }

    @Override
    public void exitWhere_statement(SearchQLParser.Where_statementContext ctx) {
        if (ctx.conditions() == null) {
            searchResult.addResult(new WhereStatementResult(new ArrayList<>(), WhereStatementResult.State.EXPRESSION));
            return;
        }

        List<TerminalNode> comma = ctx.conditions().COMMA();
        List<SearchQLParser.ConditionContext> conditionsList = ctx.conditions().condition();

        List<WhereConditionResult> whereConditions = conditionsList
                .stream()
                .map(c -> {
                    SearchQLParser.Condition_inContext conditionIn = c.condition_in();
                    SearchQLParser.Condition_compareContext conditionCompare = c.condition_compare();
                    if (conditionIn != null) {
                        TerminalNode openPar = conditionIn.OPEN_PAR();
                        TerminalNode closePar = conditionIn.CLOSE_PAR();
                        WhereConditionResult.State state;
                        if (openPar == null) {
                            state = WhereConditionResult.State.FEATURE;
                        } else if (closePar == null) {
                            state = WhereConditionResult.State.STATEMENT;
                        } else {
                            state = WhereConditionResult.State.COMPLETED;
                        }
                        String featureName = conditionIn.feature().any_name().getText();
                        List<String> statements = conditionIn.any_name().stream().map(any -> any.getText()).collect(Collectors.toList());
                        return new WhereConditionResult(featureName, statements, state);
                    } else if (conditionCompare != null) {
                        String featureName = conditionCompare.feature().getText();
                        List<TerminalNode> spaces = conditionCompare.SPACES();
                        String comparison = Optional.ofNullable(conditionCompare.comparison()).map(co -> co.getText()).orElse(null);
                        String statement = Optional.ofNullable(conditionCompare.any_name()).map(co -> co.getText()).orElse(null);
                        WhereConditionResult.State state;
                        if (featureName == null) {
                            state = WhereConditionResult.State.FEATURE;
                        } else if (spaces.size() == 1) {
                            state = WhereConditionResult.State.CONDITION;
                        } else if (statement == null) {
                            state = WhereConditionResult.State.STATEMENT;
                        } else {
                            state = WhereConditionResult.State.COMPLETED;
                        }
                        return new WhereConditionResult(featureName, comparison, statement, state);
                    } else {
                        throw new IllegalStateException("Unknown condition " + c);
                    }
                })
                .collect(Collectors.toList());

        WhereStatementResult.State state = WhereStatementResult.State.EXPRESSION;
        if (comma.size() != conditionsList.size()) {
            if (!whereConditions.isEmpty()) {
                WhereConditionResult last = whereConditions.get(whereConditions.size() - 1);
                WhereConditionResult.State lastState = last.getState();
                if (lastState == WhereConditionResult.State.COMPLETED) {
                    state = WhereStatementResult.State.COMPLETED;
                }
            }
        }

        searchResult.addResult(new WhereStatementResult(whereConditions, state));
    }

    @Override
    public void exitOrderby_statement(SearchQLParser.Orderby_statementContext ctx) {
        String featureName = Optional.ofNullable(ctx.feature()).map(co -> co.getText()).orElse(null);
        String orderDirection = Optional.ofNullable(ctx.order_direction()).map(co -> co.getText()).orElse(null);

        OrderByStatementResult.State state;
        List<TerminalNode> spaces = ctx.SPACES();
        if (spaces.size() == 0) {
            state = OrderByStatementResult.State.START;
        } else if (spaces.size() == 1) {
            state = OrderByStatementResult.State.FEATURE;
        } else if (orderDirection == null) {
            state = OrderByStatementResult.State.DIRECTION;
        } else {
            state = OrderByStatementResult.State.COMPLETED;
        }
        searchResult.addResult(new OrderByStatementResult(featureName, orderDirection, state));
    }

    public SearchResult getResult() {
        return searchResult;
    }
}
