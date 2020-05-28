package pl.bergholc.bazak.jira.action.show;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.model.Task;
import pl.bergholc.bazak.jira.repository.TaskRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.TaskView;

import java.util.Arrays;
import java.util.List;

public class ShowTasksAction implements Action {
    private TaskView taskView;
    private TaskRepository repository;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;

    public ShowTasksAction(TaskView taskView, TaskRepository repository, SessionService session, InfoService info, AccessService accessService) {
        this.taskView = taskView;
        this.repository = repository;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public void execute() {
        try {
            int projectId = taskView.getProjectId();
            if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                throw new AccessException();
            }

            List<Task> tasks = repository.findByProjectIdAndEmployee(projectId,session.getUserLogin());

            taskView.display(tasks);
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Arrays.asList(Role.EMPLOYEE, Role.CUSTOMER);
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public String getDisplayName() {
        return "Show Tasks";
    }
}
