package pl.bergholc.bazak.jira.persistence;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private Class entity;
    private List<SearchCondition> conditions;
    private List<Operator> operators;

    public QueryBuilder(Class entity) {
        this.entity = entity;
        conditions = new ArrayList<>();
        operators = new ArrayList<>();
    }

    public void appendOperator(Operator operator) {
        operators.add(operator);
    }

    public void appendCondition(SearchCondition condition) {
        conditions.add(condition);
    }

    public List<SearchCondition> getConditions() {
        return conditions;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public Class getEntity() {
        return entity;
    }


}


