package pl.bergholc.bazak.jira.view;

import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;

import java.util.List;
import java.util.Set;

public interface ProjectView {
    void display(List<Project> project);
    void display(Set<Project> project);
    void display(Project project, Role role);
    void delete(String message);
    int getProjectId();
    String getData(String attribute);
}
