package pl.bergholc.bazak.jira.utility;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.persistence.Operator;
import pl.bergholc.bazak.jira.persistence.Persistable;
import pl.bergholc.bazak.jira.persistence.QueryBuilder;
import pl.bergholc.bazak.jira.persistence.SearchCondition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class SqlUtility {
    private static Logger logger = LoggerManager.getApplicationLogger();

    public static String getTableName(Class cl) {
        logger.trace("getTableName");
        return cl.getSimpleName() + "s";
    }

    public static String parseColumnName(Method method) {
        logger.trace("parseColumnName");
        String prefixMethodName = "get";
        if (method.getName().startsWith(prefixMethodName))
            return method.getName().substring(prefixMethodName.length());
        prefixMethodName = "is";
        return method.getName().substring(prefixMethodName.length());
    }

    public static boolean isGetter(Method method) {
        logger.trace("isGetter");
        return method.getName().startsWith("get") || method.getName().startsWith("is");
    }

    public static String getSqlConditionQuery(QueryBuilder qs){
        logger.trace("getSqlConditionQuery");
        List<SearchCondition> conditions = qs.getConditions();
        List<Operator> operators = qs.getOperators();
        StringBuilder query = new StringBuilder();
        for(int i = 0; i < conditions.size(); i++) {
            query.append(conditions.get(i).getKey());
            query.append(" ");
            query.append(conditions.get(i).getOperator());
            query.append(" ");
            query.append("?");
            query.append(" ");
            if(operators.size()>i) {
                query.append(operators.get(i));
                query.append(" ");
            }
        }
        return query.toString();
    }

    public static PreparedStatement getPreparedStatement(QueryBuilder qs, PreparedStatement ps) throws SQLException {
        logger.trace("getPreparedStatement");
        for (int i = 0; i < qs.getConditions().size(); i++) {
            ps.setObject(i+1,qs.getConditions().get(i).getValue());
        }
        logger.debug(ps);
        return ps;
    }

    public static SqlTable getColumnsWithValues(Persistable persistable) {
        logger.trace("getColumnWithValues");
        Object value;
        String name;
        SqlTable sqlTable = new SqlTable();

        for (Method method : persistable.getClass().getMethods()) {
            if (!method.toString().contains("getClass")) {
                try {
                    if (isGetter(method)) {
                        name = parseColumnName(method);
                        value = method.invoke(persistable);
                        sqlTable.put(name, value);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }
        return sqlTable;
    }

}