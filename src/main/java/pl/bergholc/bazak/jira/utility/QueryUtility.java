package pl.bergholc.bazak.jira.utility;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.service.LoggerManager;

import java.util.Map;

public class QueryUtility {
    private static Logger logger = LoggerManager.getApplicationLogger();

    public static String getUpdateQuery(SqlTable columns, String tableName, int id) {
        logger.trace("getUpdateQuery");
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(tableName);
        query.append(" SET ");
        String name;
        final String value = "?";

        for (Map.Entry<String, Object> entry : columns.entrySet()) {
            name = entry.getKey();
            query.append(",");
            query.append(name);
            query.append("=");
            query.append(value);
        }
        query.append(" WHERE id = ");
        query.append(id);

        String result = query.toString().replaceFirst(",", "");
        logger.debug(result);

        return result;
    }

    public static String getInsertQuery(SqlTable  columns, String tableName) {
        logger.trace("getInsertQuery");
        StringBuilder query =  new StringBuilder();
        query.append("INSERT INTO ");
        query.append(tableName);
        query.append("(");
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Object> entry : columns.entrySet()) {
            names.append(entry.getKey());
            names.append(",");
            values.append("?");
            values.append(",");
        }
        query.append(names.toString().substring(0, names.toString().length() - 1));
        query.append(") VALUES (");
        query.append(values.toString().substring(0, values.toString().length() - 1));
        query.append(")");

        logger.debug(query.toString());
        return query.toString();
    }
}
