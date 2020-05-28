package pl.bergholc.bazak.jira.view.console;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.service.ConsoleTextManager;
import pl.bergholc.bazak.jira.service.LoggerManager;

import java.util.List;


public class ConsoleMenu {
    private Logger logger = LoggerManager.getApplicationLogger();
    private List<Action> actions;
    private ConsoleTextManager consoleTextManager = new ConsoleTextManager();

    public ConsoleMenu(List<Action> actions) {
        this.actions = actions;
    }

    private boolean doActionExist(int option) {
        logger.trace("doActionExist");
        return option <= actions.size() + 1 && option >= 1;
    }

    private void displayMenu() {
        System.out.println("Set number of action:");
        int i;
        for (i = 0; i < actions.size(); i++) {
            System.out.println(i + 1 + " " + actions.get(i).getDisplayName());
        }
        System.out.println(++i + " Exit");
    }

    private int getOptionNumber() {
        logger.trace("getOptionNumber");
        int option = consoleTextManager.getInt("");
        while (!doActionExist(option)) {
            option = consoleTextManager.getInt("Set number of action:");
        }
        return option - 1;
    }

    public int showMenu() {
        logger.trace("showMenu");
        displayMenu();
        int userChoice = getOptionNumber();
        logger.debug(userChoice);
        return userChoice;
    }
}
