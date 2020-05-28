package pl.bergholc.bazak.jira.action.show;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.model.User;
import pl.bergholc.bazak.jira.repository.PermissionRepository;
import pl.bergholc.bazak.jira.repository.ProjectRepository;
import pl.bergholc.bazak.jira.repository.UserRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.PermissionView;
import pl.bergholc.bazak.jira.view.ProjectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowProjectDetailsAction implements Action {
    private PermissionView permissionView;
    private PermissionRepository permissionRepository;
    private SessionService session;
    private ProjectView projectView;
    private ProjectRepository projectRepository;
    private InfoService info;
    private AccessService accessService;
    private UserRepository userRepository;

    public ShowProjectDetailsAction(PermissionView permissionView, PermissionRepository permissionRepository, SessionService session, ProjectView projectView, ProjectRepository projectRepository, InfoService info, AccessService accessService, UserRepository userRepository) {
        this.permissionView = permissionView;
        this.permissionRepository = permissionRepository;
        this.session = session;
        this.projectView = projectView;
        this.projectRepository = projectRepository;
        this.info = info;
        this.accessService = accessService;
        this.userRepository = userRepository;
    }

    @Override
    public String getDisplayName() {
        return "Show Project Details";
    }

    @Override
    public void execute() {
        try {
            int projectId = projectView.getProjectId();
            if (!accessService.isAdmin(session.getUserId())) {
                if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                    throw new AccessException();
                }
            }
            Project project = projectRepository.findById(projectId);
            List<Permission> permissions = permissionRepository.findByProjectId(projectId);

            permissions = permissions
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());

            List<String> names = new ArrayList<>();
            for (Permission permission : permissions) {
                User user = userRepository.getUserById(permission.getUserId());
                names.add(user.getLogin());
            }
            permissionView.display(permissions, project, names);
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Arrays.asList(Role.MANAGER, Role.CUSTOMER);
    }
}