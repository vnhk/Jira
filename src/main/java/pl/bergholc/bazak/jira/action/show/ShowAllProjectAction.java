package pl.bergholc.bazak.jira.action.show;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.ProjectRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.ProjectView;

import java.util.List;

public class ShowAllProjectAction implements Action {
    private ProjectView projectView;
    private ProjectRepository projectRepository;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;

    public ShowAllProjectAction(ProjectView projectView, ProjectRepository projectRepository, SessionService session, InfoService info, AccessService accessService) {
        this.projectView = projectView;
        this.projectRepository = projectRepository;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public void execute() {
        try {
            if (!accessService.isAdmin(session.getUserId())) {
                throw new AccessException();
            }
            projectView.display(projectRepository.showAll());
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return true;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Show All Projects";
    }

}
