package pl.bergholc.bazak.jira.view;

import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Project;

import java.util.List;

public interface PermissionView {
    void display(List<Permission> permissions, Project project, List<String> names);

    int getProjectId();

    String getUsername();

    int getRole();
}
