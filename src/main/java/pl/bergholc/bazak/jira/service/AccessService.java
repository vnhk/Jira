package pl.bergholc.bazak.jira.service;

import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.model.User;
import pl.bergholc.bazak.jira.repository.PermissionRepository;
import pl.bergholc.bazak.jira.repository.UserRepository;

import java.util.List;

public class AccessService {
    private UserRepository userRepository;
    private PermissionRepository permissionRepository;

    public AccessService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public AccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccessService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean isAdmin(int id) throws PersistenceException {
        User user = userRepository.getUserById(id);
        return user.isAdmin();
    }

    public boolean hasNoNeededRole(List<Role> neededRoles, int userId, int projectId) throws PersistenceException {
        List<Permission> permissionsForProject = permissionRepository.findByProjectId(projectId);
        for (Permission permission : permissionsForProject) {
            if (permission.getUserId() == userId) {
                if (neededRoles.contains(Role.values()[permission.getRoleId() - 1])) {
                    return false;
                }
            }
        }
        return true;
    }

    public Role getRole(int userId, int projectId) throws PersistenceException {
        List<Permission> permissionsForProject = permissionRepository.findByProjectId(projectId);
        for (Permission permission : permissionsForProject) {
            if (permission.getUserId() == userId) {
                return Role.values()[permission.getRoleId() - 1];
            }
        }

        return null;
    }
}
