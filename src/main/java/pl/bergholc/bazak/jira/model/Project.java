package pl.bergholc.bazak.jira.model;

import pl.bergholc.bazak.jira.persistence.Persistable;

import java.util.Objects;


public class Project implements Persistable {
    private int id;
    private String name;
    private String description;
    private int creatorId;

    @Override
    public String toString() {
        return "#" +
                "*" + id +'\n'+
                "*" + name + '\n' +
                "*" + description + '\n';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return getId() == project.getId() &&
                getCreatorId() == project.getCreatorId() &&
                getName().equals(project.getName()) &&
                getDescription().equals(project.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getCreatorId());
    }
}
