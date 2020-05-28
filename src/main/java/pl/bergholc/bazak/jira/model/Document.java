package pl.bergholc.bazak.jira.model;

import pl.bergholc.bazak.jira.persistence.Persistable;

public class Document implements Persistable {
    private int id;
    private String description;
    private int creatorId;
    private String topic;
    private String content;
    private int projectId;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "#" +
                "" + id +
                "* " + title + '\n' +
                "* " + topic + '\n' +
                "* " + description + '\n' +
                "* " + content + '\n';
    }
}
