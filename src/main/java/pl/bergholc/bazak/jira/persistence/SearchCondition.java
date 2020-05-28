package pl.bergholc.bazak.jira.persistence;


public class SearchCondition {
    private String key;
    private String operator;
    private String value;

    public SearchCondition(String key, Operator operator, String value) {
        this.key = key;
        this.operator = operator.getValue();
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}