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


public class ShowDocumentsAction implements Action {
    private DocumentView documentView;
    private DocumentRepository repository;
    private SessionService session;
    private InfoService info;
    private AccessService accessService;

    public ShowDocumentsAction(DocumentView documentView, DocumentRepository repository, SessionService session, InfoService info, AccessService accessService) {
        this.documentView = documentView;
        this.repository = repository;
        this.session = session;
        this.info = info;
        this.accessService = accessService;
    }

    @Override
    public void execute() {
        try {
            int projectId = documentView.getProjectId();
            if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                throw new AccessException();
            }

            List<Document> documents = repository.findByProjectId(projectId);
            documentView.display(documents);
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e) {
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Arrays.asList(Role.EMPLOYEE, Role.CUSTOMER, Role.MANAGER);
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public String getDisplayName() {
        return "Show Documents";
    }
}
