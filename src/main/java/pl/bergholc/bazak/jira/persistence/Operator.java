package pl.bergholc.bazak.jira.persistence;


public enum Operator {
    AND("AND"), OR("OR"), EQUAL("="),
    GREATER_THAN(">"), LESS_THAN("<");

    private String value;

    Operator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}

