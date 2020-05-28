package pl.bergholc.bazak.jira.action;

import pl.bergholc.bazak.jira.model.Role;

import java.util.List;

public interface Action {
    String getDisplayName();
    boolean onlyAdminAccess();
    void execute();

    List<Role> getAllowedRoles();
}
