package pl.bergholc.bazak.jira.action.create;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.FormException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.ProjectRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.utility.FormUtility;
import pl.bergholc.bazak.jira.view.ProjectView;

import java.util.Collections;
import java.util.List;

public class CreateProjectAction implements Action {
    private ProjectView projectView;
    private ProjectRepository projectRepository;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;

    public CreateProjectAction(ProjectView projectView, ProjectRepository projectRepository, SessionService session, InfoService info, AccessService accessService) {
        this.projectView = projectView;
        this.projectRepository = projectRepository;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public String getDisplayName() {
        return "Create Project";
    }

    @Override
    public boolean onlyAdminAccess() {
        return true;
    }

    @Override
    public void execute() {
        try {
            if (!accessService.isAdmin(session.getUserId())) {
                throw new AccessException();
            }
            Project project = new Project();
            project.setCreatorId(session.getUserId());
            project.setName(projectView.getData("name"));
            if (FormUtility.isEmptyForm(project.getName())) {
                throw new FormException("Project name");
            }
            project.setDescription(projectView.getData("description"));
            project = projectRepository.add(project);
            projectView.display(project,null);
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        } catch (FormException e) {
            info.error(e.getMessage());
        }
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Collections.singletonList(null);
    }
}