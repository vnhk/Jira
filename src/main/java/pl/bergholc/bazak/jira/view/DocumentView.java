package pl.bergholc.bazak.jira.view;

import pl.bergholc.bazak.jira.model.Document;

import java.util.List;

public interface DocumentView {
    void display(List<Document> document);

    void display(Document document);

    int getDocumentId(List<Document> documents);

    int getDocumentId();

    int getProjectId();

    String getData(String attribute);
}