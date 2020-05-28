package pl.bergholc.bazak.jira.action.delete;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Document;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.DocumentRepository;
import pl.bergholc.bazak.jira.repository.PermissionRepository;
import pl.bergholc.bazak.jira.repository.ProjectRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.ProjectView;

import java.util.Collections;
import java.util.List;

public class DeleteProjectAction implements Action {
    private ProjectRepository projectRepository;
    private ProjectView projectView;
    private PermissionRepository permissionRepository;
    private SessionService session;
    private DocumentRepository documentRepository;
    private InfoService info;
    private AccessService accessService;

    public DeleteProjectAction(ProjectRepository projectRepository, ProjectView projectView, PermissionRepository permissionRepository, SessionService session, DocumentRepository documentRepository, InfoService info, AccessService accessService) {
        this.projectRepository = projectRepository;
        this.projectView = projectView;
        this.permissionRepository = permissionRepository;
        this.session = session;
        this.documentRepository = documentRepository;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public String getDisplayName() {
        return "Delete Project";
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

            List<Permission> permissions = permissionRepository.findByProjectId(projectId);
            for (Permission permission : permissions) {
                permissionRepository.delete(permission);
            }
            List<Document> documents = documentRepository.findByProjectId(projectId);
            for (Document document : documents) {
                documentRepository.delete(document.getId());
            }
            projectRepository.delete(projectId);
            projectView.delete("Project #" + projectId + " was deleted.");
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e){
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Collections.singletonList(Role.MANAGER);
    }
}
