package pl.bergholc.bazak.jira.view.console;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.service.ConsoleTextManager;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.PermissionView;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;

import java.util.List;

public class PermissionConsoleView implements PermissionView {
    private Logger logger = LoggerManager.getApplicationLogger();
    private ConsoleTextManager consoleTextManager = new ConsoleTextManager();

    @Override
    public int getRole() {
        System.out.println("1.Manager 2. Engineer 3. HR");
        int role = consoleTextManager.getInt("Set number of Role");
        while(role<1||role>3){
            role = consoleTextManager.getInt("Set correct number of Role");
        }
        return role;
    }

    @Override
    public String getUsername() {
        return consoleTextManager.loadFromConsole("Set username:");

    }

    @Override
    public int getProjectId(){
        logger.trace("getProjectId");
       return consoleTextManager.getInt("Set id of Project");
    }

    @Override
    public void display(List<Permission> permissions, Project project, List<String> names) {
        logger.trace("display");
        System.out.println(project.toString());
        int iterator = 0;
        for (Permission permission:permissions) {
            System.out.println("User: " + names.get(iterator)+": "+ Role.values()[permission.getRoleId()-1].name());
            iterator++;
        }
    }
}
