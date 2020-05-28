package pl.bergholc.bazak.jira.repository;

import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Task;
import pl.bergholc.bazak.jira.persistence.*;
import pl.bergholc.bazak.jira.utility.RepositoryUtility;

import java.util.List;

public class TaskRepository {
    private PersistenceManager persistence;

    public TaskRepository(PersistenceManager persistence) {
        this.persistence = persistence;
    }

    public Task add(Task task) throws PersistenceException {
        return (Task) persistence.create(task);
    }

    public List<Task> findByEmployee(String employeeId) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Task.class);
        qs.appendCondition(new SearchCondition("employee", Operator.EQUAL, employeeId));
        List<Persistable> projects = persistence.find(qs);

        return RepositoryUtility.castFromPersistable(projects);
    }

    public Task findById(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Task.class);
        qs.appendCondition(new SearchCondition("id", Operator.EQUAL, String.valueOf(id)));
        List<Persistable> tasks = persistence.find(qs);
        if (tasks.size() == 0) {
            return null;
        }
        return (Task) tasks.get(0);

    }

    public List<Task> findByProjectId(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Task.class);
        qs.appendCondition(new SearchCondition("projectId", Operator.EQUAL, String.valueOf(id)));
        return RepositoryUtility.castFromPersistable(persistence.find(qs));
    }

    public void update(Task task) throws PersistenceException {
        if (task != null)
            persistence.update(task);
    }

    public void delete(int id) throws PersistenceException {
        Task task = new Task();
        task.setId(id);
        persistence.delete(task);
    }

    public List<Task> showAll() throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Task.class);
        List<Persistable> tasks = persistence.find(qs);
        return RepositoryUtility.castFromPersistable(tasks);
    }

    public List<Task> findByProjectIdAndEmployee(int projectId, String userLogin) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Task.class);
        qs.appendCondition(new SearchCondition("employee", Operator.EQUAL, userLogin));
        qs.appendOperator(Operator.AND);
        qs.appendCondition(new SearchCondition("projectId", Operator.EQUAL, String.valueOf(projectId)));

        List<Persistable> projects = persistence.find(qs);

        return RepositoryUtility.castFromPersistable(projects);

    }
}
