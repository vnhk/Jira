package pl.bergholc.bazak.jira.action.display;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.view.web.DisplayFormView;

import java.util.List;

public class DisplayLoginAction implements Action {

    private DisplayFormView displayFormView;

    public DisplayLoginAction(DisplayFormView displayFormView) {
        this.displayFormView = displayFormView;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void execute() {
        displayFormView.display("/loginForm.jsp");
    }

    @Override
    public List<Role> getAllowedRoles() {
        return null;
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }
}
