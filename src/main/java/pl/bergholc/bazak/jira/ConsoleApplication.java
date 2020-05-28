package pl.bergholc.bazak.jira;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.config.Config;
import pl.bergholc.bazak.jira.exception.DatabaseConnectionException;
import pl.bergholc.bazak.jira.persistence.sql.SqlPersistenceManager;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.console.ConsoleMenu;


public class ConsoleApplication {

    private static Logger logger = LoggerManager.getApplicationLogger();

    public static void main(String[] args) {
        int LOGIN_ACTION = 0;
        try {
            logger.trace("application started");
            Config config = new Config()
                    .withSqlPersistence()
                    .withConsoleView();

            SqlPersistenceManager sqlPersistence = (SqlPersistenceManager) config.getPersistenceManager();
            ConsoleMenu menu = new ConsoleMenu(config.getActions());
            config.getActions().get(LOGIN_ACTION).execute();
            config.getActions().remove(LOGIN_ACTION);

            if (!config.getSession().isLogged()) {
                System.err.println("Login or password is incorrect");
                return;
            }
            config.removeAdminActions();
            int amountOfActions = config.getActions().size();
            int userChoice;
            while (true) {
                userChoice = menu.showMenu();
                if (userChoice == amountOfActions) {
                    break;
                }
                config.getActions().get(userChoice).execute();
            }

            sqlPersistence.getConnection().close();
            logger.trace("application ended");
        } catch (DatabaseConnectionException e) {
            System.err.println("Access denied for database");
            logger.info("Access denied for database");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unknown error - contact with Administrator");
            logger.info(e.getMessage());
        }
    }
}
