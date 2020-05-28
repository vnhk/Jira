package pl.bergholc.bazak.jira.repository;

import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Permission;
import pl.bergholc.bazak.jira.persistence.Operator;
import pl.bergholc.bazak.jira.persistence.PersistenceManager;
import pl.bergholc.bazak.jira.persistence.QueryBuilder;
import pl.bergholc.bazak.jira.persistence.SearchCondition;
import pl.bergholc.bazak.jira.utility.RepositoryUtility;

import java.util.List;

public class PermissionRepository {
    private PersistenceManager persistence;

    public PermissionRepository(PersistenceManager persistence) {
        this.persistence = persistence;
    }

    public Permission add(Permission permission) throws PersistenceException {
        return (Permission) persistence.create(permission);
    }

    public List<Permission> findByProjectId(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Permission.class);
        qs.appendCondition(new SearchCondition("projectId", Operator.EQUAL, String.valueOf(id)));
        return RepositoryUtility.castFromPersistable(persistence.find(qs));
    }

    public List<Permission> findByUserId(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Permission.class);
        qs.appendCondition(new SearchCondition("userId", Operator.EQUAL, String.valueOf(id)));
        return RepositoryUtility.castFromPersistable(persistence.find(qs));
    }

    public void delete(Permission permission) throws PersistenceException {
        persistence.delete(permission);
    }

}
