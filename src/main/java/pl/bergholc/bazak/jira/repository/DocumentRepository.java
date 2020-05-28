package pl.bergholc.bazak.jira.repository;

import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Document;
import pl.bergholc.bazak.jira.persistence.*;
import pl.bergholc.bazak.jira.utility.RepositoryUtility;

import java.util.LinkedList;
import java.util.List;


public class DocumentRepository {
    private PersistenceManager persistence;

    public DocumentRepository(PersistenceManager persistence) {
        this.persistence = persistence;
    }

    public Document add(Document document) throws PersistenceException {
        return (Document) persistence.create(document);
    }

    public List<Document> findByTitle(String title) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Document.class);
        qs.appendCondition(new SearchCondition("title", Operator.EQUAL, title));
        List<Persistable> projects = persistence.find(qs);

        return RepositoryUtility.castFromPersistable(projects);
    }

    public Document findById(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Document.class);
        qs.appendCondition(new SearchCondition("id", Operator.EQUAL, String.valueOf(id)));
        List<Persistable> documents = new LinkedList<>();
        documents = persistence.find(qs);
        if(documents.size()==0){
            return null;
        }
        return (Document) documents.get(0);

    }

    public List<Document> findByProjectId(int id) throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Document.class);
        qs.appendCondition(new SearchCondition("projectId", Operator.EQUAL, String.valueOf(id)));
        return RepositoryUtility.castFromPersistable(persistence.find(qs));
    }

    public void update(Document document) throws PersistenceException {
        if (document != null)
            persistence.update(document);
    }

    public void delete(int id) throws PersistenceException {
        Document document = new Document();
        document.setId(id);
        persistence.delete(document);
    }

    public List<Document> showAll() throws PersistenceException {
        QueryBuilder qs = new QueryBuilder(Document.class);
        List<Persistable> documents = persistence.find(qs);
        return RepositoryUtility.castFromPersistable(documents);
    }
}
