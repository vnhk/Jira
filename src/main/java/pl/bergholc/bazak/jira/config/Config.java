package pl.bergholc.bazak.jira.config;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.action.create.CreateDocumentAction;
import pl.bergholc.bazak.jira.action.create.CreateProjectAction;
import pl.bergholc.bazak.jira.action.create.CreateTaskAction;
import pl.bergholc.bazak.jira.action.create.CreateUserAction;
import pl.bergholc.bazak.jira.action.delete.DeleteDocumentAction;
import pl.bergholc.bazak.jira.action.delete.DeleteProjectAction;
import pl.bergholc.bazak.jira.action.display.*;
import pl.bergholc.bazak.jira.action.other.AddUserToProjectAction;
import pl.bergholc.bazak.jira.action.other.EditDocumentAction;
import pl.bergholc.bazak.jira.action.other.LogInAction;
import pl.bergholc.bazak.jira.action.other.LogOutAction;
import pl.bergholc.bazak.jira.action.show.*;
import pl.bergholc.bazak.jira.exception.DatabaseConnectionException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.persistence.PersistenceManager;
import pl.bergholc.bazak.jira.persistence.sql.SqlPersistenceManager;
import pl.bergholc.bazak.jira.repository.*;
import pl.bergholc.bazak.jira.service.*;
import pl.bergholc.bazak.jira.view.*;
import pl.bergholc.bazak.jira.view.console.DocumentConsoleView;
import pl.bergholc.bazak.jira.view.console.PermissionConsoleView;
import pl.bergholc.bazak.jira.view.console.ProjectConsoleView;
import pl.bergholc.bazak.jira.view.console.UserConsoleView;
import pl.bergholc.bazak.jira.view.web.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Config {
    private PersistenceManager persistenceManager;
    private List<Action> actions;
    private SessionService session;
    private AccessService accessService;

    public Config() {
        actions = new ArrayList<>();
    }

    public AccessService getAccessService() {
        return accessService;
    }

    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    public SessionService getSession() {
        return session;
    }

    public void setSession(SessionService session) {
        this.session = session;
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public void removeAdminActions() {
        try {
            if (!accessService.isAdmin(session.getUserId())) {
                actions.removeIf(Action::onlyAdminAccess);
            }
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Action> getOnlyAdminActions() {
        List<Action> adminActions = new LinkedList<>();
        for (Action action : actions) {
            if (action.onlyAdminAccess()) {
                adminActions.add(action);
            }
        }
        return adminActions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Config withSqlPersistence() throws DatabaseConnectionException {
        persistenceManager = new SqlPersistenceManager(new JDBCConnection());
        return this;
    }

    public void closeConnection() {
        SqlPersistenceManager sqlPersistenceManager = (SqlPersistenceManager) persistenceManager;
        sqlPersistenceManager.getConnection().close();
    }

    public Config withWebView(HttpServletRequest request, HttpServletResponse response) {
        session = new WebSession(request.getSession(true));

        InfoService info = new WebInfo(request, response);

        UserView userView = new UserWebView(request, response);
        TaskView taskView = new TaskWebView(request, response);
        DocumentView documentView = new DocumentWebView(request, response);
        PermissionRepository permissionRepository = new PermissionRepository(persistenceManager);
        DocumentRepository documentRepository = new DocumentRepository(persistenceManager);
        PermissionView permissionView = new PermissionWebView(request, response);
        DisplayFormView displayFormView = new DisplayFormView(request, response);
        UserRepository userRepository = new UserRepository(persistenceManager);
        TaskRepository taskRepository = new TaskRepository(persistenceManager);
        ProjectView projectView = new ProjectWebView(request, response);
        ProjectRepository projectRepository = new ProjectRepository(persistenceManager);
        accessService = new AccessService(userRepository, permissionRepository);
        ShowProjectsAction showProjectsAction = new ShowProjectsAction(projectView, projectRepository, session, permissionRepository, info);
        CreateProjectAction createProjectAction = new CreateProjectAction(projectView, projectRepository, session, info, accessService);
        LogInAction logInAction = new LogInAction(userView, userRepository, session, info);
        ShowProjectDetailsAction showProjectDetailsAction = new ShowProjectDetailsAction(permissionView, permissionRepository, session, projectView, projectRepository, info, accessService, userRepository);
        DeleteProjectAction deleteProjectAction = new DeleteProjectAction(projectRepository, projectView, permissionRepository, session, documentRepository, info, accessService);
        DisplayProjectFormAction displayProjectFormAction = new DisplayProjectFormAction(session, displayFormView, info, accessService);
        DisplayLoginAction displayLoginAction = new DisplayLoginAction(displayFormView);
        ShowDocumentsAction showDocumentsAction = new ShowDocumentsAction(documentView, documentRepository, session, info, accessService);
        LogOutAction logOutAction = new LogOutAction(session, info);
        DisplayDocumentFormAction displayDocumentFormAction = new DisplayDocumentFormAction(displayFormView);
        CreateDocumentAction createDocumentAction = new CreateDocumentAction(documentView, documentRepository, session, info, accessService);
        ShowDocumentDetailsAction showDocumentDetailsAction = new ShowDocumentDetailsAction(documentView, documentRepository, session, info, accessService);
        EditDocumentAction editDocumentAction = new EditDocumentAction(documentView, documentRepository, session, info);
        DeleteDocumentAction deleteDocumentAction = new DeleteDocumentAction(documentRepository, documentView, info, session, accessService);
        DisplayUserFormAction displayUserFormAction = new DisplayUserFormAction(session, displayFormView, info, accessService);
        DisplayTaskFormAction displayTaskFormAction = new DisplayTaskFormAction(displayFormView);
        DisplayDocumentEditFormAction displayDocumentEditFormAction = new DisplayDocumentEditFormAction(displayFormView, documentRepository, info);
        ShowAllProjectAction showAllProjectAction = new ShowAllProjectAction(projectView, projectRepository, session, info, accessService);
        AddUserToProjectAction addUserToProjectAction = new AddUserToProjectAction(permissionRepository, userRepository, permissionView, session, accessService, info);
        DisplayUserToProjectFormAction displayUserToProjectFormAction = new DisplayUserToProjectFormAction(displayFormView);
        ShowOneProjectAction showOneProjectAction = new ShowOneProjectAction(session, projectView, projectRepository, info, accessService);
        CreateUserAction createUserAction = new CreateUserAction(userRepository, userView, session, info, accessService);
        CreateTaskAction createTaskAction = new CreateTaskAction(taskView, taskRepository, session, info, accessService);
        ShowTasksAction showTasksAction = new ShowTasksAction(taskView, taskRepository, session, info, accessService);

        actions.add(displayUserToProjectFormAction);
        actions.add(displayDocumentEditFormAction);
        actions.add(showOneProjectAction);
        actions.add(addUserToProjectAction);
        actions.add(logInAction);
        actions.add(displayLoginAction);
        actions.add(deleteDocumentAction);
        actions.add(displayProjectFormAction);
        actions.add(createProjectAction);
        actions.add(showProjectsAction);
        actions.add(deleteProjectAction);
        actions.add(showProjectDetailsAction);
        actions.add(showDocumentsAction);
        actions.add(logOutAction);
        actions.add(displayDocumentFormAction);
        actions.add(createDocumentAction);
        actions.add(showDocumentDetailsAction);
        actions.add(editDocumentAction);
        actions.add(createUserAction);
        actions.add(displayUserFormAction);
        actions.add(showAllProjectAction);
        actions.add(showTasksAction);
        actions.add(createTaskAction);
        actions.add(displayTaskFormAction);


        return this;
    }

    public Config withConsoleView() {

        InfoService info = new ConsoleInfo();
        ConsoleSession consoleSession = new ConsoleSession();
        session = consoleSession;
        PermissionView permissionView = new PermissionConsoleView();
        PermissionRepository permissionRepository = new PermissionRepository(persistenceManager);
        UserView userView = new UserConsoleView();
        UserRepository userRepository = new UserRepository(persistenceManager);
        accessService = new AccessService(userRepository, permissionRepository);
        LogInAction login = new LogInAction(userView, userRepository, consoleSession, info);

        ProjectView projectView = new ProjectConsoleView();
        ProjectRepository projectRepository = new ProjectRepository(persistenceManager);

        DocumentView documentView = new DocumentConsoleView();
        DocumentRepository documentRepository = new DocumentRepository(persistenceManager);


        ShowProjectsAction showProjectsAction = new ShowProjectsAction(projectView, projectRepository, session, permissionRepository, info);
        CreateProjectAction createProjectAction = new CreateProjectAction(projectView, projectRepository, session, info, accessService);
        ShowProjectDetailsAction showProjectDetailsAction = new ShowProjectDetailsAction(permissionView, permissionRepository, session, projectView, projectRepository, info, accessService, userRepository);
        DeleteProjectAction deleteProjectAction = new DeleteProjectAction(projectRepository, projectView, permissionRepository, session, documentRepository, info, accessService);
        ShowDocumentsAction showDocumentsAction = new ShowDocumentsAction(documentView, documentRepository, session, info, accessService);
        CreateDocumentAction createDocumentAction = new CreateDocumentAction(documentView, documentRepository, session, info, accessService);
        ShowDocumentDetailsAction showDocumentDetailsAction = new ShowDocumentDetailsAction(documentView, documentRepository, session, info, accessService);
        EditDocumentAction editDocumentAction = new EditDocumentAction(documentView, documentRepository, session, info);
        DeleteDocumentAction deleteDocumentAction = new DeleteDocumentAction(documentRepository, documentView, info, session, accessService);
        ShowAllProjectAction showAllProjectAction = new ShowAllProjectAction(projectView, projectRepository, session, info, accessService);
        AddUserToProjectAction addUserToProjectAction = new AddUserToProjectAction(permissionRepository, userRepository, permissionView, session, accessService, info);
        CreateUserAction createUserAction = new CreateUserAction(userRepository, userView, session, info, accessService);


        actions.add(login);
        actions.add(addUserToProjectAction);
        actions.add(createUserAction);
        actions.add(createProjectAction);
        actions.add(createDocumentAction);
        actions.add(showDocumentDetailsAction);
        actions.add(showProjectDetailsAction);
        actions.add(showAllProjectAction);
        actions.add(showProjectsAction);
        actions.add(showDocumentsAction);
        actions.add(editDocumentAction);
        actions.add(deleteDocumentAction);
        actions.add(deleteProjectAction);


        return this;
    }
}
