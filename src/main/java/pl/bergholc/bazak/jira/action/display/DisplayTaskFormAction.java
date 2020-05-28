package pl.bergholc.bazak.jira.action.display;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.view.web.DisplayFormView;

import java.util.Collections;
import java.util.List;

public class DisplayTaskFormAction implements Action {
    private DisplayFormView displayFormView;

    public DisplayTaskFormAction(DisplayFormView displayFormView) {
        this.displayFormView = displayFormView;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void execute() {
        displayFormView.setAttribute("projectId", displayFormView.getParameter("projectId"));
        displayFormView.display("/taskForm.jsp");
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Collections.singletonList(Role.CUSTOMER);
    }
}
