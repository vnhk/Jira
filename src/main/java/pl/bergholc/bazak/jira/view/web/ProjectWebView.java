package pl.bergholc.bazak.jira.view.web;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.model.Project;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.ProjectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ProjectWebView implements ProjectView {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Logger logger = LoggerManager.getApplicationLogger();

    public ProjectWebView(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public int getProjectId() {
        return Integer.parseInt(request.getParameter("projectId"));
    }

    public void display(Project project, Role role) {
        try {
            request.setAttribute("project", project);
            request.setAttribute("projectId", project.getId());
            if (role != null) {
                request.setAttribute("role", role.ordinal());
            } else {
                request.setAttribute("role", 0);

            }
            request.getRequestDispatcher("/project.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(String message) {
        try {
            request.setAttribute("message", message);
            request.getRequestDispatcher("/info.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void display(List<Project> project) {
        try {
            request.setAttribute("projects", project);
            request.getRequestDispatcher("/projects.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void display(Set<Project> project) {
        try {
            request.setAttribute("projects", project);
            request.getRequestDispatcher("/projects.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getData(String attribute) {
        return request.getParameter(attribute);
    }
}


