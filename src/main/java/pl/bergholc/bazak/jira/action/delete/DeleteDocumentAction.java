package pl.bergholc.bazak.jira.action.delete;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.DocumentRepository;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.service.SessionService;
import pl.bergholc.bazak.jira.view.DocumentView;

import java.util.Collections;
import java.util.List;

public class DeleteDocumentAction implements Action {
    private DocumentRepository documentRepository;
    private DocumentView documentView;
    private InfoService info;
    private SessionService session;
    private AccessService accessService;

    public DeleteDocumentAction(DocumentRepository documentRepository, DocumentView documentView, InfoService info, SessionService session, AccessService accessService) {
        this.documentRepository = documentRepository;
        this.documentView = documentView;
        this.info = info;
        this.session = session;
        this.accessService = accessService;
    }

    @Override
    public String getDisplayName() {
        return "Delete Document";
    }

    @Override
    public void execute() {
        try {
            int projectId = documentView.getProjectId();
            if (!accessService.isAdmin(session.getUserId())) {
                if (accessService.hasNoNeededRole(getAllowedRoles(), session.getUserId(), projectId)) {
                    throw new AccessException();
                }
            }
            int id = documentView.getDocumentId();
            documentRepository.delete(id);
            info.info("Document was deleted.");
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        } catch (AccessException e){
            info.error("You do not have enough rights to perform this operation");
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Collections.singletonList(Role.MANAGER);
    }
}
