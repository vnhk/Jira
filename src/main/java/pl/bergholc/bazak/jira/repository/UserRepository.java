package pl.bergholc.bazak.jira.repository;


import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.persistence.*;
import pl.bergholc.bazak.jira.model.User;

import java.util.List;

public class UserRepository {
    private PersistenceManager persistence;

    public UserRepository(PersistenceManager persistence) {
        this.persistence = persistence;
    }

    public User add(User user) throws PersistenceException {
        return (User) persistence.create(user);
    }

    public User logIn(String login, String password) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(User.class);
        qs.appendCondition(new SearchCondition("login", Operator.EQUAL, login));
        qs.appendOperator(Operator.AND);
        qs.appendCondition(new SearchCondition("password", Operator.EQUAL, password));

        List<Persistable> users = persistence.find(qs);
        if (users.isEmpty())
            return null;

        return (User) users.get(0);
    }

    public User getUserById(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(User.class);
        qs.appendCondition(new SearchCondition("id", Operator.EQUAL, String.valueOf(id)));
        List<Persistable> users = persistence.find(qs);
        if (users.isEmpty())
            return null;

        return (User) users.get(0);
    }

    public int getUserIdByName(String name) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(User.class);
        qs.appendCondition(new SearchCondition("login", Operator.EQUAL, name));
        List<Persistable> users = persistence.find(qs);
        if (users.isEmpty())
            return 0;

        return users.get(0).getId();
    }
}
