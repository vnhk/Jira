package pl.bergholc.bazak.jira.action.other;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.PermissionRepository;
import pl.bergholc.bazak.jira.repository.UserRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.PermissionView;

import java.util.Collections;
import java.util.List;

public class AddUserToProjectAction implements Action {
    private PermissionRepository permissionRepository;
    private UserRepository userRepository;
    private PermissionView view;
    private SessionService session;
    private AccessService accessService;
    private InfoService info;

    public AddUserToProjectAction(PermissionRepository permissionRepository, UserRepository userRepository, PermissionView view, SessionService session, AccessService accessService, InfoService info) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.view = view;
        this.session = session;
        this.accessService = accessService;
        this.info = info;
    }

    @Override
    public String getDisplayName() {
        return "Add Users to Project";
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public void execute() {
        try {
            int projectId = view.getProjectId();
            if (!accessService.isAdmin(session.getUserId())) {
                if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                    throw new AccessException();
                }
            }
            String username = view.getUsername();
            int userId = userRepository.getUserIdByName(username);
            Permission permissions = new Permission();
            permissions.setProjectId(projectId);
            permissions.setUserId(userId);
            permissions.setRoleId(view.getRole());
            permissionRepository.add(permissions);
            info.info("User has been added to the project.");
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Collections.singletonList(Role.MANAGER);
    }
}
