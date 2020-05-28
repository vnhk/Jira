package pl.bergholc.bazak.jira.action.display;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.web.DisplayFormView;

import java.util.List;

public class DisplayProjectFormAction implements Action {
    private SessionService session;
    private DisplayFormView displayFormView;
    private InfoService info;
    private AccessService accessService;

    public DisplayProjectFormAction(SessionService session, DisplayFormView displayFormView, InfoService info, AccessService accessService) {
        this.session = session;
        this.displayFormView = displayFormView;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public String getDisplayName() {
        return "Create Project";
    }

    @Override
    public void execute() {
        try {
            if (!accessService.isAdmin(session.getUserId())) {
                throw new AccessException();
            }
            displayFormView.display("/projectForm.jsp");
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
}
