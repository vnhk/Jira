package pl.bergholc.bazak.jira.action.other;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.model.User;
import pl.bergholc.bazak.jira.repository.UserRepository;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.UserView;

import java.util.List;

public class LogInAction implements Action {
    private UserView userView;
    private UserRepository userRepository;
    private SessionService session;
    private InfoService info;

    public LogInAction(UserView userView, UserRepository userRepository, SessionService session, InfoService info) {
        this.userView = userView;
        this.userRepository = userRepository;
        this.session = session;
        this.info = info;
    }

    @Override
    public String getDisplayName() {
        return "Login";
    }

    @Override
    public void execute() {
        String login = userView.getData("login");
        String password = userView.getData("password");
        try {
            User user = userRepository.logIn(login, password);
            if (user == null) {
                throw new AccessException();
            }
            session.setUserId(user.getId());
            session.setUserLogin(user.getLogin());
            info.info("Login completed successfully.\n" +
                    "Account ID: " + user.getId());
        } catch (PersistenceException | AccessException e) {
            info.warning("Login or password is incorrect");
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return null;
    }
}
