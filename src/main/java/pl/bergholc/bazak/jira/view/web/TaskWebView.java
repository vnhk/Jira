package pl.bergholc.bazak.jira.view.web;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.model.Task;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.TaskView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TaskWebView implements TaskView {
    private Logger logger = LoggerManager.getApplicationLogger();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public TaskWebView(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public int getTaskId(List<Task> tasks) {
        int id = Integer.valueOf(request.getParameter("taskId"));
        int iteration = 0;
        for (Task task : tasks) {
            if (task.getId() == id) {
                break;
            }
            iteration++;
        }
        return iteration;
    }

    @Override
    public void display(List<Task> task) {
        try {
            request.setAttribute("tasks", task);
            request.setAttribute("projectId", request.getParameter("projectId"));
            request.getRequestDispatcher("/tasks.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void display(Task task) {
        try {
            request.setAttribute("task", task);
            request.getRequestDispatcher("/tasks.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }


    @Override
    public int getTaskId() {
        return Integer.valueOf(request.getParameter("taskId"));
    }


    public Task createTask(int projectId) {
        String name = request.getParameter("taskName");
        String description = request.getParameter("taskDescription");
        String empId = request.getParameter("taskEmployee");

        Task task = new Task();
        task.setProjectId(projectId);
        task.setDescription(description);
        task.setName(name);
        task.setEmployee(empId);

        return task;
    }

    @Override
    public String getData(String attribute) {
        return request.getParameter(attribute);
    }

    @Override
    public int getProjectId() {
        String result = request.getParameter("projectId");
        if (result == null) {
            return (Integer) (request.getAttribute("projectId"));
        }
        return Integer.valueOf(result);
    }
}
