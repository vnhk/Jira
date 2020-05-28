package pl.bergholc.bazak.jira.action.other;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;

import java.util.List;

public class LogOutAction implements Action {
    private SessionService session;
    private InfoService info;

    public LogOutAction(SessionService session, InfoService info) {
        this.session = session;
        this.info = info;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public void execute() {
        session.clear();
        info.info("Successful logout.");
    }

    @Override
    public List<Role> getAllowedRoles() {
        return null;
    }
}
