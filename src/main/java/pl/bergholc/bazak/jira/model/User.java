package pl.bergholc.bazak.jira.model;

import pl.bergholc.bazak.jira.persistence.Persistable;


public class User implements Persistable {
    private int id;
    private String login;
    private String password;
    private boolean admin;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "#" +
                "" + id +
                "*" + login + '\'' +
                "*" + password + '\'' +
                "*" + admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
