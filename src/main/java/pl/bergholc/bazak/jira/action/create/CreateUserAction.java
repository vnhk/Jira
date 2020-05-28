package pl.bergholc.bazak.jira.action.create;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.FormException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.model.User;
import pl.bergholc.bazak.jira.repository.UserRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.utility.FormUtility;
import pl.bergholc.bazak.jira.view.UserView;

import java.util.Collections;
import java.util.List;

public class CreateUserAction implements Action {
    private UserRepository repository;
    private UserView view;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;

    public CreateUserAction(UserRepository repository, UserView view, SessionService session, InfoService info, AccessService accessService) {
        this.repository = repository;
        this.view = view;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public String getDisplayName() {
        return "Create User";
    }

    @Override
    public void execute() {
        try {
            if (!accessService.isAdmin(session.getUserId())) {
                throw new AccessException();
            }
            User user = new User();
            user.setLogin(view.getData("login"));
            user.setPassword(view.getData("password"));
            if (FormUtility.isEmptyForm(user.getLogin())) {
                throw new FormException("User login");
            }
            if (FormUtility.isEmptyForm(user.getPassword())) {
                throw new FormException("User password");
            }
            String admin = view.getData("admin");
            if (admin.equals("no")) {
                user.setAdmin(false);
            } else {
                user.setAdmin(true);
            }
            repository.add(user);
            info.info("New user was created. #ID: " + user.getId());
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        } catch (FormException e) {
            info.error(e.getMessage());
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return true;
    }

    @Override

    public List<Role> getAllowedRoles() {
        return Collections.singletonList(null);
    }
}
