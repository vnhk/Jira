package pl.bergholc.bazak.jira.action.create;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.FormException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Document;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.DocumentRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.utility.FormUtility;
import pl.bergholc.bazak.jira.view.DocumentView;

import java.util.Arrays;
import java.util.List;

public class CreateDocumentAction implements Action {

    private DocumentView documentView;
    private DocumentRepository repository;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;


    public CreateDocumentAction(DocumentView documentView, DocumentRepository repository, SessionService session, InfoService info, AccessService accessService) {
        this.documentView = documentView;
        this.repository = repository;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public String getDisplayName() {
        return "Create Document";
    }

    @Override
    public void execute() {
        try {
            int projectId = documentView.getProjectId();
            if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                throw new AccessException();
            }
            Document document = new Document();
            document.setProjectId(projectId);
            document.setCreatorId(session.getUserId());
            document.setTitle(documentView.getData("title"));
            if (FormUtility.isEmptyForm(document.getTitle())) {
                throw new FormException("Document title");
            }
            document.setDescription(documentView.getData("description"));
            document.setTopic(documentView.getData("topic"));
            if (FormUtility.isEmptyForm(document.getTopic())) {
                throw new FormException("Document topic");
            }
            document.setContent(documentView.getData("content"));

            repository.add(document);
            info.info("Document was created.");
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        } catch (FormException e) {
            info.error(e.getMessage());
        }
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Arrays.asList(Role.EMPLOYEE, Role.MANAGER, Role.CUSTOMER);
    }
}