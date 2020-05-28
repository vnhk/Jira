package pl.bergholc.bazak.jira.action.show;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Document;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.DocumentRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.DocumentView;

import java.util.Arrays;
import java.util.List;

public class ShowDocumentDetailsAction implements Action {
    private DocumentView documentView;
    private DocumentRepository documentRepository;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;

    public ShowDocumentDetailsAction(DocumentView documentView, DocumentRepository documentRepository, SessionService session, InfoService info, AccessService accessService) {
        this.documentView = documentView;
        this.documentRepository = documentRepository;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public String getDisplayName() {
        return "Show Document Details";
    }

    @Override
    public void execute() {
        try {
            int projectId = documentView.getProjectId();
            if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                throw new AccessException();
            }
            List<Document> documents = documentRepository.findByProjectId(projectId);
            int id = documentView.getDocumentId(documents);
            documentView.display(documents.get(id));
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Arrays.asList(Role.MANAGER, Role.EMPLOYEE, Role.CUSTOMER);
    }
}
