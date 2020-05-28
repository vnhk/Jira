package pl.bergholc.bazak.jira.view.console;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.service.ConsoleTextManager;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.ProjectView;

import java.util.List;
import java.util.Set;


public class ProjectConsoleView implements ProjectView {
    private ConsoleTextManager consoleTextManager = new ConsoleTextManager();
    private Logger logger = LoggerManager.getApplicationLogger();

    @Override
    public void display(Project project, Role role) {
        logger.trace("display");
        System.out.println(project);
    }

    @Override
    public void delete(String message) {
        System.out.println(message);
    }

    @Override
    public String getData(String attribute) {
        return consoleTextManager.loadFromConsole(attribute);
    }

    public int getProjectId() {
        logger.trace("getProjectId");
        return consoleTextManager.getInt("Set id of Project");
    }

    @Override
    public void display(Set<Project> projects) {
        logger.trace("display");
        for (Project project : projects) {
            display(project, null);
        }
    }

    @Override

    public void display(List<Project> project) {
        logger.trace("display");
        for (Project p : project) {
            display(p, null);
        }
    }
}
