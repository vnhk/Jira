package pl.bergholc.bazak.jira.action.show;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.ProjectRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.ProjectView;

import java.util.Arrays;
import java.util.List;

public class ShowOneProjectAction implements Action {
    private SessionService session;
    private ProjectView projectView;
    private ProjectRepository projectRepository;
    private InfoService info;
    private AccessService accessService;

    public ShowOneProjectAction(SessionService session, ProjectView projectView, ProjectRepository projectRepository, InfoService info, AccessService accessService) {
        this.session = session;
        this.projectView = projectView;
        this.projectRepository = projectRepository;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public String getDisplayName() {
        return "Show Project";
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
            projectView.display(project, accessService.getRole(session.getUserId(), projectId));
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
        return Arrays.asList(Role.EMPLOYEE, Role.MANAGER, Role.CUSTOMER);
    }
}
