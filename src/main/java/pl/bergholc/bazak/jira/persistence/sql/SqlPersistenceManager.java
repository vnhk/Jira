package pl.bergholc.bazak.jira.persistence.sql;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.persistence.Persistable;
import pl.bergholc.bazak.jira.persistence.PersistenceManager;
import pl.bergholc.bazak.jira.persistence.QueryBuilder;
import pl.bergholc.bazak.jira.service.JDBCConnection;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.utility.QueryUtility;
import pl.bergholc.bazak.jira.utility.SqlTable;
import pl.bergholc.bazak.jira.utility.SqlUtility;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SqlPersistenceManager implements PersistenceManager {
    private Logger logger = LoggerManager.getApplicationLogger();
    private JDBCConnection connection;

    public SqlPersistenceManager(JDBCConnection connection) {
        this.connection = connection;
    }

    public JDBCConnection getConnection() {
        return connection;
    }

    private PreparedStatement prepareStatement(String sqlQuery, SqlTable columns) throws SQLException {
        logger.trace("prepareStatement");

        PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        int numberOfIteration = 0;

        for (Map.Entry<String, Object> entry : columns.entrySet()) {
            numberOfIteration++;
            preparedStatement.setObject(numberOfIteration, entry.getValue());
            logger.debug(entry.getValue());
        }
        return preparedStatement;
    }

    private int executeQuery(PreparedStatement preparedStatement) throws SQLException {
        logger.trace("executeQuery");
        int id = 0;
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    @Override
    public Persistable create(Persistable persistable) throws PersistenceException {
        logger.trace("create");
        String columnName = SqlUtility.getTableName(persistable.getClass());
        SqlTable table = SqlUtility.getColumnsWithValues(persistable);
        String query = QueryUtility.getInsertQuery(table, columnName);
        int id;
        try {
            PreparedStatement preparedStatement = prepareStatement(query, table);
            id = executeQuery(preparedStatement);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new PersistenceException(columnName + " can't be created", e.getMessage());
        }
        logger.info(columnName + ": created new row");
        persistable.setId(id);
        return persistable;
    }

    @Override
    public void update(Persistable persistable) throws PersistenceException {
        logger.trace("update");
        String columnName = SqlUtility.getTableName(persistable.getClass());
        SqlTable table = SqlUtility.getColumnsWithValues(persistable);
        int id = persistable.getId();
        String query = QueryUtility.getUpdateQuery(table, columnName, id);
        try {
            PreparedStatement preparedStatement = prepareStatement(query, table);
            id = executeQuery(preparedStatement);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new PersistenceException(columnName + " can't be edited", e.getMessage());
        }
        logger.info(columnName + ": updated row with id = " + id);
    }

    @Override
    public void delete(Persistable persistable) throws PersistenceException {
        logger.trace("delete");
        String columnName = SqlUtility.getTableName(persistable.getClass());
        int id = persistable.getId();
        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement("DELETE FROM " + columnName + " WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info(columnName + ": deleted row with id = " + id);
        } catch (SQLException sql) {
            logger.error(sql.getMessage());
            throw new PersistenceException(columnName + ": Row can't be deleted", sql.getMessage());
        }
    }

    @Override
    public List<Persistable> find(QueryBuilder queryBuilder) throws PersistenceException {
        logger.trace("find");
        String name = SqlUtility.getTableName(queryBuilder.getEntity());
        String query = "SELECT DISTINCT * FROM " + name;
        if (queryBuilder.getConditions().size() > 0)
            query += " WHERE " + SqlUtility.getSqlConditionQuery(queryBuilder);

        logger.debug(query);
        Object obj;

        Method[] methods;
        ResultSet resultSet;
        try {
            methods = queryBuilder.getEntity().getMethods();
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(query);
            SqlUtility.getPreparedStatement(queryBuilder, preparedStatement);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new PersistenceException(name + ": Row can't be selected", e.getMessage());
        }
        List<Persistable> persistences = new LinkedList<>();

        try {
            while (resultSet.next()) {
                obj = Class.forName(queryBuilder.getEntity().getName()).getConstructor().newInstance();
                for (Method method : methods) {
                    String prefixMethodName = "set";
                    if (method.getName().equals("setId")) {
                        method.invoke(obj, Integer.parseInt(String.valueOf(resultSet.getObject(method.getName().substring(prefixMethodName.length())))));
                    } else if (method.getName().startsWith(prefixMethodName)) {
                        method.invoke(obj, resultSet.getObject(method.getName().substring(prefixMethodName.length())));
                    }
                }
                persistences.add((Persistable) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info(persistences.size() + " rows returned");
        return persistences;
    }
}
