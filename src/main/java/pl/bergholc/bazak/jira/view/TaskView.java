package pl.bergholc.bazak.jira.view;

import java.util.List;
import pl.bergholc.bazak.jira.model.Task;

public interface TaskView {
    void display(List<Task> task);

    void display(Task task);

    int getTaskId(List<Task> tasks);

    int getTaskId();

    int getProjectId();

    String getData(String attribute);
}