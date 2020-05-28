package pl.bergholc.bazak.jira.view.web;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.PermissionView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PermissionWebView implements PermissionView {
    private Logger logger = LoggerManager.getApplicationLogger();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public PermissionWebView(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String getUsername() {
        return request.getParameter("username");
    }

    @Override
    public int getRole() {
        int roleId;
        String role = request.getParameter("role");
        if (role.equals("manager")) {
            roleId = Role.MANAGER.ordinal() + 1;
        } else if (role.equals("employee")) {
            roleId = Role.EMPLOYEE.ordinal() + 1;
        } else {
            roleId = Role.CUSTOMER.ordinal() + 1;
        }

        return roleId;
    }

    @Override
    public void display(List<Permission> permissions, Project project, List<String> names) {
        try {
            request.setAttribute("project", project);
            request.setAttribute("permissions", permissions);
            request.setAttribute("names", names);
            request.getRequestDispatcher("/permissions.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public int getProjectId() {
        return Integer.parseInt(request.getParameter("projectId"));
    }
}
