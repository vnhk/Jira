package pl.bergholc.bazak.jira.model;

import pl.bergholc.bazak.jira.persistence.Persistable;

import java.util.Objects;

public class Permission implements Persistable {
    private int userId;
    private int roleId;
    private int projectId;
    private int id;

    public Permission() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;
        Permission that = (Permission) o;
        return getUserId() == that.getUserId() &&
                getRoleId() == that.getRoleId() &&
                getProjectId() == that.getProjectId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getRoleId(), getProjectId());
    }
}
