package pl.bergholc.bazak.jira.action.show;


import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.PermissionRepository;
import pl.bergholc.bazak.jira.repository.ProjectRepository;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.ProjectView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowProjectsAction implements Action {

    private ProjectView projectView;
    private ProjectRepository projectRepository;
    private SessionService session;
    private PermissionRepository permissionRepository;
    private InfoService info;

    public ShowProjectsAction(ProjectView projectView, ProjectRepository projectRepository,
                              SessionService session, PermissionRepository permissionRepository, InfoService info) {
        this.projectView = projectView;
        this.projectRepository = projectRepository;
        this.session = session;
        this.permissionRepository = permissionRepository;
        this.info = info;
    }

    @Override
    public void execute() {
        try {
            List<Permission> permissions = permissionRepository.findByUserId(session.getUserId());

            Set<Project> projects = new HashSet<>();
            for (Permission permission : permissions) {
                projects.add(projectRepository.findById(permission.getProjectId()));
            }

            projectView.display(projects);
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        }
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Arrays.asList(Role.EMPLOYEE, Role.MANAGER, Role.CUSTOMER);
    }

    @Override
    public String getDisplayName() {
        return "Show Projects";
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

}
