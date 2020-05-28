package pl.bergholc.bazak.jira.view.console;

import pl.bergholc.bazak.jira.service.ConsoleTextManager;
import pl.bergholc.bazak.jira.view.UserView;

public class UserConsoleView implements UserView {
    private ConsoleTextManager consoleTextManager = new ConsoleTextManager();

    public String getData(String message) {
        return consoleTextManager.loadFromConsole(message);
    }
}
