package pl.bergholc.bazak.jira.repository;

import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.persistence.*;
import pl.bergholc.bazak.jira.utility.RepositoryUtility;
import pl.bergholc.bazak.jira.model.Project;

import java.util.List;

public class ProjectRepository {
    private PersistenceManager persistence;

    public ProjectRepository(PersistenceManager persistence) {
        this.persistence = persistence;
    }

    public Project add(Project project) throws PersistenceException {
        return (Project) persistence.create(project);
    }

    public List<Project> findByName(String name) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Project.class);
        qs.appendCondition(new SearchCondition("name", Operator.EQUAL, name));
        List<Persistable> projects = persistence.find(qs);

        return RepositoryUtility.castFromPersistable(projects);
    }

    public void delete(int id) throws PersistenceException {
        Project project = new Project();
        project.setId(id);
        persistence.delete(project);
    }

    public Project findById(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Project.class);
        qs.appendCondition(new SearchCondition("id", Operator.EQUAL, String.valueOf(id)));
        List<Persistable> persistables = persistence.find(qs);
        if (persistables != null)
            return (Project) persistence.find(qs).get(0);
        return null;
    }

    public List<Project> showAll() throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Project.class);
        List<Persistable> projects = persistence.find(qs);
        return RepositoryUtility.castFromPersistable(projects);
    }
}
