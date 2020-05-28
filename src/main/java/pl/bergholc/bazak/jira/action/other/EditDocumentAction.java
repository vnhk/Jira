package pl.bergholc.bazak.jira.action.other;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.FormException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Document;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.DocumentRepository;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.utility.FormUtility;
import pl.bergholc.bazak.jira.view.DocumentView;

import java.util.List;

public class EditDocumentAction implements Action {
    private DocumentView view;
    private DocumentRepository repository;
    private SessionService session;
    private InfoService info;

    public EditDocumentAction(DocumentView view, DocumentRepository repository, SessionService session, InfoService info) {
        this.view = view;
        this.repository = repository;
        this.session = session;
        this.info = info;
    }

    @Override
    public String getDisplayName() {
        return "Edit Document";
    }

    @Override
    public void execute() {
        try {
            int docId = view.getDocumentId();
            Document document = repository.findById(docId);
            if (document == null) {
                throw new PersistenceException("Document does not exist", "Document");
            }
            int projectId = document.getProjectId();
            if (session.getUserId() != document.getCreatorId()) {
                throw new AccessException();
            }
            document.setProjectId(projectId);
            document.setTitle(view.getData("title"));
            if (FormUtility.isEmptyForm(document.getTitle())) {
                throw new FormException("Document title");
            }
            document.setDescription(view.getData("description"));
            document.setTopic(view.getData("topic"));
            if (FormUtility.isEmptyForm(document.getTopic())) {
                throw new FormException("Document topic");
            }
            document.setContent(view.getData("content"));
            repository.update(document);
            info.info("Document was edited.");
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.warning("Editing is allowed only for the creator of the Document");
        } catch (FormException e) {
            info.error(e.getMessage());
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return null;
    }
}
