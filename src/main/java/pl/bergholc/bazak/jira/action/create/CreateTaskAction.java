package pl.bergholc.bazak.jira.action.create;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.FormException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.model.Task;
import pl.bergholc.bazak.jira.repository.TaskRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.utility.FormUtility;
import pl.bergholc.bazak.jira.view.TaskView;

import java.util.Collections;
import java.util.List;

public class CreateTaskAction implements Action {

    private TaskView taskView;
    private TaskRepository repository;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;


    public CreateTaskAction(TaskView taskView, TaskRepository repository, SessionService session, InfoService info, AccessService accessService) {
        this.taskView = taskView;
        this.repository = repository;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public String getDisplayName() {
        return "Create Task";
    }

    @Override
    public void execute() {
        try {
            int projectId = taskView.getProjectId();
            if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                throw new AccessException();
            }
            Task task = new Task();
            task.setProjectId(projectId);
            task.setName(taskView.getData("name"));
            if (FormUtility.isEmptyForm(task.getName())) {
                throw new FormException("Task name");
            }
            task.setDescription(taskView.getData("description"));
            String employee = taskView.getData("employee");
            task.setEmployee(employee);
            if (FormUtility.isEmptyForm(String.valueOf(task.getEmployee()))) {
                throw new FormException("Task employee id");
            }

            task.setStatus("Open");
            repository.add(task);
            info.info("Task was created.");
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
        return Collections.singletonList(Role.CUSTOMER);
    }
}